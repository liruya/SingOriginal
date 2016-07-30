package com.singoriginal.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.TopRankAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.TopRank;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.NetUtil;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * 乐库.排行榜
 * A simple {@link Fragment} subclass.
 */
public class ToplistFragment extends Fragment
{
    private RecyclerView top_rv_show;
    private ArrayList<TopRank> tops;
    private TopRankAdapter adapter;
    private Handler hdl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view;
        if (NetUtil.isNetworkAvailable(getContext()) || adapter != null)
        {
            view = inflater.inflate(R.layout.fragment_toplist, null);
            initView(view);
            initData();
        }
        else
        {
            view = inflater.inflate(R.layout.fragment_default, null);
        }
        return view;
    }

    private void initView(View view)
    {
        top_rv_show = (RecyclerView) view.findViewById(R.id.top_rv_show);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        top_rv_show.setLayoutManager(lm);
    }

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
                    case ConstVal.RANK_CODE:
                        tops = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<TopRank>>(){}.getType());
                        adapter = new TopRankAdapter(getContext(), tops);
                        top_rv_show.setAdapter(adapter);
                        break;
                }
            }
        };
        if (adapter == null)
        {
            tops = new ArrayList<>();
            Request request = new Request.Builder().url(ConstVal.TOPRANK_LINK + "&version" + ConstVal.VERSION).build();
            OkHttpUtil.enqueue(getContext(), hdl, ConstVal.RANK_CODE, request);
        }
        else
        {
            top_rv_show.setAdapter(adapter);
        }
    }
}
