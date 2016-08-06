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
import com.singoriginal.activity.MusicDetailActivity;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Music;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.MusicDetail;
import com.singoriginal.model.SongBrief;
import com.singoriginal.util.MusicUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Request;

/**
 * 音乐服务类
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
        MusicData.music_play_state = ConstVal.PLAY_STATE_PAUSE;
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
                    MusicUtil.playSendDuration(MusicService.this, mediaPlayer.getDuration());
                    MusicUtil.playShowItem(MusicService.this);
                    startPlay();
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
                    if (MusicData.music_play_mode == ConstVal.PLAY_MODE_SINGLE_LOOP)
                    {
                        startPlay();
                    }
                    else
                    {
                        MusicUtil.playNext(MusicService.this);
                    }
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
                        SongBrief brief = new Gson().fromJson(json, SongBrief.class);
                        MusicData.brief = brief;
                        String songurl = MusicData.brief.getData().getSqurl();

                        if (songurl == null || songurl.equals(""))
                        {
                            songurl = MusicData.brief.getData().getHqurl();
                            if (songurl == null || songurl.equals(""))
                            {
                                songurl = MusicData.brief.getData().getLqurl();
                            }
                        }
                        prepareNetMedia(songurl);
                        break;

                    case ConstVal.GET_CURRENT_MUSIC_DETAIL:
                        MusicData.currentMusicDetail = new Gson().fromJson(json, MusicDetail.class);
                        Intent intent = new Intent(getPackageName() + ".DETAIL_RECEIVER");
                        intent.putExtra("requestCode", ConstVal.DETAIL_UPDATE);
                        sendBroadcast(intent);
                        break;
                }
            }
        };

        musicReceiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".MUSIC_RECEIVER");
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
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
        }
        if (musicReceiver != null)
        {
            unregisterReceiver(musicReceiver);
        }
        super.onDestroy();
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
                MusicData.music_play_state = ConstVal.PLAY_STATE_PREPARE;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void prepareNetMedia(String url)
    {
        Uri uri = Uri.parse(url);
        if (uri != null && mediaPlayer != null)
        {
            mediaPlayer.reset();
            try
            {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                MusicData.music_play_state = ConstVal.PLAY_STATE_PREPARE;
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
                        MusicUtil.playSendProgress(MusicService.this, mediaPlayer.getCurrentPosition());
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        MusicData.music_play_state = ConstVal.PLAY_STATE_PLAYING;
        MusicUtil.playSendState(MusicService.this);
    }

    private void pausePlay()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.pause();
            MusicData.music_play_state = ConstVal.PLAY_STATE_PAUSE;
            MusicUtil.playSendState(MusicService.this);
        }
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
        Intent toggleIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER");
        toggleIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_TOGGLE);
        PendingIntent togglePendingIntent = PendingIntent.getBroadcast(this,
                                                                       1,
                                                                       toggleIntent,
                                                                       PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_play, togglePendingIntent);

        //点击下一首
        Intent nextIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER");
        nextIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this,
                                                                     2,
                                                                     nextIntent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_next, nextPendingIntent);

        //点击上一首
        Intent prevIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER");
        prevIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_PREV);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this,
                                                                     3,
                                                                     prevIntent,
                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_ib_prev, prevPendingIntent);

        //关闭
//        Intent closeIntent = new Intent(getPackageName() + ".MUSIC_RECEIVER");
//        closeIntent.putExtra("requestCode", ConstVal.MUSIC_PLAY_CLOSE);
//        PendingIntent closePendingIntent = PendingIntent.getBroadcast(this,
//                                                                     4,
//                                                                     closeIntent,
//                                                                     PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteView.setOnClickPendingIntent(R.id.ntf_ib_close, closePendingIntent);

        //
        Intent detailIntent = new Intent(this, MusicDetailActivity.class);
        PendingIntent detailPendingIntent = PendingIntent.getActivity(this,
                                                                      5,
                                                                      detailIntent,
                                                                      PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.ntf_iv_show, detailPendingIntent);
        remoteView.setOnClickPendingIntent(R.id.ntf_tv_song, detailPendingIntent);
        remoteView.setOnClickPendingIntent(R.id.ntf_tv_author, detailPendingIntent);

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
                            pausePlay();
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
                            startPlay();
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
                            pausePlay();
                            MusicUtil.playSendProgress(MusicService.this, 0);
                            if (MusicData.music_play_mode == ConstVal.PLAY_MODE_RANDOM)
                            {
                                MusicData.music_play_idx = new Random().nextInt(MusicData.musicList.size());
                            }
                            else
                            {
                                if (MusicData.music_play_idx < MusicData.musicList.size() - 1)
                                {
                                    MusicData.music_play_idx ++;
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
                                Picasso.with(MusicService.this)
                                       .load(msc.getUserimg())
                                       .resize(320, 320)
                                       .into(remoteView, R.id.ntf_iv_show, ConstVal.NOTIFY_SHOW, notification);
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                            url = ConstVal.GETSONGURL_LINK
                                  + "songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype();
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
                            url = ConstVal.GETCURRENTDETAIL_LINK
                                  + "&songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype()
                                  + "&version=" + ConstVal.VERSION;
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.GET_CURRENT_MUSIC_DETAIL, request);
                        }
                    }
                    break;

                case ConstVal.MUSIC_PLAY_PREV:
                    if (MusicData.musicList != null && MusicData.musicList.size() > 0)
                    {
                        if (mediaPlayer != null)
                        {
                            pausePlay();
                            MusicUtil.playSendProgress(MusicService.this, 0);
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
                                Picasso.with(MusicService.this)
                                       .load(msc.getUserimg())
                                       .resize(320, 320)
                                       .into(remoteView, R.id.ntf_iv_show, ConstVal.NOTIFY_SHOW, notification);
                                notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
                            }
                            url = ConstVal.GETSONGURL_LINK
                                  + "songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype();
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
                            url = ConstVal.GETCURRENTDETAIL_LINK
                                  + "&songid=" + msc.getSongid()
                                  + "&songtype=" + msc.getSongtype()
                                  + "&version=" + ConstVal.VERSION;
                            request = new Request.Builder().url(url).build();
                            OkHttpUtil.enqueue(context, hdl, ConstVal.GET_CURRENT_MUSIC_DETAIL, request);
                        }
                    }
                    break;

                case ConstVal.MUSIC_PLAY_CLOSE:
                    if (mediaPlayer != null)
                    {
                        pausePlay();
                        mediaPlayer.reset();
                        notificationManager.cancel(ConstVal.NOTIFY_SHOW);
                    }
                    break;

                case ConstVal.GET_CURRENT_MUSIC_DETAIL:
                    msc = MusicData.musicList.get(MusicData.music_play_idx);
                    url = ConstVal.GETCURRENTDETAIL_LINK
                          + "&songid=" + msc.getSongid()
                          + "&songtype=" + msc.getSongtype()
                          + "&version=" + ConstVal.VERSION;
                    request = new Request.Builder().url(url).build();
                    OkHttpUtil.enqueue(context, hdl, ConstVal.GET_CURRENT_MUSIC_DETAIL, request);
                    break;

                case ConstVal.SET_PLAY_POSITIN:
                    int position = intent.getIntExtra("position", 0);
                    if (mediaPlayer != null)
                    {
                        mediaPlayer.seekTo(position);
                    }
                    break;

                case ConstVal.GET_CURRENT_MUSIC_DURATION:
                    if (mediaPlayer != null)
                    {
                        MusicUtil.playSendDuration(MusicService.this, mediaPlayer.getDuration());
                    }
                    break;
            }
        }
    }
}
