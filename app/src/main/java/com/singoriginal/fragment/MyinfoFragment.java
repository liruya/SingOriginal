package com.singoriginal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.activity.CollectionSongActivity;
import com.singoriginal.activity.DownloadActivity;
import com.singoriginal.activity.FortuneCenterActivity;
import com.singoriginal.activity.InfoActivity;
import com.singoriginal.activity.LocalSongsActivity;
import com.singoriginal.activity.MySongActivity;
import com.singoriginal.activity.MyWorkActivity;
import com.singoriginal.activity.SetUpActivity;
import com.singoriginal.activity.UploadSongsActivity;
import com.singoriginal.adapter.InfoAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyinfoFragment extends Fragment implements View.OnClickListener {
    private ArrayList<View> list;
    private TextView my_tv_msg;
    private TextView my_tv_wealth;
    private TextView my_tv_upload;
    private TextView my_tv_local;
    private TextView my_tv_song;
    private TextView my_tv_list;
    private TextView my_tv_collect;
    private TextView my_tv_download;
    private TextView my_tv_set;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo, null);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {

        my_tv_msg.setOnClickListener(this);
        my_tv_wealth.setOnClickListener(this);
        my_tv_upload.setOnClickListener(this);
        my_tv_local.setOnClickListener(this);
        my_tv_song.setOnClickListener(this);
        my_tv_list.setOnClickListener(this);
        my_tv_collect.setOnClickListener(this);
        my_tv_download.setOnClickListener(this);
        my_tv_set.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.my_tv_msg:
                intent.setClass(getActivity(), InfoActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_wealth:
                intent.setClass(getActivity(), FortuneCenterActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_upload:
                intent.setClass(getActivity(), UploadSongsActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_local:
                intent.setClass(getActivity(), LocalSongsActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_song:
                intent.setClass(getActivity(), MyWorkActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_list:
                intent.setClass(getActivity(), MySongActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_collect:
                intent.setClass(getActivity(), CollectionSongActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_download:
                intent.setClass(getActivity(), DownloadActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.my_tv_set:
                intent.setClass(getActivity(), SetUpActivity.class);
                getContext().startActivity(intent);
                break;

        }
    }

    private void initView(View view) {

        my_tv_msg = (TextView) view.findViewById(R.id.my_tv_msg);
        my_tv_wealth = (TextView) view.findViewById(R.id.my_tv_wealth);
        my_tv_upload = (TextView) view.findViewById(R.id.my_tv_upload);
        my_tv_local = (TextView) view.findViewById(R.id.my_tv_local);
        my_tv_song = (TextView) view.findViewById(R.id.my_tv_song);
        my_tv_list = (TextView) view.findViewById(R.id.my_tv_list);
        my_tv_collect = (TextView) view.findViewById(R.id.my_tv_collect);
        my_tv_download = (TextView) view.findViewById(R.id.my_tv_download);
        my_tv_set = (TextView) view.findViewById(R.id.my_tv_set);

        list = new ArrayList<>();
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.info_page1, null);
        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.info_page2, null);
        list.add(view1);
        list.add(view2);
        InfoAdapter adapter = new InfoAdapter(list);
        ViewPager my_vp_info = (ViewPager) view.findViewById(R.id.my_vp_info);
        my_vp_info.setAdapter(adapter);
        int h = getViewHeight(view1);
        //创建一个layoutparams对象
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) my_vp_info.getLayoutParams();
        linearParams.height = h;
        my_vp_info.setLayoutParams(linearParams);
    }

    /**
     * 测量控件高度
     *
     * @param view
     * @return
     */
    private int getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }
}
