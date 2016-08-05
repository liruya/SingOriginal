package com.singoriginal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.activity.SongDetailsActivity;
import com.singoriginal.adapter.ChannelAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.singoriginal.onclickinterface.ChannelOnItemClickListener;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelInfoFragment extends Fragment {

    private RecyclerView channel_info_recyclerView;
    private List<Channel> dataList;
    private ChannelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel_info, null);

        initView(view);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        channel_info_recyclerView.setLayoutManager(glm);
        initData();

        return view;
    }

    private void initEvent() {

        //Item点击事件
        adapter.setMyOnItemClickListener(new ChannelOnItemClickListener() {
            @Override
            public void myOnItemClickListener(View view, int position) {

                Intent intent = new Intent(getContext(), SongDetailsActivity.class);
                intent.putExtra("detailsUrl", ConstVal.SONG_DETAILS_HTTP_PATH + dataList.get(position).getID() + "&version=" + ConstVal.VERSION2);
                intent.putExtra("IM", dataList.get(position).getIM());
                intent.putExtra("NA", dataList.get(position).getNA());
                intent.putExtra("NU", dataList.get(position).getNU());
                intent.putExtra("LI", dataList.get(position).getLI());
                getContext().startActivity(intent);
            }
        });

    }

    private void initData() {

        dataList = new ArrayList<>();

        Gson gson = new Gson();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        dataList = new Gson().fromJson(GsonUtil.getJsonArray(json), new TypeToken<ArrayList<Channel>>() {
                        }.getType());

                        adapter = new ChannelAdapter(getContext(), dataList);
                        channel_info_recyclerView.setAdapter(adapter);
                        initEvent();

                        break;
                }
            }
        };
        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(ConstVal.CHANNEL_HTTP_PATH + "&version=" + ConstVal.VERSION2).build();
        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request);
    }

    private void initView(View view) {

        channel_info_recyclerView = (RecyclerView) view.findViewById(R.id.channel_info_recyclerView);
    }

}
