package com.singoriginal.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp网络工具类,OkHttp不建议创建多个OkHttpClient
 * Created by lanouhn on 16/7/21.
 */
public class OkHttpUtil
{
    private static final String TAG = "OkHttpUtil";
    private static final OkHttpClient httpClient = new OkHttpClient();

    /**
     * 非异步线程
     * @param request
     * @return Response转换为String类型
     * @throws IOException
     */
    public static String execute(Request request) throws IOException
    {
        return httpClient.newCall(request).execute().body().string();
    }

    public static void enqueue(final Context context, final Handler hdl, final int code, Request request)
    {
        httpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                final String json = response.body().string();
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Message msg = hdl.obtainMessage(code, json);
                        hdl.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

}
