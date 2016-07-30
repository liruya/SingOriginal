package com.singoriginal.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.DailyRecmd;
import com.singoriginal.model.Music;
import com.singoriginal.model.NewSong;
import com.singoriginal.model.PopularSong;
import com.singoriginal.model.RankSong;
import com.singoriginal.service.MusicService;
import com.squareup.picasso.Picasso;

/**
 * 音乐工具类
 * Created by lanouhn on 16/7/25.
 */
public class MusicUtil
{
    private static MusicService.MyBinder binder;
    private static boolean unbindFlag = false;

    public static void showNotification(Context context, Bundle bundle)
    {
        String imgurl = bundle.getString("imgurl");
        String songid = bundle.getString("songid");
        String songtype = bundle.getString("songtype");
        String songname = bundle.getString("songname");
        String author = bundle.getString("author");
        NotificationManager notificationManager
                = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.sing_icon_client);
        builder.setTicker(songname + " - " + author);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.notification_music);
        remoteView.setTextViewText(R.id.ntf_tv_song, songname);
        remoteView.setTextViewText(R.id.ntf_tv_author, author);
        //        Intent intent = new Intent(getPackageName() + ".MUSIC_PAUSE");
        //        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
        //                                                                 0,
        //                                                                 intent,
        //                                                                 PendingIntent.FLAG_UPDATE_CURRENT);
        //        remoteView.setOnClickPendingIntent(R.id.ntf_ib_play, pendingIntent);
        //        builder.setContent(remoteView).setContentIntent(pendingIntent);
        builder.setCustomBigContentView(remoteView).setOngoing(true).setAutoCancel(false);
        Notification notification = builder.build();
        Picasso.with(context)
               .load(imgurl)
               .resize(320, 320)
               .centerCrop()
               .into(remoteView, R.id.ntf_iv_show, ConstVal.NOTIFY_SHOW, notification);
        notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
    }

    public static void startMusicService(Context context)
    {
        Intent intent = new Intent(context, MusicService.class);
        context.startService(intent);
    }

    public static void stopMusicService(Context context)
    {
        Intent intent = new Intent(context, MusicService.class);
        context.stopService(intent);
    }

    /**
     * 创建服务的连接
     * @param context
     * @return
     */
    public static ServiceConnection creatServiceConnection(Context context)
    {
        ServiceConnection svcConn = new ServiceConnection()
        {
            /**
             * 服务与Activity绑定成功后调用
             * @param name
             * @param service
             */
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                binder = (MusicService.MyBinder) service;
            }

            /**
             * 服务非正常断开连接时调用
             * @param name
             */
            @Override
            public void onServiceDisconnected(ComponentName name)
            {
                binder = null;
            }
        };

        return svcConn;
    }

    public static void unbindMusicService(Context context)
    {
        if (binder != null)
        {
            unbindFlag = true;
            while (unbindFlag);
        }
    }

    public static void unbindMusicServiceImmediately(Context context, ServiceConnection svcConnection)
    {
        if (binder != null)
        {
            context.unbindService(svcConnection);
        }
    }

    /**
     * MusicService与Activity绑定
     * @param context
     * @param svcConnection
     */
    public static void bindMusicService(final Context context, final ServiceConnection svcConnection)
    {
        Intent intent = new Intent(context, MusicService.class);
        context.bindService(intent, svcConnection, Context.BIND_AUTO_CREATE);
        unbindFlag = false;

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    if (binder != null)
                    {

                        if (unbindFlag)
                        {
                            context.unbindService(svcConnection);
                            binder = null;
                            unbindFlag = false;
                        }
                    }
                }
            }
        }).start();
    }

    public static Music convertMusicType(Context context, Object object)
    {
        Music msc = null;
        String pkg = object.getClass().getName();
        String classtype = pkg.substring(pkg.lastIndexOf(".")+1);
        switch (classtype)
        {
            case "AdvertSong":
                AdvertSong as = (AdvertSong) object;
                msc = new Music(as.getID() + "",
                                as.getSN(),
                                as.getSK(),
                                as.getUser().getID(),
                                as.getUser().getNN(),
                                as.getUser().getI());
                break;

            case "RankSong":
                RankSong rs = (RankSong) object;
                msc = new Music(rs.getID() + "",
                                rs.getSN(),
                                rs.getSK(),
                                rs.getUser().getID(),
                                rs.getUser().getNN(),
                                rs.getUser().getI());
                break;

            case "NewSong":
                NewSong ns = (NewSong) object;
                msc = new Music(ns.getID() + "",
                                ns.getSN(),
                                ns.getSK(),
                                ns.getUser().getID(),
                                ns.getUser().getNN(),
                                ns.getUser().getI());
                break;

            case "PopularSong":
                PopularSong ps = (PopularSong) object;
                msc = new Music(ps.getID() + "",
                                ps.getSN(),
                                ps.getSK(),
                                ps.getUser().getID(),
                                ps.getUser().getNN(),
                                ps.getUser().getI());
                break;

            case "DailyRecmd":
                DailyRecmd ds = (DailyRecmd) object;
                msc = new Music(ds.getSongId(),
                                ds.getRecommendName(),
                                ds.getSongType(),
                                ds.getUserId(),
                                ds.getNickName(),
                                ds.getImage());
                break;
        }
        return msc;
    }

//    public static void registerService(Context context)
//    {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(context.getPackageName() + ".MUSIC_PAUSE");
//        context.registerReceiver(musicPauseReceiver, filter);
//    }
//
//    public static void unregisterMusicReceiver(Context context)
//    {
//        context.unregisterReceiver(musicPauseReceiver);
//    }

    static class MusicPauseReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
        }
    }
}
