package com.singoriginal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ListSongAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.impl.ScrollViewListener;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.SongList;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;
import com.singoriginal.util.RtfUtil;
import com.singoriginal.view.CustomScrollView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class SongListActivity extends AppCompatActivity
{
    private View header;
    private View vs_play = null;
    private View inc_play;
    private ImageView songlist_iv_show;
    private ViewStub songlist_vs_play;
    private Space songlist_space;
    private LinearLayout songlist_ll;
    private CustomScrollView songlist_scroll;
    private CircleImageView songlist_civ_icon;
    private TextView songlist_tv_author;
    private ImageButton songlist_ib_colc;
    private ImageButton songlist_ib_share;
    private TextView songlist_tv_desc;
    private TextView songlist_tv_tag;
    private ImageButton songlist_ib_expan;
    private RecyclerView songlist_rv_show;

    private ArrayList<AdvertSong> advertSongs;
    private ListSongAdapter adapter;
    private Handler hdl;

    private int height;
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
        songlist_vs_play = (ViewStub) findViewById(R.id.songlist_vs_play);
        inc_play = findViewById(R.id.songlist_inc_play);
        songlist_iv_show = (ImageView) findViewById(R.id.songlist_iv_show);
        songlist_space = (Space) findViewById(R.id.songlist_space);
        songlist_ll = (LinearLayout) findViewById(R.id.songlist_ll);
        songlist_scroll = (CustomScrollView) findViewById(R.id.songlist_scroll);
        songlist_civ_icon = (CircleImageView) findViewById(R.id.songlist_civ_icon);
        songlist_tv_author = (TextView) findViewById(R.id.songlist_tv_author);
        songlist_ib_colc = (ImageButton) findViewById(R.id.songlist_ib_colc);
        songlist_ib_share = (ImageButton) findViewById(R.id.songlist_ib_share);
        songlist_tv_desc = (TextView) findViewById(R.id.songlist_tv_desc);
        songlist_tv_tag = (TextView) findViewById(R.id.songlist_tv_tag);
        songlist_ib_expan = (ImageButton) findViewById(R.id.songlist_tb_expan);
        songlist_rv_show = (RecyclerView) findViewById(R.id.songlist_rv_show);

        header.setBackgroundColor(0x11000000);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) songlist_iv_show.getLayoutParams();
        lp.width = ConstVal.SCREEN_WIDTH;
        lp.height = (int) (ConstVal.SCREEN_WIDTH * 0.64);
        songlist_iv_show.setLayoutParams(lp);

        int llHeight = getViewHeight(songlist_ll);
        int hdHeight = getViewHeight(header);
        LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) songlist_space.getLayoutParams();
        llp.height = (int) (ConstVal.SCREEN_WIDTH * 0.64 - llHeight - hdHeight);
        songlist_space.setLayoutParams(llp);

        LinearLayoutManager lm = new LinearLayoutManager(SongListActivity.this,
                                                         LinearLayoutManager.VERTICAL,
                                                         false);
        songlist_rv_show.setLayoutManager(lm);
    }

    private void initData()
    {
        Intent intent = getIntent();
        String id = intent.getStringExtra("LinkUrl");
        String title = intent.getStringExtra("title");
        TextView tv_title = (TextView) header.findViewById(R.id.tit_tv_tit);
        tv_title.setText(title);
        advertSongs = new ArrayList<>();
        hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case ConstVal.ADVERT_DETAIL_CODE:
                        SongList songs = new Gson().fromJson(GsonUtil.getJsonArray(json), SongList.class);
                        Picasso.with(SongListActivity.this)
                               .load(songs.getP())
                               .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH*0.64))
                               .centerInside()
                               .into(songlist_iv_show);
                        Picasso.with(SongListActivity.this).load(songs.getUser().getI()).resize(96, 96).into(songlist_civ_icon);
                        songlist_tv_author.setText(songs.getUser().getNN());
                        songlist_tv_desc.setText(songs.getC());
                        songlist_tv_tag.setText(songs.getL());
                        break;

                    case ConstVal.SONGLIST_DETAIL_CODE:
                        advertSongs = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                         new TypeToken<ArrayList<AdvertSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, advertSongs);
                        songlist_rv_show.setAdapter(adapter);
                        SpannableStringBuilder str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getItemCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        TextView tv_play = (TextView) inc_play.findViewById(R.id.item_play_tv);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                        break;
                }
            }
        };
        Request request = new Request.Builder().url(ConstVal.SONGLIST_URL + id).build();
        OkHttpUtil.enqueue(SongListActivity.this, hdl, ConstVal.ADVERT_DETAIL_CODE, request);

        request = new Request.Builder()
                .url(ConstVal.SONGLIST_SONG_URL + id + ConstVal.SONGLIST_SONG_PARAM)
                .build();
        OkHttpUtil.enqueue(SongListActivity.this, hdl, ConstVal.SONGLIST_DETAIL_CODE, request);
    }

    private void initEvent()
    {
        songlist_scroll.setScrollViewListener(new ScrollViewListener()
        {
            @Override
            public void onSrollChanged(CustomScrollView scrollView,
                                       int x,
                                       int y,
                                       int oldx,
                                       int oldy)
            {
                height = inc_play.getTop();

                if (oldy < height && y >= height)
                {
                    if (vs_play == null)
                    {
                        vs_play = songlist_vs_play.inflate();
                        SpannableStringBuilder str = RtfUtil.getRtf(null, "全部歌曲", ConstVal.COLOR_SHALLOWBLACK, 42);
                        str = RtfUtil.getRtf(str, " (共" + adapter.getItemCount() + "首)", ConstVal.COLOR_GRAY, 36);
                        TextView tv_play = (TextView) vs_play.findViewById(R.id.item_play_tv);
                        tv_play.setText(str, TextView.BufferType.SPANNABLE);
                    }
                    else
                    {
                        vs_play.setVisibility(View.VISIBLE);
                    }
                }
                else if(oldy >= height && y < height)
                {
                    vs_play.setVisibility(View.GONE);
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
