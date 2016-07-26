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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.DynamicAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.DynamicSquare;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;

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
    private Handler handler;

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
        handler = new Handler() {
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

        Bundle bundle = getArguments();
        if (bundle != null) {

            int code = bundle.getInt("code");
            host(code);
        }

    }

    public void host(int code) {

        String path = "";
        if (code == 0) {
            path = ConstVal.DYNAMIC_HOST_HTTP_PATH;
        } else {
            path = ConstVal.DYNAMIC_NEW_HTTP_PATH;
        }
        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(path).build();
        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request);
    }

    private void initEvent(View view) {

    }

    private void initView(View view) {

        dynamic_square_recyclerView = (RecyclerView) view.findViewById(R.id.dynamic_square_recyclerView);
    }

}
