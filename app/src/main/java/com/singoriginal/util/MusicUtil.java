package com.singoriginal.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.DailyRecmd;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.model.Music;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.Musician;
import com.singoriginal.model.NewSong;
import com.singoriginal.model.PopularSong;
import com.singoriginal.model.RankSong;
import com.singoriginal.service.MusicService;

/**
 * 音乐工具类
 * Created by lanouhn on 16/7/25.
 */
public class MusicUtil
{
    private static final String TAG = "MusicUtil_TAG";
    private static MusicService.MyBinder binder;
    private static boolean unbindFlag = false;

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

            case "Musician":
                Musician mscian = (Musician) object;
                msc = new Music(mscian.getSong().getID() + "",
                                mscian.getSong().getSN(),
                                mscian.getSong().getSK(),
                                mscian.getID(),
                                mscian.getNN(),
                                mscian.getI());
                break;

            case "HeadIconWork$Data":
                HeadIconWork.Data data = (HeadIconWork.Data) object;
                msc = new Music(data.getID() + "",
                                data.getSN(),
                                data.getSK(),
                                data.getUser().getID(),
                                data.getUser().getNN(),
                                data.getUser().getI());
                break;
        }
        return msc;
    }

    public static void sendBroadcast(Context context, int requestCode, String extFilter)
    {
        Intent intent = new Intent(context.getPackageName() + extFilter);
        intent.putExtra("requestCode", requestCode);
        context.sendBroadcast(intent);
    }

    /**
     * 开始播放
     * @param context
     */
    public static void playStart(Context context)
    {
        sendBroadcast(context, ConstVal.MUSIC_PLAY_START, ".MUSIC_RECEIVER");
    }

    /**
     * 播放下一首
     * @param context
     */
    public static void playNext(Context context)
    {
        sendBroadcast(context, ConstVal.MUSIC_PLAY_NEXT, ".MUSIC_RECEIVER");
    }

    /**
     * 播放上一首
     * @param context
     */
    public static void playPrev(Context context)
    {
        sendBroadcast(context, ConstVal.MUSIC_PLAY_PREV, ".MUSIC_RECEIVER");
    }

    /**
     * 播放/暂停切换
     * @param context
     */
    public static void playToggle(Context context)
    {
        sendBroadcast(context, ConstVal.MUSIC_PLAY_TOGGLE, ".MUSIC_RECEIVER");
    }

    /**
     * 关闭
     * @param context
     */
    public static void playClose(Context context)
    {
        sendBroadcast(context, ConstVal.MUSIC_PLAY_CLOSE, ".MUSIC_RECEIVER");
    }

    /**
     * 获取详细信息
     * @param context
     */
    public static void playGetDetail(Context context)
    {
        sendBroadcast(context, ConstVal.GET_CURRENT_MUSIC_DETAIL, ".MUSIC_RECEIVER");
    }

    /**
     * 发送歌曲长度
     * @param context
     */
    public static void playSendDuration(Context context, int duration)
    {
        Intent intent = new Intent(context.getPackageName() + ".DETAIL_RECEIVER");
        intent.putExtra("requestCode", ConstVal.DETAIL_DURATION);
        intent.putExtra("duration", duration);
        context.sendBroadcast(intent);
    }

    /**
     * 发送播放状态
     * @param context
     */
    public static void playSendState(Context context)
    {
        Intent intent = new Intent(context.getPackageName() + ".DETAIL_RECEIVER");
        intent.putExtra("requestCode", ConstVal.DETAIL_STATE);
        context.sendBroadcast(intent);
    }

    /**
     * 发送播放状态
     * @param context
     */
    public static void playSendMusicianState(Context context)
    {
        Intent intent = new Intent(context.getPackageName() + ".MUSICIAN_RECEIVER");
        context.sendBroadcast(intent);
    }

    /**
     * 发送歌曲进度
     * @param context
     * @param progress
     */
    public static void playSendProgress(Context context, int progress)
    {
        Intent intent = new Intent(context.getPackageName() + ".DETAIL_RECEIVER");
        intent.putExtra("requestCode", ConstVal.DETAIL_PROGRESS);
        intent.putExtra("progress", progress);
        context.sendBroadcast(intent);
    }

    public static void playShowItem(Context context)
    {
        Intent intent = new Intent(context.getPackageName() + ".SHOW_SELECT_RECEIVER");
        intent.putExtra("requestCode", ConstVal.SHOW_SELECT_ITEM);
        context.sendBroadcast(intent);
    }

    public static void playShowSelect(Context context)
    {
        Intent intent = new Intent(context.getPackageName() + ".PLAYING_ITEM_RECEIVER");
        intent.putExtra("requestCode", MusicData.music_play_idx);
        context.sendBroadcast(intent);
    }

    public static void playAuthorSelect(Context context)
    {
        Intent intent = new Intent(context.getPackageName() + ".AUTHOR_ITEM_RECEIVER");
        intent.putExtra("requestCode", MusicData.music_play_idx);
        context.sendBroadcast(intent);
    }
}


