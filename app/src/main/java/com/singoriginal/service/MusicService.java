package com.singoriginal.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by lanouhn on 16/7/30.
 */
public class MusicService extends Service
{
    private static MediaPlayer mediaPlayer;
    private MyBinder myBinder;

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
        //创建MediaPlay对象,并设置准备,完成监听事件
        mediaPlayer = new MediaPlayer();
        Log.e("TAG", "onCreate: 1");
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                mp.start();
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

    }

    private void playNext()
    {

    }

    public class MyBinder extends Binder
    {

    }
}
