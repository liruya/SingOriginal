package com.singoriginal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.activity.MusicianActivity;
import com.singoriginal.activity.SongListActivity;
import com.singoriginal.activity.WebActivity;
import com.singoriginal.adapter.AdvertAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Advert;
import com.singoriginal.model.Hotlist;
import com.singoriginal.model.Liveroom;
import com.singoriginal.model.MusicTopics;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.NetUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * 乐库.推荐页面
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment
{
    private static boolean isDataLoaded;
    //Log输出标签
    private static final String TAG = "RecommendFragment";
    private ViewPager recmd_vp_show;
    private Button recmd_btn_mscian;
    private Button recmd_btn_daily;
    private Button recmd_btn_rank;
    private View rec_inc_hot;
    private View rec_inc_live;
    private View rec_inc_topic;

    private ArrayList<Advert> advertList;
    private AdvertAdapter adapter;

    private ArrayList<Hotlist> hotList;
    private ArrayList<Liveroom> liveList;
    private ArrayList<MusicTopics> topicList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        if (NetUtil.isNetworkAvailable(getContext()))
        {
            initView(view);
            if (!isDataLoaded)
            {
                initData(view);
            }
            initEvent();
        }
        return view;
    }

    private void initView(View view)
    {
        recmd_vp_show = (ViewPager) view.findViewById(R.id.recmd_vp_show);
        recmd_btn_mscian = (Button) view.findViewById(R.id.recmd_btn_mscian);
        recmd_btn_daily = (Button) view.findViewById(R.id.recmd_btn_daily);
        recmd_btn_rank = (Button) view.findViewById(R.id.recmd_btn_rank);
        TableRow hot_tr1 = (TableRow) view.findViewById(R.id.hot_tr1);
        TableRow hot_tr2 = (TableRow) view.findViewById(R.id.hot_tr2);
        TableRow live_tr1 = (TableRow) view.findViewById(R.id.live_tr1);
        TableRow live_tr2 = (TableRow) view.findViewById(R.id.live_tr2);
        hot_tr1.getLayoutParams().height = hot_tr1.getLayoutParams().width/3;
        hot_tr1.setLayoutParams(hot_tr1.getLayoutParams());
        hot_tr2.getLayoutParams().height = hot_tr2.getLayoutParams().width/3;
        live_tr1.getLayoutParams().height = live_tr1.getLayoutParams().width/2;
        live_tr2.getLayoutParams().height = live_tr2.getLayoutParams().width/2;
        rec_inc_hot = view.findViewById(R.id.rec_inc_hot);
        rec_inc_live = view.findViewById(R.id.rec_inc_live);
        rec_inc_topic = view.findViewById(R.id.rec_inc_topic);
        TextView tv_title = (TextView) rec_inc_live.findViewById(R.id.recmd_item_title);
        tv_title.setText(getString(R.string.live_room));
        tv_title = (TextView) rec_inc_topic.findViewById(R.id.recmd_item_title);
        tv_title.setText(getString(R.string.music_topic));

        TableLayout.LayoutParams lp = (TableLayout.LayoutParams) recmd_vp_show.getLayoutParams();
        lp.height = (int) (ConstVal.SCREEN_WIDTH * 0.4);
        recmd_vp_show.setLayoutParams(lp);
    }

    private void initData(final View view)
    {
        //空数据初始化适配器
        advertList = new ArrayList<>();
        hotList = new ArrayList<>();

        //音乐.推荐.轮播图数据api接口地址
        Handler hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                int width;
                int height;
                switch (msg.what)
                {
                    //轮播图api数据码
                    case ConstVal.ADVERT_CODE:
                        advertList = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                            new TypeToken<ArrayList<Advert>>(){}.getType());
                        adapter = new AdvertAdapter(getContext(), advertList);
                        recmd_vp_show.setAdapter(adapter);
                        recmd_vp_show.setCurrentItem(advertList.size()*1000);
                        break;

                    case ConstVal.HOTLIST_CODE:
                        hotList = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                         new TypeToken<ArrayList<Hotlist>>(){}.getType());
                        width = ConstVal.SCREEN_WIDTH/3;
                        height = width;
                        for (int i = 0; i < 6; i++)
                        {
                            View rec = view.findViewById(ConstVal.HOTLIST_RESID[i]);
                            if (i >= hotList.size())
                            {
                                rec.setVisibility(View.GONE);
                                break;
                            }
                            final Hotlist hot = hotList.get(i);
                            ImageView iv_show = (ImageView) rec.findViewById(R.id.rec_iv_show);
                            TextView tv_num = (TextView) rec.findViewById(R.id.rec_tv_num);
                            Picasso.with(getContext())
                                   .load(hot.getPicture())
                                   .placeholder(R.mipmap.default_image_6_0)
                                   .resize(width, height)
                                   .centerCrop()
                                   .into(iv_show);
                            tv_num.setText(hot.getPlayCount()+"");
                            tv_num.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.dj_listener, 0, 0, 0);
                            rec.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    Intent intent = new Intent(getContext(), SongListActivity.class);
                                    intent.putExtra("LinkUrl", hot.getSongListId());
                                    intent.putExtra("title", hot.getTitle());
                                    intent.putExtra("code", ConstVal.SONGLIST_DETAIL_CODE);
                                    getContext().startActivity(intent);
                                }
                            });
                            //标题
                            TextView tv_name = (TextView) view.findViewById(ConstVal.HOTLIST_NAMEID[i]);
                            tv_name.setText(hot.getTitle());
                        }
                        break;

                    case ConstVal.LIVEROOM_CODE:
                        liveList = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                       new TypeToken<ArrayList<Liveroom>>(){}.getType());
                        width = ConstVal.SCREEN_WIDTH/2;
                        height = (int) (width * 0.6);
                        for (int i = 0; i < 4; i++)
                        {
                            View rc = view.findViewById(ConstVal.LIVEROOM_RESID[i]);
                            if (i >= liveList.size())
                            {
                                rc.setVisibility(View.GONE);
                                break;
                            }
                            Liveroom live = liveList.get(i);
                            ImageView iv_show = (ImageView) rc.findViewById(R.id.rec_iv_show);
                            ImageView iv_stat = (ImageView) rc.findViewById(R.id.rec_iv_stat);
                            TextView tv_num = (TextView) rc.findViewById(R.id.rec_tv_num);
                            Picasso.with(getContext()).load(live.getImgPath()).resize(width, height).centerCrop().into(iv_show);
                            int resid = R.mipmap.main_rec_prelive_preshow_icon;
                            switch (live.getType())
                            {
                                case ConstVal.LIVE_STAT_PRESHOW:
                                    resid = R.mipmap.main_rec_prelive_preshow_icon;
                                    break;
                                case ConstVal.LIVE_STAT_ONLINE:
                                    resid = R.mipmap.main_rec_prelive_online_icon;
                                    break;
                                case ConstVal.LIVE_STAT_RECOMMEND:
                                    resid = R.mipmap.main_rec_prelive_reconmmend_icon;
                                    break;
                            }
                            iv_stat.setVisibility(View.VISIBLE);
                            Picasso.with(getContext()).load(resid).into(iv_stat);
                            tv_num.setText("粉丝" + live.getFans());
                            //标题
                            TextView tv_name = (TextView) view.findViewById(ConstVal.LIVEROOM_NAMEID[i]);
                            tv_name.setText(live.getNickName());
                            //时间
                            TextView tv_time = (TextView) view.findViewById(ConstVal.LIVEROOM_DESCID[i]);
                            tv_time.setText(live.getWeek() + " " + live.getTime());
                        }
                        break;

                    case ConstVal.TOPIC_CODE:
                        topicList = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                       new TypeToken<ArrayList<MusicTopics>>(){}.getType());
                        width = ConstVal.SCREEN_WIDTH;
                        height = (int) (width * 0.4);
                        for (int i = 0; i < 3; i++)
                        {
                            View rc = view.findViewById(ConstVal.TOPIC_RESID[i]);
                            if (i >= topicList.size())
                            {
                                rc.setVisibility(View.GONE);
                                break;
                            }
                            final MusicTopics topic = topicList.get(i);
                            ImageView iv_show = (ImageView) rc.findViewById(R.id.rec_iv_show);
                            rc.findViewById(R.id.rec_tv_num).setVisibility(View.GONE);
                            Picasso.with(getContext()).load(topic.getImgUrl()).resize(width, height).into(iv_show);
                            rc.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    Intent intent = new Intent(getContext(), WebActivity.class);
                                    intent.putExtra("LinkUrl", topic.getUrl());
                                    startActivity(intent);
                                }
                            });
                            //标题
                            TextView tv_name = (TextView) view.findViewById(ConstVal.TOPIC_NAMEID[i]);
                            tv_name.setText(topic.getTitle());
                        }
                        break;
                }
            }
        };
        //创建OkHttpClient请求
        Request request = new Request.Builder().url(ConstVal.ADVERT_LINK + "&version=" + ConstVal.VERSION).build();
        OkHttpUtil.enqueue(getContext(), hdl, ConstVal.ADVERT_CODE, request);
        request = new Request.Builder().url(ConstVal.HOTLIST_LINK + "&version=" + ConstVal.VERSION).build();
        OkHttpUtil.enqueue(getContext(), hdl, ConstVal.HOTLIST_CODE, request);
        request = new Request.Builder().url(ConstVal.LIVEROOM_LINK + "&version=" + ConstVal.VERSION).build();
        OkHttpUtil.enqueue(getContext(), hdl, ConstVal.LIVEROOM_CODE, request);
        request = new Request.Builder().url(ConstVal.MUSICTOPICS_LINK + "&version=" + ConstVal.VERSION).build();
        OkHttpUtil.enqueue(getContext(), hdl, ConstVal.TOPIC_CODE, request);
    }

    private void initEvent()
    {
        //热门直播.更多
        TextView tv_more = (TextView) rec_inc_hot.findViewById(R.id.recmd_item_more);
        tv_more.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ViewPager msc_vp_show = (ViewPager) getActivity().findViewById(R.id.msc_vp_show);
                msc_vp_show.setCurrentItem(1);
            }
        });

        //音乐专题.更多
        tv_more = (TextView) rec_inc_topic.findViewById(R.id.recmd_item_more);
        tv_more.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("LinkUrl", ConstVal.TOPICMORE_LINK);
                startActivity(intent);
            }
        });

        recmd_btn_mscian.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), MusicianActivity.class);
                startActivity(intent);
            }
        });

        recmd_btn_daily.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), SongListActivity.class);
                intent.putExtra("LinkUrl", ConstVal.DAILYRECMD_LINK
                                           + "&pagesize=20&page=1");
                intent.putExtra("title", "每日推荐");
                intent.putExtra("code", ConstVal.DAILYRECMD_CODE);
                startActivity(intent);
            }
        });

        recmd_btn_rank.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), SongListActivity.class);
                intent.putExtra("LinkUrl", ConstVal.RANKDETAIL_LINK
                                           + "&id=yc&pagesize=20&pageindex=1");
                intent.putExtra("title", "原创排行榜");
                intent.putExtra("code", ConstVal.RANKYC_CODE);
                startActivity(intent);
            }
        });
    }
}
