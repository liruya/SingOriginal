package com.singoriginal.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.adapter.HeadIconMessageAdapter;
import com.singoriginal.adapter.HeadIconWorkAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.HeadIconMessage;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadIconMessageFragment extends Fragment {

    private ListView headIcon_message_listView;
    private Handler handler;
    private HeadIconMessageAdapter adapter;
    private HeadIconMessage message;
    private ArrayList<HeadIconMessage.Data> messageList;

    private String SUID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_head_icon_work, null);

        Bundle bundle = this.getArguments();
        SUID = bundle.getString("SUID");

        Log.i("SUID", SUID);

        initView(view);
        setData();

        return view;
    }

    private void setData() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        message = new Gson().fromJson(json, HeadIconMessage.class);
                        messageList = new ArrayList<>();
                        for (int i = 0; i < message.getData().length; i++) {

                            messageList.add(message.getData()[i]);
                        }
                        adapter = new HeadIconMessageAdapter(getContext(), messageList);
                        headIcon_message_listView.setAdapter(adapter);

                        break;
                }
            }
        };

        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(ConstVal.HEADICON_MESSAGE_HTTP_PATH +
                SUID + ConstVal.HEADICON_MESSAGE_HTTP_PARAM + "&version" + ConstVal.VERSION2).build();

        Log.i("request", ConstVal.HEADICON_MESSAGE_HTTP_PATH +
                SUID + ConstVal.HEADICON_MESSAGE_HTTP_PARAM + "&version" + ConstVal.VERSION2);
        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request);

    }

    private void initView(View view) {

        headIcon_message_listView = (ListView) view.findViewById(R.id.headIcon_message_listView);
    }

}
