package com.singoriginal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 * Created by lanouhn on 16/7/28.
 */
public class NetUtil
{
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectManager
                = (ConnectivityManager) context.getApplicationContext()
                                               .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectManager == null)
        {
            return false;
        }
        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
        if (netInfo == null)
        {
            return false;
        }
        if (netInfo.getState() == NetworkInfo.State.CONNECTED)
        {
            return true;
        }
        return false;
    }
}
