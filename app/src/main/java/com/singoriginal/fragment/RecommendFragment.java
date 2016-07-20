package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.AdvertAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Advert;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment
{
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
        //音乐.推荐.轮播图数据api接口地址
        String path = ConstVal.ADVERT_LINK + "&version=" + ConstVal.VERSION;
        //创建OkHttpClient对象并实例化
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        //创建一个Call对象
        Call call = httpClient.newCall(request);

        imgLinks = new ArrayList<>();
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String json = response.body().string();
                Gson gson = new Gson();
                ArrayList<Advert> ads = gson.fromJson(json, new TypeToken<ArrayList<Advert>>(){}.getType());
                for (Advert ad : ads)
                {
                    imgLinks.add(ad.getImgUrl());
                }
                adapter = new AdvertAdapter(getContext(), imgLinks);
                recmd_vp_show.setAdapter(adapter);
            }
        });
    }
}
