package com.singoriginal.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Space;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ListSongAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.DailyRecmd;
import com.singoriginal.model.NewSong;
import com.singoriginal.model.PopularSong;
import com.singoriginal.model.RankSong;
import com.singoriginal.model.SongList;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.MusicUtil;
import com.singoriginal.util.OkHttpUtil;
import com.singoriginal.util.RtfUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class SongListActivity extends AppCompatActivity
{
    private View header;
    private View lv_header;
    private View vs_play = null;
    private View inc_play;
    private ListView songlist_lv_show;
    private ImageView songlist_iv_show;
    private ViewStub songlist_vs_play;
    private TextView songlist_tv_day;
    private TextView songlist_tv_sub;
    private View vs_author;
    private View vs_desc;

    private ViewStub lvhdr_vs_author;
    private ViewStub lvhdr_vs_desc;
    private Space lvhdr_space;

    private ListSongAdapter adapter;
    private ArrayList<Object> list;
    private Handler hdl;

    private int[] vsIdx = new int[]{-1};
    private int lvHdrheight;
    private int spcHeight;
    private int code;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        initView();
        initData();
        initEvent();
    }

    private void initView()
    {
        header = findViewById(R.id.songlist_inc_title);
        songlist_lv_show = (ListView) findViewById(R.id.songlist_lv_show);
        songlist_vs_play = (ViewStub) findViewById(R.id.songlist_vs_play);
        songlist_tv_day = (TextView) findViewById(R.id.songlist_tv_day);
        songlist_tv_sub = (TextView) findViewById(R.id.songlist_tv_sub);
        lv_header = LayoutInflater.from(SongListActivity.this).inflate(R.layout.item_lv_header, songlist_lv_show, false);
        lvhdr_vs_author = (ViewStub) lv_header.findViewById(R.id.lvhdr_vs_author);
        lvhdr_vs_desc = (ViewStub) lv_header.findViewById(R.id.lvhdr_vs_desc);
        lvhdr_space = (Space) lv_header.findViewById(R.id.lvhdr_space);

        inc_play = lv_header.findViewById(R.id.lvhdr_inc_play);
        songlist_iv_show = (ImageView) findViewById(R.id.songlist_iv_show);

        header.setBackgroundColor(0x11000000);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) songlist_iv_show.getLayoutParams();
        lp.width = ConstVal.SCREEN_WIDTH;
        lp.height = (int) (ConstVal.SCREEN_WIDTH * 0.64);
        songlist_iv_show.setLayoutParams(lp);

        int hdHeight = getViewHeight(header);

        spcHeight = (int) (ConstVal.SCREEN_WIDTH * 0.64 - hdHeight);
    }

    private void initData()
    {
        Intent intent = getIntent();
        String link = intent.getStringExtra("LinkUrl");
        String title = intent.getStringExtra("title");
        code = intent.getIntExtra("code", 0);
        TextView tv_title = (TextView) header.findViewById(R.id.tit_tv_tit);
        tv_title.setText(title);
        String sub;
        list = new ArrayList<>();
        if (intent.hasExtra("imgLink"))
        {
            Picasso.with(SongListActivity.this)
                   .load(intent.getStringExtra("imgLink"))
                   .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH*0.64))
                   .centerCrop()
                   .into(songlist_iv_show);
        }
        hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;

                JSONObject obj = null;
                try
                {
                    JSONObject js = new JSONObject(json);
                    if (js.has(ConstVal.DATA))
                    {
                        String data = js.getString(ConstVal.DATA);
                        if (data.startsWith("{") && data.endsWith("}"))
                        {
                            obj = new JSONObject(data);
                            String imgurl = null;
                            if (obj.has("photoBig"))
                            {
                                imgurl = obj.getString("photoBig");
                            }
                            else if(obj.has("banner"))
                            {
                                imgurl = obj.getString("banner");
                            }
                            if (imgurl != null)
                            {
                                Picasso.with(SongListActivity.this)
                                       .load(imgurl)
                                       .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH*0.64))
                                       .centerCrop()
                                       .into(songlist_iv_show);
                            }
                        }

                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                SpannableStringBuilder str = null;
                TextView tv_play = (TextView) findViewById(R.id.item_play_tv);
                vsIdx = new int[]{-1};
                switch (msg.what)
                {
                    case ConstVal.ADVERT_DETAIL_CODE:
                        SongList songs = new Gson().fromJson(GsonUtil.getJsonArray(json), SongList.class);
                        Picasso.with(SongListActivity.this)
                               .load(songs.getP())
                               .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH*0.64))
                               .centerCrop()
                               .into(songlist_iv_show);
                        TextView tv_author = (TextView) vs_author.findViewById(R.id.songlist_tv_author);
                        CircleImageView songlist_civ_icon = (CircleImageView) vs_author.findViewById(R.id.songlist_civ_icon);
                        TextView tv_desc = (TextView) vs_desc.findViewById(R.id.songlist_tv_desc);
                        TextView tv_tag = (TextView) vs_desc.findViewById(R.id.songlist_tv_tag);
                        Picasso.with(SongListActivity.this).load(songs.getUser().getI()).resize(96, 96).into(songlist_civ_icon);
                        tv_author.setText(songs.getUser().getNN());
                        tv_desc.setText(songs.getC());
                        tv_tag.setText(songs.getL());
                        break;

                    case ConstVal.SONGLIST_DETAIL_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                         new TypeToken<ArrayList<AdvertSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, vsIdx, msg.what);
                        songlist_lv_show.setAdapter(adapter);

                        int authorHeight = getViewHeight(vs_author);
                        spcHeight = spcHeight - authorHeight;
                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKFC_CODE:
                    case ConstVal.RANKYC_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<RankSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, vsIdx, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKTP_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<NewSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, vsIdx, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKPOP_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<PopularSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, vsIdx, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.DAILYRECMD_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<DailyRecmd>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, vsIdx, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;
                }
            }
        };

        Request request;
        switch (code)
        {
            case ConstVal.SONGLIST_DETAIL_CODE:
                vs_author = lvhdr_vs_author.inflate();
                vs_desc = lvhdr_vs_desc.inflate();
                request = new Request.Builder().url(ConstVal.SONGLIST_URL + link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, ConstVal.ADVERT_DETAIL_CODE, request);

                request = new Request.Builder()
                        .url(ConstVal.SONGLIST_SONG_URL + link + ConstVal.SONGLIST_SONG_PARAM)
                        .build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, ConstVal.SONGLIST_DETAIL_CODE, request);
                break;

            case ConstVal.RANKYC_CODE:
                sub = "最热门的原创音乐排行榜,每周一更新";
                request = new Request.Builder().url(link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, code, request);
                songlist_tv_sub.setVisibility(View.VISIBLE);
                songlist_tv_sub.setText(sub);
                break;
            case ConstVal.RANKFC_CODE:
                sub = "最优秀的流行歌曲翻唱排行,每周一更新";
                request = new Request.Builder().url(link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, code, request);
                songlist_tv_sub.setVisibility(View.VISIBLE);
                songlist_tv_sub.setText(sub);
                break;
            case ConstVal.RANKTP_CODE:
                sub = "新晋歌曲排行,每周一更新";
                request = new Request.Builder().url(link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, code, request);
                songlist_tv_sub.setVisibility(View.VISIBLE);
                songlist_tv_sub.setText(sub);
                break;
            case ConstVal.RANKPOP_CODE:
                sub = "按歌曲上周所获支持卡总数排名";
                request = new Request.Builder().url(link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, code, request);
                songlist_tv_sub.setVisibility(View.VISIBLE);
                songlist_tv_sub.setText(sub);
                break;

            case ConstVal.DAILYRECMD_CODE:
                request = new Request.Builder().url(link).build();
                OkHttpUtil.enqueue(SongListActivity.this, hdl, code, request);
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                songlist_tv_day.setVisibility(View.VISIBLE);
                songlist_tv_day.setText(day + "");
                sub = "最新最热的优秀作品呈现给你";
                songlist_tv_sub.setVisibility(View.VISIBLE);
                songlist_tv_sub.setText(sub);
                break;
        }
        songlist_lv_show.addHeaderView(lv_header);
        ViewGroup.LayoutParams lpSpace = lvhdr_space.getLayoutParams();
        lpSpace.height = spcHeight;
        lvhdr_space.setLayoutParams(lpSpace);
        lvHdrheight = getViewHeight(lv_header);
    }

    private void initEvent()
    {
        //ListView的Item点击事件
        songlist_lv_show.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (vsIdx[0] >= parent.getFirstVisiblePosition() && vsIdx[0] <= parent.getLastVisiblePosition())
                {
                    parent.getChildAt(vsIdx[0] - parent.getFirstVisiblePosition()).findViewById(R.id.itemsong_view).setVisibility(View.INVISIBLE);
                }
                vsIdx = new int[]{position};
                view.findViewById(R.id.itemsong_view).setVisibility(View.VISIBLE);
                String songid = null;
                String songtype = null;
                String imgurl = null;
                String songname = null;
                String author = null;
                switch (code)
                {
                    case ConstVal.SONGLIST_DETAIL_CODE:
                        AdvertSong as = (AdvertSong) list.get(position-1);
                        songid = as.getID() + "";
                        songtype = as.getSK();
                        imgurl = as.getUser().getI();
                        songname = as.getSN();
                        author = as.getUser().getNN();
                        break;
                    case ConstVal.RANKFC_CODE:
                    case ConstVal.RANKYC_CODE:
                        RankSong rs = (RankSong) list.get(position-1);
                        songid = rs.getID() + "";
                        songtype = rs.getSK();
                        imgurl = rs.getUser().getI();
                        songname = rs.getSN();
                        author = rs.getUser().getNN();
                        break;
                    case ConstVal.RANKTP_CODE:
                        NewSong ts = (NewSong) list.get(position-1);
                        songid = ts.getID() + "";
                        songtype = ts.getSK();
                        imgurl = ts.getUser().getI();
                        songname = ts.getSN();
                        author = ts.getUser().getNN();
                        break;
                    case ConstVal.RANKPOP_CODE:
                        PopularSong ps = (PopularSong) list.get(position-1);
                        songid = ps.getID() + "";
                        songtype = ps.getSK();
                        imgurl = ps.getUser().getI();
                        songname = ps.getSN();
                        author = ps.getUser().getNN();
                        break;
                    case ConstVal.DAILYRECMD_CODE:
                        DailyRecmd ds = (DailyRecmd) list.get(position-1);
                        songid = ds.getSongId();
                        songtype = ds.getSongType();
                        imgurl = ds.getImage();
                        songname = ds.getRecommendName();
                        author = ds.getNickName();
                        MusicUtil.convertMusicType(SongListActivity.this, ds);
                        break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("imgurl", imgurl);
                bundle.putString("songid", songid);
                bundle.putString("songtype", songtype);
                bundle.putString("songname", songname);
                bundle.putString("author", author);
//                String path = ConstVal.GETSONGURL_LINK + "songid=" + songid + "&songtype=" + songtype;
//                Request request = new Request.Builder().url(ConstVal.GETSONGURL_LINK + )
                MusicUtil.showNotification(SongListActivity.this, bundle);

            }
        });

        songlist_lv_show.setOnScrollListener(new AbsListView.OnScrollListener()
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
                if (vsIdx[0] >= firstVisibleItem && vsIdx[0] <= view.getLastVisiblePosition())
                {
                    view.getChildAt(vsIdx[0] - firstVisibleItem).findViewById(R.id.itemsong_view).setVisibility(View.VISIBLE);
                }
                int scrollY;
                if (visibleItemCount == 0)
                {
                    return;
                }
                View c = view.getChildAt(0);
                if (firstVisibleItem == 0)
                {
                    scrollY = 0 - c.getTop();
                }
                else
                {
                    if (c != null)
                    {
                        scrollY = lvHdrheight + (firstVisibleItem-1)*c.getHeight() - c.getTop();
                    }
                    else
                    {
                        return;
                    }
                }
                if (scrollY >= inc_play.getTop())
                {
                    if (vs_play == null)
                    {
                        vs_play = songlist_vs_play.inflate();
                        SpannableStringBuilder str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        TextView tv_play = (TextView) vs_play.findViewById(R.id.item_play_tv);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                    }
                    else
                    {
                        vs_play.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    if (vs_play != null)
                    {
                        vs_play.setVisibility(View.GONE);
                    }
                }
            }
        });

        ImageButton ib_back = (ImageButton) header.findViewById(R.id.tit_ib_back);
        ib_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void showNotification(Bundle bundle)
    {
        String imgurl = bundle.getString("imgurl");
        String songid = bundle.getString("songid");
        String songtype = bundle.getString("songtype");
        String songname = bundle.getString("songname");
        String author = bundle.getString("author");
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.sing_icon_client);
        builder.setTicker(songname + " - " + author);
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_music);
        remoteView.setTextViewText(R.id.ntf_tv_song, songname);
        remoteView.setTextViewText(R.id.ntf_tv_author, author);
        //        Intent intent = new Intent(getPackageName() + ".MUSIC_PAUSE");
        //        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
        //                                                                 0,
        //                                                                 intent,
        //                                                                 PendingIntent.FLAG_UPDATE_CURRENT);
        //        remoteView.setOnClickPendingIntent(R.id.ntf_ib_play, pendingIntent);
        //        builder.setContent(remoteView).setContentIntent(pendingIntent);
        builder.setCustomBigContentView(remoteView).setOngoing(true).setAutoCancel(false);
        Notification notification = builder.build();
        Picasso.with(this)
               .load(imgurl)
               .resize(320, 320)
               .centerCrop()
               .into(remoteView, R.id.ntf_iv_show, ConstVal.NOTIFY_SHOW, notification);
        notificationManager.notify(ConstVal.NOTIFY_SHOW, notification);
    }

    /**
     * 测量控件高度
     * @param view
     * @return
     */
    private int getViewHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }
}
