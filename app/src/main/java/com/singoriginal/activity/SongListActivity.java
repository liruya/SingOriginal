package com.singoriginal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Space;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ListSongAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.dialog.SongmoreDialog;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.DailyRecmd;
import com.singoriginal.model.Music;
import com.singoriginal.model.MusicData;
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

    private SongList songs;
    private ListSongAdapter adapter;
    private ArrayList<Object> list;
    private ArrayList<Music> musicList;
    private Handler hdl;

    private int vsIdx = -1;
    private int lvHdrheight;
    private int spcHeight;
    private int code;
    //文字是否展开
    private boolean isExpan;
    //
    private boolean isLike;
    private PlayingItemReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        initView();
        receiver = new PlayingItemReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".PLAYING_ITEM_RECEIVER");
        registerReceiver(receiver, filter);
        initData();
        initEvent();
//        ShareSDK.initSDK(SongListActivity.this);
    }

    @Override
    protected void onDestroy()
    {
        isExpan = false;
        if (receiver != null)
        {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    private void initView()
    {
        header = findViewById(R.id.songlist_inc_title);
        songlist_lv_show = (ListView) findViewById(R.id.songlist_lv_show);
        songlist_vs_play = (ViewStub) findViewById(R.id.songlist_vs_play);
        songlist_tv_day = (TextView) findViewById(R.id.songlist_tv_day);
        songlist_tv_sub = (TextView) findViewById(R.id.songlist_tv_sub);
        lv_header = LayoutInflater.from(SongListActivity.this)
                                  .inflate(R.layout.item_lv_header, songlist_lv_show, false);
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
        final String link = intent.getStringExtra("LinkUrl");
        String title = intent.getStringExtra("title");
        code = intent.getIntExtra("code", 0);
        TextView tv_title = (TextView) header.findViewById(R.id.tit_tv_tit);
        tv_title.setText(title);
        String sub;
        list = new ArrayList<>();
        musicList = new ArrayList<>();
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
                vsIdx = -1;
                switch (msg.what)
                {
                    case ConstVal.ADVERT_DETAIL_CODE:
                        songs = new Gson().fromJson(GsonUtil.getJsonArray(json), SongList.class);
                        Picasso.with(SongListActivity.this)
                               .load(songs.getP())
                               .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH*0.64))
                               .centerCrop()
                               .into(songlist_iv_show);
                        TextView tv_author = (TextView) vs_author.findViewById(R.id.songlist_tv_author);
                        CircleImageView songlist_civ_icon = (CircleImageView) vs_author.findViewById(R.id.songlist_civ_icon);
                        final TextView tv_desc = (TextView) vs_desc.findViewById(R.id.songlist_tv_desc);
                        TextView tv_tag = (TextView) vs_desc.findViewById(R.id.songlist_tv_tag);
                        final ImageButton tb = (ImageButton) vs_desc.findViewById(R.id.songlist_tb_expan);
                        Picasso.with(SongListActivity.this).load(songs.getUser().getI()).resize(96, 96).into(songlist_civ_icon);
                        tv_author.setText(songs.getUser().getNN());
                        tv_desc.setText(songs.getC());
                        tv_tag.setText(songs.getL());
                        tb.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                isExpan = !isExpan;
                                if (isExpan)
                                {
                                    tv_desc.setMaxLines(tv_desc.getLineCount());
                                    tb.setImageResource(R.mipmap.affas_shou);
                                }
                                else
                                {
                                    tv_desc.setMaxLines(2);
                                    tb.setImageResource(R.mipmap.affas_suo);
                                }
                            }
                        });
                        break;

                    case ConstVal.SONGLIST_DETAIL_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                         new TypeToken<ArrayList<AdvertSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        musicList.clear();
                        musicList = convertList(list);
                        MusicUtil.playShowSelect(SongListActivity.this);

                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKFC_CODE:
                    case ConstVal.RANKYC_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<RankSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        musicList.clear();
                        musicList = convertList(list);
                        MusicUtil.playShowSelect(SongListActivity.this);

                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKTP_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<NewSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        musicList.clear();
                        musicList = convertList(list);
                        MusicUtil.playShowSelect(SongListActivity.this);

                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.RANKPOP_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<PopularSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        musicList.clear();
                        musicList = convertList(list);
                        MusicUtil.playShowSelect(SongListActivity.this);

                        str = null;
                        str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;

                    case ConstVal.DAILYRECMD_CODE:
                        list = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                   new TypeToken<ArrayList<DailyRecmd>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, list, msg.what);
                        songlist_lv_show.setAdapter(adapter);
                        musicList.clear();
                        musicList = convertList(list);
                        MusicUtil.playShowSelect(SongListActivity.this);

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
                vs_author.findViewById(R.id.songlist_civ_icon).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(SongListActivity.this, HeadIconActivity.class);
                        intent.putExtra("SIM", songs.getUser().getI());
                        intent.putExtra("SU", songs.getUser().getNN());
                        intent.putExtra("SUID", songs.getUser().getID() + "");
                        startActivity(intent);
                    }
                });
                vs_author.findViewById(R.id.songlist_ib_share).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        SongmoreDialog.showShareDialog(SongListActivity.this, null);
                    }
                });
                final ImageButton ib_like = (ImageButton) vs_author.findViewById(R.id.songlist_ib_colc);
                ib_like.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        isLike = !isLike;
                        if (isLike)
                        {
                            ib_like.setImageResource(R.mipmap.dj_sc);
                        }
                        else
                        {
                            ib_like.setImageResource(R.mipmap.dj_wsc);
                        }
                    }
                });
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
        if (vs_author != null)
        {
            int authorHeight = getViewHeight(vs_author);
            spcHeight = spcHeight - authorHeight;
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
                loadMusicList();
                MusicData.music_play_idx = (int) id;
                MusicUtil.playStart(SongListActivity.this);
                int first = songlist_lv_show.getFirstVisiblePosition();
                int last = songlist_lv_show.getLastVisiblePosition();
                showItemSelect(first, last, vsIdx, false);
                vsIdx = position;
                showItemSelect(first, last, vsIdx, true);
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

                int first = songlist_lv_show.getFirstVisiblePosition();
                int last = songlist_lv_show.getLastVisiblePosition();
                if (vsIdx >= first && vsIdx <= last)
                {
                    for (int i = first; i <= last; i++)
                    {
                        if (first == 0)
                        {
                            break;
                        }
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
                        vs_play.findViewById(R.id.item_play_tv).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                loadMusicList();
                                MusicData.music_play_idx = 0;
                                MusicUtil.playStart(SongListActivity.this);
                                Intent intent = new Intent(SongListActivity.this, MusicDetailActivity.class);
                                startActivity(intent);
                            }
                        });
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

        header.findViewById(R.id.tit_ib_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        header.findViewById(R.id.tit_ib_msc).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (MusicData.musicList != null && MusicData.musicList.size() > 0)
                {
                    Intent intent = new Intent(SongListActivity.this, MusicDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        inc_play.findViewById(R.id.item_play_tv).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loadMusicList();
                MusicData.music_play_idx = 0;
                MusicUtil.playStart(SongListActivity.this);
                Intent intent = new Intent(SongListActivity.this, MusicDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Music> convertList(ArrayList<Object> list)
    {
        ArrayList<Music> musicList = new ArrayList<>();
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                musicList.add(MusicUtil.convertMusicType(SongListActivity.this, list.get(i)));
            }
        }
        return musicList;
    }

    private void loadMusicList()
    {
        if (musicList != null && musicList.size() > 0)
        {
            if (MusicData.musicList == null)
            {
                MusicData.musicList = new ArrayList<>();
            }
            if (MusicData.musicList.equals(musicList))
            {
                return;
            }
            MusicData.musicList.clear();
            MusicData.musicList.addAll(musicList);
        }
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

    private void showItemSelect(int first, int last, int position, boolean selected)
    {
        if (position >= first && position <= last)
        {
            View view = songlist_lv_show.getChildAt(position - first);
            TextView tv_song = (TextView) view.findViewById(R.id.itemsong_tv_title);
            TextView tv_author = (TextView) view.findViewById(R.id.itemsong_tv_athor);
            if (selected)
            {
                view.findViewById(R.id.itemsong_view).setVisibility(View.VISIBLE);
                tv_song.setTextColor(ConstVal.COLOR_DARKGREEN);
                tv_author.setTextColor(ConstVal.COLOR_DARKGREEN);
            }
            else
            {
                view.findViewById(R.id.itemsong_view).setVisibility(View.INVISIBLE);
                tv_song.setTextColor(ConstVal.COLOR_SHALLOWBLACK);
                tv_author.setTextColor(ConstVal.COLOR_GRAY);
            }

        }
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

    class PlayingItemReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int result = intent.getIntExtra("requestCode", -1);
            if (result >= 0)
            {
                if (isEqual(MusicData.musicList, musicList))
                {
                    int first = songlist_lv_show.getFirstVisiblePosition();
                    int last = songlist_lv_show.getLastVisiblePosition();
                    showItemSelect(first, last, vsIdx, false);
                    vsIdx = result+1;
                    showItemSelect(first, last, vsIdx, true);
                }
            }
        }
    }
}
