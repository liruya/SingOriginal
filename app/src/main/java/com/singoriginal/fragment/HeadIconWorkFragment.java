package com.singoriginal.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.adapter.HeadIconWorkAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.Music;
import com.singoriginal.util.MusicUtil;
import com.singoriginal.util.OkHttpUtil;

import java.util.ArrayList;

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
    private ArrayList<Music> musicList;

    private String SUID;
    private boolean isPress = true;
    private int vsIdx = -1;
    private AuthorItemReceiver receiver;

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
        receiver = new AuthorItemReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getContext().getPackageName() + ".AUTHOR_ITEM_RECEIVER");
        getContext().registerReceiver(receiver, filter);

        return view;
    }

    @Override
    public void onDestroy()
    {
        if (receiver != null)
        {
            getContext().unregisterReceiver(receiver);
        }
        super.onDestroy();
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

//        //ListView的Item点击事件
//        headIcon_work_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (vsIdx >= parent.getFirstVisiblePosition() && vsIdx <= parent.getLastVisiblePosition()) {
//                    parent.getChildAt(vsIdx - parent.getFirstVisiblePosition())
//                            .findViewById(R.id.item_headWork_view)
//                            .setVisibility(View.INVISIBLE);
//                }
//                vsIdx = position;
//                view.findViewById(R.id.item_headWork_view).setVisibility(View.VISIBLE);
//
//                MusicData.music_play_idx = (int) id;
//                MusicUtil.playStart(getContext());
//            }
//        });
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

        headIcon_work_listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (workList != null && workList.size() > 0)
                {
                    if (MusicData.musicList == null)
                    {
                        MusicData.musicList = new ArrayList<>();
                    }
                    MusicData.musicList.clear();
                    for (HeadIconWork.Data data : workList)
                    {
                        MusicData.musicList.add(MusicUtil.convertMusicType(getContext(), data));
                    }
                    MusicData.music_play_idx = (int) id;
                    MusicUtil.playStart(getContext());
                }
                int first = parent.getFirstVisiblePosition();
                int last = parent.getLastVisiblePosition();
                showItemSelect(first, last, vsIdx, false);
                vsIdx = position;
                showItemSelect(first, last, vsIdx, true);
            }
        });

        headIcon_work_listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount)
            {
                int first = headIcon_work_listView.getFirstVisiblePosition();
                int last = headIcon_work_listView.getLastVisiblePosition();
                if (vsIdx >= first && vsIdx <= last)
                {
                    for (int i = first; i <= last; i++)
                    {
                        if (vsIdx == i)
                        {
                            showItemSelect(first, last, i, true);
                        }
                        else
                        {
                            showItemSelect(first, last, i, false);
                        }
                    }
                }
            }
        });
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
                        musicList = new ArrayList<>();
                        for (HeadIconWork.Data data : workList)
                        {
                            musicList.add(MusicUtil.convertMusicType(getContext(), data));
                        }
                        MusicUtil.playAuthorSelect(getContext());
                        break;
                }
            }
        };
    }

    private boolean isEqual(ArrayList<Music> src, ArrayList<Music> des)
    {
        if (src == null || src.size() == 0
            || des == null || des.size() == 0
            || src.size() != src.size())
        {
            return  false;
        }
        for (int i = 0; i < src.size(); i++)
        {
            if (!src.get(i).getSongid().equals(des.get(i).getSongid()))
            {
                return false;
            }
            if (!src.get(i).getSongtype().equals(des.get(i).getSongtype()))
            {
                return false;
            }
            if (!src.get(i).getSongname().equals(des.get(i).getSongname()))
            {
                return false;
            }
            if (src.get(i).getUserid() != des.get(i).getUserid())
            {
                return false;
            }
            if (!src.get(i).getUsername().equals(des.get(i).getUsername()))
            {
                return false;
            }
            if (!src.get(i).getUserimg().equals(des.get(i).getUserimg()))
            {
                return false;
            }
        }
        return true;
    }

    private void showItemSelect(int first, int last, int position, boolean selected)
    {
        if (position >= first && position <= last)
        {
            View view = headIcon_work_listView.getChildAt(position - first);
            TextView tv_song = (TextView) view.findViewById(R.id.item_headWork_songName);
            TextView tv_time = (TextView) view.findViewById(R.id.item_headWork_time);
            if (selected)
            {
                view.findViewById(R.id.item_headWork_view).setVisibility(View.VISIBLE);
                tv_song.setTextColor(ConstVal.COLOR_DARKGREEN);
                tv_time.setTextColor(ConstVal.COLOR_DARKGREEN);
            }
            else
            {
                view.findViewById(R.id.item_headWork_view).setVisibility(View.INVISIBLE);
                tv_song.setTextColor(ConstVal.COLOR_SHALLOWBLACK);
                tv_time.setTextColor(ConstVal.COLOR_GRAY);
            }

        }
    }

    class AuthorItemReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int result = intent.getIntExtra("requestCode", -1);
            if (result >= 0)
            {
                if (isEqual(MusicData.musicList, musicList))
                {
                    int first = headIcon_work_listView.getFirstVisiblePosition();
                    int last = headIcon_work_listView.getLastVisiblePosition();
                    showItemSelect(first, last, vsIdx, false);
                    vsIdx = result;
                    showItemSelect(first, last, vsIdx, true);
                }
            }
        }
    }

}
