package com.singoriginal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.DynamicAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.DynamicSquare;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by lanouhn on 16/7/19.
 */
public class DynamicSquareFragment extends Fragment {

    private RecyclerView dynamic_square_recyclerView;
    private List<DynamicSquare> dataList;
    private DynamicAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dynamic_square, null);

        initView(view);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        dynamic_square_recyclerView.setLayoutManager(manager);

        initData();

        initEvent(view);
        return view;
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

                        dataList = new Gson().fromJson(GsonUtil.getJsonArray(json), new TypeToken<ArrayList<DynamicSquare>>() {
                        }.getType());

                        adapter = new DynamicAdapter(getContext(), dataList);
                        dynamic_square_recyclerView.setAdapter(adapter);

                        break;
                }
            }
        };
        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(ConstVal.Dynamic_HTTP_PATH).build();
        OkHttpUtil.enqueue(getContext(), handler, request);
    }

    private void initEvent(View view) {

    }

    private void initView(View view) {

        dynamic_square_recyclerView = (RecyclerView) view.findViewById(R.id.dynamic_square_recyclerView);
    }

}
