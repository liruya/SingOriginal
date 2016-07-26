package com.singoriginal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ListSongAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.SongList;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

public class SongListActivity extends AppCompatActivity
{
    private View header;
    private View vs_play;
    private View inc_play;
    private ImageView songlist_iv_show;
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
        vs_play = findViewById(R.id.songlist_vs_play);
        inc_play = findViewById(R.id.songlist_inc_play);
        songlist_iv_show = (ImageView) findViewById(R.id.songlist_iv_show);
        songlist_civ_icon = (CircleImageView) findViewById(R.id.songlist_civ_icon);
        songlist_tv_author = (TextView) findViewById(R.id.songlist_tv_author);
        songlist_ib_colc = (ImageButton) findViewById(R.id.songlist_ib_colc);
        songlist_ib_share = (ImageButton) findViewById(R.id.songlist_ib_share);
        songlist_tv_desc = (TextView) findViewById(R.id.songlist_tv_desc);
        songlist_tv_tag = (TextView) findViewById(R.id.songlist_tv_tag);
        songlist_ib_expan = (ImageButton) findViewById(R.id.songlist_tb_expan);
        songlist_rv_show = (RecyclerView) findViewById(R.id.songlist_rv_show);

    }

    private void initData()
    {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
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
                        Picasso.with(SongListActivity.this).load(songs.getP()).into(songlist_iv_show);
                        Picasso.with(SongListActivity.this).load(songs.getUser().getI()).into(songlist_civ_icon);
                        songlist_tv_author.setText(songs.getUser().getNN());
                        songlist_tv_desc.setText(songs.getC());
                        songlist_tv_tag.setText(songs.getL());
                        break;

                    case ConstVal.SONGLIST_DETAIL_CODE:
                        advertSongs = new Gson().fromJson(GsonUtil.getJsonArray(json),
                                                         new TypeToken<ArrayList<AdvertSong>>(){}.getType());
                        adapter = new ListSongAdapter(SongListActivity.this, advertSongs);
                        songlist_rv_show.setAdapter(adapter);
                        break;
                }
            }
        };
        Request request = new Request.Builder().url(ConstVal.SONGLIST_URL + id).build();
        OkHttpUtil.enqueue(this, hdl, ConstVal.ADVERT_DETAIL_CODE, request);

        request = new Request.Builder()
                .url(ConstVal.SONGLIST_SONG_URL + id + ConstVal.SONGLIST_SONG_PARAM)
                .build();
        OkHttpUtil.enqueue(SongListActivity.this, hdl, ConstVal.SONGLIST_DETAIL_CODE, request);
    }

    private void initEvent()
    {

    }
}
