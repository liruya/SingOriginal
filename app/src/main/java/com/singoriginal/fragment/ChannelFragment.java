package com.singoriginal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.constant.CommanVal;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/7/19.
 */
public class ChannelFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton channel_attention;
    private RadioButton channel_total;
    private List<Channel> dataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channel, null);


        initView(view);
        initEvent(view);

        return view;
    }

    private void initEvent(View view) {


        channel_attention.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (dataList == null || dataList.size() == 0) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.channel_frameLayout, new NoAttentionChannelFragment()).commit();
                    } else {

                    }
                }
            }
        });

        channel_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.channel_frameLayout, new ChannelInfoFragment()).commit();
                }
            }
        });
        channel_attention.setChecked(true);
    }

    private void initView(View view) {

        dataList = new ArrayList<>();
        channel_attention = (RadioButton) view.findViewById(R.id.channel_attention);
        channel_total = (RadioButton) view.findViewById(R.id.channel_total);

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
