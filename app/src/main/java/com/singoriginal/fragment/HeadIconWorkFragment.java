package com.singoriginal.fragment;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.activity.HeadIconActivity;
import com.singoriginal.activity.SongDetailsActivity;
import com.singoriginal.activity.SongListActivity;
import com.singoriginal.adapter.HeadIconWorkAdapter;
import com.singoriginal.adapter.SongDetailsAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.HeadIconInfo;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.SongDetails;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.MusicUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadIconWorkFragment extends Fragment {

    private ListView headIcon_work_listView;
    private LinearLayout headIcon_work_ll;
    private TextView headIcon_work_tv;
    private ImageView headIcon_work_bracket;
    private ImageView headIcon_iv_setList;
    private RadioGroup headIcon_work_rg;
    private RadioButton headIcon_work_rbOriginal;
    private RadioButton headIcon_work_rbCover;
    private RadioButton headIcon_work_rbAccompaniment;
    private Handler handler;
    private HeadIconWorkAdapter adapter;
    private HeadIconWork work;
    private ArrayList<HeadIconWork.Data> workList;

    private String SUID;
    private boolean isPress = true;
    private int vsIdx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_head_icon_work, null);

        Bundle bundle = this.getArguments();
        SUID = bundle.getString("SUID");

        initView(view);
        setData();
        initEvent();

        return view;
    }

    private void initEvent() {

        //点击是否显示歌曲类型栏
        headIcon_work_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPress) {
                    headIcon_work_rg.setVisibility(View.VISIBLE);
                    headIcon_work_bracket.setImageResource(R.mipmap.visitor_zp_select_up);
                } else {
                    headIcon_work_rg.setVisibility(View.GONE);
                    headIcon_work_bracket.setImageResource(R.mipmap.visitor_zp_select_down);
                }

                isPress = !isPress;
            }
        });

        headIcon_work_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.headIcon_work_rbOriginal:
                        headIcon_work_tv.setText("原创");
                        //创建OkHttpClient请求
                        final Request request = new Request.Builder().url(ConstVal.HEADICON_WORK_HTTP_PATH + SUID + "&songtype=" + "yc" +
                                ConstVal.HEADICON_WORK_HTTP_PARAM1 + ConstVal.HEADICON_WORK_HTTP_PARAM2).build();
                        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request);
                        break;
                    case R.id.headIcon_work_rbCover:
                        headIcon_work_tv.setText("翻唱");
                        //创建OkHttpClient请求
                        final Request request2 = new Request.Builder().url(ConstVal.HEADICON_WORK_HTTP_PATH + SUID + "&songtype=" + "fc" +
                                ConstVal.HEADICON_WORK_HTTP_PARAM1 + ConstVal.HEADICON_WORK_HTTP_PARAM2).build();
                        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request2);
                        break;
                    case R.id.headIcon_work_rbAccompaniment:
                        headIcon_work_tv.setText("伴奏");
                        //创建OkHttpClient请求
                        final Request request3 = new Request.Builder().url(ConstVal.HEADICON_WORK_HTTP_PATH + SUID + "&songtype=" + "bz" +
                                ConstVal.HEADICON_WORK_HTTP_PARAM1 + ConstVal.HEADICON_WORK_HTTP_PARAM2).build();
                        OkHttpUtil.enqueue(getContext(), handler, ConstVal.ADVERT_CODE, request3);
                        break;
                }
            }
        });
        headIcon_work_rg.check(R.id.headIcon_work_rbCover);

        //ListView的Item点击事件
        headIcon_work_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (vsIdx >= parent.getFirstVisiblePosition() && vsIdx <= parent.getLastVisiblePosition()) {
                    parent.getChildAt(vsIdx - parent.getFirstVisiblePosition())
                            .findViewById(R.id.item_headWork_view)
                            .setVisibility(View.INVISIBLE);
                }
                vsIdx = position;
                view.findViewById(R.id.item_headWork_view).setVisibility(View.VISIBLE);

                MusicData.music_play_idx = (int) id;
                MusicUtil.playStart(getContext());
            }
        });
    }

    private void initView(View view) {

        headIcon_work_listView = (ListView) view.findViewById(R.id.headIcon_work_listView);
        headIcon_work_ll = (LinearLayout) view.findViewById(R.id.headIcon_work_ll);
        headIcon_work_tv = (TextView) view.findViewById(R.id.headIcon_work_tv);
        headIcon_work_bracket = (ImageView) view.findViewById(R.id.headIcon_work_bracket);
        headIcon_iv_setList = (ImageView) view.findViewById(R.id.headIcon_iv_setList);
        headIcon_work_rg = (RadioGroup) view.findViewById(R.id.headIcon_work_rg);
        headIcon_work_rbOriginal = (RadioButton) view.findViewById(R.id.headIcon_work_rbOriginal);
        headIcon_work_rbCover = (RadioButton) view.findViewById(R.id.headIcon_work_rbCover);
        headIcon_work_rbAccompaniment = (RadioButton) view.findViewById(R.id.headIcon_work_rbAccompaniment);
    }

    private void setData() {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        work = new Gson().fromJson(json, HeadIconWork.class);
                        workList = new ArrayList<>();
                        for (int i = 0; i < work.getData().length; i++) {

                            workList.add(work.getData()[i]);
                        }
                        adapter = new HeadIconWorkAdapter(getContext(), workList);
                        headIcon_work_listView.setAdapter(adapter);

                        break;
                }
            }
        };
    }

}
