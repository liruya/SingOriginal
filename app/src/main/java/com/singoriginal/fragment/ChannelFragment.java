package com.singoriginal.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ChannelAdapter;
import com.singoriginal.constant.CommanVal;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lanouhn on 16/7/19.
 */
public class ChannelFragment extends Fragment {

    private RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, null);

        initView(view);

        return view;
    }

    private void initView(View view) {

        //如果已登录则显示个人信息页面,否则显示登录注册页面 标题头及页面主体均不相同
        //页面公用标题头初始化
        View incView = view.findViewById(R.id.channel_include_header);

        incView.findViewById(R.id.hdr_ib_srch).setVisibility(View.INVISIBLE);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.GONE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setText(getString(R.string.channel));
        tv_second.setChecked(true);

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.GONE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
        Toast.makeText(getContext(), CommanVal.isLogin + "", Toast.LENGTH_SHORT).show();
        //"频道"主体页面
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        Fragment frag;
        if (CommanVal.isLogin) {
            frag = new ChannelInfoFragment();
        } else {
            frag = new NotloginFragment();
        }
        beginTransaction.replace(R.id.channel_frameLayout, frag).commit();
    }
}
