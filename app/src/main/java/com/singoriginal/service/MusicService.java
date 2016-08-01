package com.singoriginal.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Music;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.SongBrief;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Request;

/**
 * Created by lanouhn on 16/7/30.
 */
public class MusicService extends Service
{
    private static MediaPlayer mediaPlayer;
    private static MusicReceiver musicReceiver;
    private MyBinder myBinder = new MyBinder();
    private Handler hdl;

    private NotificationManager notificationManager;
    private Notification notification;
    private RemoteViews remoteView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return myBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        //播放器参数设置
        MusicData.music_play_idx = 0;
        MusicData.music_play_mode = ConstVal.PLAY_MODE_LIST_LOOP;
        MusicData.music_play_state = ConstVal.PLAY_STATE_PLAYING;
        MusicData.music_type = ConstVal.MUSIC_TYPE_NET;
        MusicData.musicList = new ArrayList<>();

        //创建MediaPlay对象,并设置准备,完成监听事件
        if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    mp.start();
                    MusicData.music_play_state = ConstVal.PLAY_STATE_PLAYING;
                    if (remoteView != null)
                    {
                        remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_pause);
                        notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                    }
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                }
            });
        }

        hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case ConstVal.SONGBRIEF_CODE:
                        SongBrief song = new Gson().fromJson(json, SongBrief.class);
                        String songurl = song.getData().getSqurl();
                        if (songurl == null || songurl.equals(""))
                        {
                            songurl = song.getData().getHqurl();
                            if (songurl == null || songurl.equals(""))
                            {
                                songurl = song.getData().getLqurl();
                            }
                        }
                        prepareNetMedia(getApplicationContext(), songurl);
                        break;

                    case ConstVal.MUSIC_PROGRESS_CODE:

                        break;
                }
            }
        };

        musicReceiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER.START");
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER.TOGGLE");
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER.NEXT");
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER.PREV");
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER.CLOSE");
        registerReceiver(musicReceiver, filter);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
        }
        if (musicReceiver != null)
        {
            unregisterReceiver(musicReceiver);
        }
    }

    public static void prepareLocalMedia(String path)
    {
        if (path != null && mediaPlayer != null)
        {
            mediaPlayer.reset();
            try
            {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepareAsync();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void prepareNetMedia(Context context, String url)
    {
        Uri uri = Uri.parse(url);
        if (uri != null && mediaPlayer != null)
        {
            mediaPlayer.reset();
            try
            {
                mediaPlayer.setDataSource(context, uri);
                mediaPlayer.prepareAsync();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startPlay()
    {
        mediaPlayer.start();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (mediaPlayer.isPlaying())
                {
                    try
                    {
                        Thread.sleep(1000);
                        Message msg = hdl.obtainMessage(ConstVal.MUSIC_PROGRESS_CODE, mediaPlayer.getCurrentPosition(), 0);
                        hdl.sendMessage(msg);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void playNext()
    {

    }

    private void showNotification(Bundle bundle)
    {
        String imgurl = bundle.getString("imgurl");
        String songname = bundle.getString("songname");
        String author = bundle.getString("author");
        notificationManager
                = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.sing_icon_client);
        builder.setTicker(songname + " - " + author);
        remoteView = new RemoteViews(getPackageName(), R.layout.notification_music);
        remoteView.setTextViewText(R.id.ntf_tv_song, songname);
        remoteView.setTextViewText(R.id.ntf_tv_author, author);

        //点击切换播放/暂停
        Intent toggleIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER.TOGGLE");
        toggleIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_TOGGLE);
        PendingIntent togglePendingIntent = PendingIntent.getBroadcast(this,
                                                                       0,
                                                                       toggleIntent,
                                                                       PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_play, togglePendingIntent);

        //点击下一首
        Intent nextIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER.NEXT");
        nextIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this,
                                                                     0,
                                                                     nextIntent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_next, nextPendingIntent);

        //点击上一首
        Intent prevIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER.PREV");
        prevIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_PREV);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this,
                                                                     0,
                                                                     prevIntent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_prev, prevPendingIntent);

        //点击上一首
        Intent closeIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER.CLOSE");
        closeIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this,
                                                                     0,
                                                                     closeIntent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_close, closePendingIntent);

        builder.setCustomBigContentView(remoteView).setOngoing(true).setAutoCancel(false);
        notification = builder.build();
        Picasso.with(this)
               .load(imgurl)
               .resize(320, 320)
               .centerCrop()
               .into(remoteView, R.id.ntf_iv_show, ConstVal.NOTIFY_SHOW, notification);
        notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
    }

    public class MyBinder extends Binder
    {

    }

    public class MusicReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int resultCode = intent.getIntExtra("requestCode", 0);
            Request request;
            Music msc;
            String url;
            Bundle bundle;
            switch (resultCode)
            {
                case ConstVal.MUSIC_PLAY_START:
                    if (mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                    }
                    msc = MusicData.musicList.get(MusicData.music_play_idx);
                    url = ConstVal.GETSONGURL_LINK
                                 + "songid=" + msc.getSongid()
                                 + "&songtype=" + msc.getSongtype();
                    request = new Request.Builder().url(url).build();
                    OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
                    bundle = new Bundle();
                    bundle.putString("imgurl", msc.getUserimg());
                    bundle.putString("songname", msc.getSongname());
                    bundle.putString("author", msc.getUsername());
                    showNotification(bundle);
                    break;

                case ConstVal.MUSIC_PLAY_TOGGLE:
                    if (MusicData.music_play_state == ConstVal.PLAY_STATE_PLAYING)
                    {
                        if (mediaPlayer != null)
                        {
                            mediaPlayer.pause();
                            MusicData.music_play_state = ConstVal.PLAY_STATE_PAUSE;
                            if (remoteView != null)
                            {
                                remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_play);
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                        }
                    }
                    else if (MusicData.music_play_state == ConstVal.PLAY_STATE_PAUSE)
                    {
                        if (mediaPlayer != null)
                        {
                            MusicData.music_play_state = ConstVal.PLAY_STATE_PLAYING;
                            mediaPlayer.start();
                            if (remoteView != null)
                            {
                                remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_pause);
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                        }
                    }
                    break;

                case ConstVal.MUSIC_PLAY_NEXT:
                    if (MusicData.musicList != null && MusicData.musicList.size() > 0)
                    {
                        if (mediaPlayer != null)
                        {
                            mediaPlayer.stop();

                            if (MusicData.music_play_mode == ConstVal.PLAY_MODE_RANDOM)
                            {
                                MusicData.music_play_idx = new Random(MusicData.musicList.size()).nextInt();
                            }
                            else
                            {
                                if (MusicData.music_play_idx < MusicData.musicList.size() - 1)
                                {
                                    MusicData.music_play_idx++;
                                }
                                else
                                {
                                    MusicData.music_play_idx = 0;
                                }
                            }
                            msc = MusicData.musicList.get(MusicData.music_play_idx);
                            if (remoteView != null)
                            {
                                remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_play);
                                remoteView.setTextViewText(R.id.ntf_tv_song, msc.getSongname());
                                remoteView.setTextViewText(R.id.ntf_tv_author, msc.getUsername());
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                            url = ConstVal.GETSONGURL_LINK
                                  + "songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype();
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
                        }
                    }
                    break;

                case ConstVal.MUSIC_PLAY_PREV:
                    if (MusicData.musicList != null && MusicData.musicList.size() > 0)
                    {
                        if (mediaPlayer != null)
                        {
                            mediaPlayer.stop();
                            if (remoteView != null)
                            {
                                remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_play);
                            }
                            if (MusicData.music_play_idx > 0)
                            {
                                MusicData.music_play_idx --;
                            }
                            else
                            {
                                MusicData.music_play_idx = MusicData.musicList.size() - 1;
                            }
                            msc = MusicData.musicList.get(MusicData.music_play_idx);
                            if (remoteView != null)
                            {
                                remoteView.setImageViewResource(R.id.ntf_ib_play, R.mipmap.note_btn_play);
                                remoteView.setTextViewText(R.id.ntf_tv_song, msc.getSongname());
                                remoteView.setTextViewText(R.id.ntf_tv_author, msc.getUsername());
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                            url = ConstVal.GETSONGURL_LINK
                                  + "songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype();
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
                        }
                    }
                    break;

                case ConstVal.MUSIC_PLAY_CLOSE:
                    if (mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        notificationManager.cancel(ConstVal.NOTIFY_SHOW);
                    }
                    break;
            }
        }
    }
}
