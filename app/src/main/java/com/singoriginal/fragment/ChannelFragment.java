package com.singoriginal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ChannelAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/7/19.
 */
public class ChannelFragment extends Fragment {

    private RecyclerView channel_recyclerView;
    private List<Channel> dataList;
    private ChannelAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    Toast.makeText(getActivity(), "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    adapter = new ChannelAdapter(getActivity(), dataList);
                    channel_recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    public ChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, null);

        initView(view);
        initData();

        return view;
    }

    private void initData() {

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        channel_recyclerView.setLayoutManager(glm);

        new Thread(new Runnable() {
            @Override
            public void run() {

                Gson gson = new Gson();

                Channel channel = new Channel();
                channel.setItem_channel_text("态度");
                String channelText = gson.toJson(channel);
                Channel channel2 = gson.fromJson(channelText,Channel.class);

                dataList = new ArrayList<Channel>();
                dataList.add(channel2);
                String channel3 = gson.toJson(dataList);


                dataList= gson.fromJson(channel3, new TypeToken<List<Channel>>(){}.getType());



                if (dataList.size() > 0) {
                    handler.sendEmptyMessage(1);
                } else {

                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private void initView(View view) {

        channel_recyclerView = (RecyclerView) view.findViewById(R.id.channel_recyclerView);

    }

}
