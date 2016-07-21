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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ChannelAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
        initData();
        initEvent(view);

        return view;
    }

    private void initEvent(View view) {


    }

    private void initData() {

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        channel_info_recyclerView.setLayoutManager(glm);

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        dataList = new ArrayList<Channel>();

        JsonObject jsonObject = parser.parse(ConstVal.CHANNEL_HTTP_PATH).getAsJsonObject();

        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement el = jsonArray.get(i);
            Channel channel = gson.fromJson(el, type);
            dataList.add(channel);
            System.out.println(channel.getNA());
        }
        jsonObject.add("data", parser.parse(gson.toJson(dataList)).getAsJsonArray());
        System.out.println(gson.toJson(jsonObject));

        adapter = new ChannelAdapter(getActivity(), dataList);
        channel_info_recyclerView.setAdapter(adapter);
    }

    private void initView(View view) {

        channel_info_recyclerView = (RecyclerView) view.findViewById(R.id.channel_info_recyclerView);
    }

}
