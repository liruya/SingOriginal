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
    private static String netType = null;

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectManager
                = (ConnectivityManager) context.getApplicationContext()
                                               .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectManager == null)
        {
            netType = null;
            return false;
        }
        NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
        if (netInfo == null)
        {
            netType = null;
            return false;
        }
        if (netInfo.getState() == NetworkInfo.State.CONNECTED)
        {
            netType = netInfo.getTypeName();
            return true;
        }
        netType = null;
        return false;
    }

    public static String getNetType()
    {
        return netType;
    }
}
