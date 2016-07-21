package com.singoriginal.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.AdvertAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Advert;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment
{
    //Log输出标签
    private static String TAG = "RecommendFragment";
    private ViewPager recmd_vp_show;
    private ArrayList<String> imgLinks;
    private AdvertAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view)
    {
        recmd_vp_show = (ViewPager) view.findViewById(R.id.recmd_vp_show);
    }

    private void initData()
    {
        //空数据初始化适配器
        imgLinks = new ArrayList<>();
        adapter = new AdvertAdapter(getContext(), imgLinks);
        recmd_vp_show.setAdapter(adapter);

        //音乐.推荐.轮播图数据api接口地址
        String path = ConstVal.ADVERT_LINK + "&version=" + ConstVal.VERSION;
        final Gson gson = new Gson();
        Handler hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    //轮播图api数据码
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        ArrayList<Advert> ads = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                                    new TypeToken<ArrayList<Advert>>(){}.getType());
                        for (Advert ad : ads)
                        {
                            imgLinks.add(ad.getImgUrl());
                        }
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(path).build();
        OkHttpUtil.enqueue(getContext(), hdl, request);
    }


}
