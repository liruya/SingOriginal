package com.singoriginal.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.SonglistAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Hotlist;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.NetUtil;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class SonglistFragment extends Fragment
{
    private static boolean isDataLoaded;
    private View song_inc_title;
    private RecyclerView song_rv_show;
    private ArrayList<Hotlist> list;
    private SonglistAdapter adapter;
    private Handler hdl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view;
        if (NetUtil.isNetworkAvailable(getContext()) || adapter != null)
        {
            view = inflater.inflate(R.layout.fragment_songlist, null);
            initView(view);
            if (!isDataLoaded)
            {
                initData();
            }
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_default, null);
        }
        return view;
    }

    /**
     * 乐库.歌单页面组件初始化
     * @param view
     */
    private void initView(View view)
    {
        song_inc_title = view.findViewById(R.id.song_inc_title);
        song_rv_show = (RecyclerView) view.findViewById(R.id.song_rv_show);
        TextView tv_title = (TextView) song_inc_title.findViewById(R.id.recmd_item_title);
        TextView tv_more = (TextView) song_inc_title.findViewById(R.id.recmd_item_more);
        tv_title.setText(R.string.recommend);
        tv_more.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.describe_more, 0);
        tv_more.setMaxHeight(100);
        tv_more.setBackgroundResource(R.drawable.text_border);
        tv_more.setText(getString(R.string.select_category));

        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        song_rv_show.setLayoutManager(glm);
    }

    /**
     * 乐库.歌单页面数据初始化
     */
    private void initData()
    {

        hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case ConstVal.HOTLIST_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json), new TypeToken<ArrayList<Hotlist>>()
                        {
                        }.getType());
                        adapter = new SonglistAdapter(getContext(), list);
                        song_rv_show.setAdapter(adapter);
                        break;
                }
            }
        };
        if (adapter == null)
        {
            list = new ArrayList<>();
            Request request = new Request.Builder().url(ConstVal.HOTLIST_LINK + "&version" + ConstVal.VERSION).build();
            OkHttpUtil.enqueue(getContext(), hdl, ConstVal.HOTLIST_CODE, request);
        }
        else
        {
            song_rv_show.setAdapter(adapter);
        }
    }

}
