package com.singoriginal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.singoriginal.R;
import com.singoriginal.adapter.MusicDetailAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.PlaycontentFragment;
import com.singoriginal.fragment.PlaylistFragment;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.MusicDetail;
import com.singoriginal.util.DownloadUtil;
import com.singoriginal.util.MusicUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MusicDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String KEY_PLAYMODE = "播放模式";
    private SharedPreferences defaultSet;

    private View msc_dtl_hdr;
    private TextView tit_tv_title;
    private ViewPager msc_dtl_vp_show;
    private CircleImageView msc_dtl_civ_icon;
    private SeekBar msc_dtl_sb_prg;
    private TextView msc_dtl_tv_singer;
    private TextView msc_dtl_tv_duration;
    private TextView msc_dtl_tv_total;
    private ImageView msc_dtl_iv_pc;
    private ImageView msc_dtl_iv_pl;
    private ImageView msc_dtl_iv_pr;
    private TextView msc_dtl_tv_dyn;
    private CircleImageView msc_dtl_play;
    private ImageButton msc_dtl_next;
    private ImageButton msc_dtl_prev;
    private ImageButton msc_dtl_loop;
    private ImageButton msc_dtl_like;
    private ImageButton msc_dtl_download;
    private ImageButton msc_dtl_share;

    private ArrayList<Fragment> frags;
    private MusicDetailAdapter adapter;

    private GetDetailReceiver detailReceiver;
    private int duration;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);

        initView();
        MusicUtil.playGetDetail(MusicDetailActivity.this);
    }

    private void initView()
    {
        msc_dtl_hdr = findViewById(R.id.msc_dtl_hdr);
        msc_dtl_vp_show = (ViewPager) findViewById(R.id.msc_dtl_vp_show);
        msc_dtl_civ_icon = (CircleImageView) findViewById(R.id.msc_dtl_civ_icon);
        msc_dtl_sb_prg = (SeekBar) findViewById(R.id.msc_dtl_sb_prg);
        msc_dtl_tv_singer = (TextView) findViewById(R.id.msc_dtl_singer);
        msc_dtl_tv_duration = (TextView) findViewById(R.id.msc_dtl_tv_pst);
        msc_dtl_tv_total = (TextView) findViewById(R.id.msc_dtl_tv_ttl);
        msc_dtl_iv_pl = (ImageView) findViewById(R.id.msc_dtl_iv_pl);
        msc_dtl_iv_pc = (ImageView) findViewById(R.id.msc_dtl_iv_pc);
        msc_dtl_iv_pr = (ImageView) findViewById(R.id.msc_dtl_iv_pr);
        msc_dtl_tv_dyn = (TextView) findViewById(R.id.msc_dtl_tb_dyn);
        msc_dtl_sb_prg = (SeekBar) findViewById(R.id.msc_dtl_sb_prg);
        msc_dtl_play = (CircleImageView) findViewById(R.id.msc_dtl_play);
        msc_dtl_next = (ImageButton) findViewById(R.id.msc_dtl_next);
        msc_dtl_prev = (ImageButton) findViewById(R.id.msc_dtl_prev);
        msc_dtl_loop = (ImageButton) findViewById(R.id.msc_dtl_loop);
        msc_dtl_like = (ImageButton) findViewById(R.id.msc_dtl_ib_like);
        msc_dtl_download = (ImageButton) findViewById(R.id.msc_dtl_ib_download);
        msc_dtl_share = (ImageButton) findViewById(R.id.msc_dtl_ib_share);

        msc_dtl_civ_icon.setOnClickListener(this);
        msc_dtl_tv_dyn.setOnClickListener(this);
        msc_dtl_play.setOnClickListener(this);
        msc_dtl_prev.setOnClickListener(this);
        msc_dtl_next.setOnClickListener(this);
        msc_dtl_loop.setOnClickListener(this);
        msc_dtl_like.setOnClickListener(this);
        msc_dtl_download.setOnClickListener(this);
        msc_dtl_share.setOnClickListener(this);

        msc_dtl_hdr.setBackgroundColor(ConstVal.COLOR_SHALLOWBLACK);
        msc_dtl_hdr.findViewById(R.id.tit_ib_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        tit_tv_title = (TextView) msc_dtl_hdr.findViewById(R.id.tit_tv_tit);
        ImageButton tit_ib_more = (ImageButton) msc_dtl_hdr.findViewById(R.id.tit_ib_msc);
        tit_ib_more.setImageResource(R.mipmap.liveroom_more);
        tit_ib_more.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        msc_dtl_vp_show.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        tit_tv_title.setText("当前播放");
                        msc_dtl_iv_pl.setImageResource(R.mipmap.dot_f);
                        msc_dtl_iv_pc.setImageResource(R.mipmap.dot_n);
                        msc_dtl_iv_pr.setImageResource(R.mipmap.dot_n);
                        msc_dtl_tv_dyn.setVisibility(View.GONE);
                        break;
                    case 1:
                        tit_tv_title.setText(MusicData.currentMusicDetail.getData().getSN());
                        msc_dtl_iv_pl.setImageResource(R.mipmap.dot_n);
                        msc_dtl_iv_pc.setImageResource(R.mipmap.dot_f);
                        msc_dtl_iv_pr.setImageResource(R.mipmap.dot_n);
                        msc_dtl_tv_dyn.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tit_tv_title.setText("灵感");
                        msc_dtl_iv_pl.setImageResource(R.mipmap.dot_n);
                        msc_dtl_iv_pc.setImageResource(R.mipmap.dot_n);
                        msc_dtl_iv_pr.setImageResource(R.mipmap.dot_f);
                        msc_dtl_tv_dyn.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        msc_dtl_sb_prg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                int position = seekBar.getProgress();
                Intent intent = new Intent(getPackageName() + ".MUSIC_RECEIVER");
                intent.putExtra("requestCode", ConstVal.SET_PLAY_POSITIN);
                intent.putExtra("position", position);
                sendBroadcast(intent);
            }
        });

        msc_dtl_download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MusicDetail msc = MusicData.currentMusicDetail;
                String title = msc.getData().getSN() + " - " + msc.getData().getUser().getNN();
                DownloadUtil.showDownloadDialog(MusicDetailActivity.this,
                                                title,
                                                MusicData.brief);

            }
        });

        detailReceiver = new GetDetailReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(getPackageName() + ".DETAIL_RECEIVER");
        registerReceiver(detailReceiver, filter);
    }

    @Override
    protected void onDestroy()
    {
        if (detailReceiver != null)
        {
            unregisterReceiver(detailReceiver);
        }
        super.onDestroy();
    }

    private void initData()
    {
        defaultSet = PreferenceManager.getDefaultSharedPreferences(this);
        MusicData.music_play_mode = defaultSet.getInt(KEY_PLAYMODE, ConstVal.PLAY_MODE_LIST_LOOP);
        frags = new ArrayList<>();
        frags.add(new PlaylistFragment());
        MusicDetail detail = MusicData.currentMusicDetail;
        frags.add(PlaycontentFragment.newInstance(null, detail.getData().getSW(), Color.WHITE));
        String[] img = detail.getData().getImg();
        String url;
        if (img == null || img.length == 0)
        {
            url = null;
        }
        else
        {
            url = img[0];
        }
        frags.add(PlaycontentFragment.newInstance(url,
                                                  detail.getData().getWord(),
                                                  Color.GRAY));
        adapter = new MusicDetailAdapter(getSupportFragmentManager(), frags);
        msc_dtl_vp_show.setAdapter(adapter);
        msc_dtl_vp_show.setCurrentItem(1);

        tit_tv_title.setText(detail.getData().getSN());
        Picasso.with(MusicDetailActivity.this)
               .load(detail.getData().getUser().getI())
               .error(R.mipmap.default_image)
               .resize(192, 192)
               .centerCrop()
               .into(msc_dtl_civ_icon);
        msc_dtl_tv_singer.setText(detail.getData().getUser().getNN());
        if (MusicData.music_play_mode == ConstVal.PLAY_MODE_LIST_LOOP)
        {
            msc_dtl_loop.setImageResource(R.mipmap.player_cycle_pressed);
        }
        else if (MusicData.music_play_mode == ConstVal.PLAY_MODE_SINGLE_LOOP)
        {
            msc_dtl_loop.setImageResource(R.mipmap.player_single_cycle_pressed);
        }
        else if (MusicData.music_play_mode == ConstVal.PLAY_MODE_RANDOM)
        {
            msc_dtl_loop.setImageResource(R.mipmap.player_round_pressed);
        }
        if (MusicData.music_play_state == ConstVal.PLAY_STATE_PLAYING)
        {
            msc_dtl_play.setImageResource(R.mipmap.player_pause_pressed);
        }
        else if (MusicData.music_play_state == ConstVal.PLAY_STATE_PAUSE)
        {
            msc_dtl_play.setImageResource(R.mipmap.player_start_pressed);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.msc_dtl_play:
                MusicUtil.playToggle(MusicDetailActivity.this);
                break;

            case R.id.msc_dtl_prev:
                if (MusicData.music_play_state != ConstVal.PLAY_STATE_PREPARE)
                {
                    MusicUtil.playPrev(MusicDetailActivity.this);
                }
                break;

            case R.id.msc_dtl_next:
                if (MusicData.music_play_state != ConstVal.PLAY_STATE_PREPARE)
                {
                    MusicUtil.playNext(MusicDetailActivity.this);
                }
                break;

            case R.id.msc_dtl_tb_dyn:
                MusicData.music_dynlrc = !MusicData.music_dynlrc;
                if (MusicData.music_dynlrc)
                {
                    msc_dtl_tv_dyn.setText("静态歌词");
                }
                else
                {
                    msc_dtl_tv_dyn.setText("动态歌词");
                }
                break;

            case R.id.msc_dtl_loop:
                if (MusicData.music_play_mode == ConstVal.PLAY_MODE_LIST_LOOP)
                {
                    MusicData.music_play_mode = ConstVal.PLAY_MODE_SINGLE_LOOP;
                    msc_dtl_loop.setImageResource(R.mipmap.player_single_cycle_pressed);
                    Toast.makeText(MusicDetailActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                }
                else if (MusicData.music_play_mode == ConstVal.PLAY_MODE_SINGLE_LOOP)
                {
                    MusicData.music_play_mode = ConstVal.PLAY_MODE_RANDOM;
                    msc_dtl_loop.setImageResource(R.mipmap.player_round_pressed);
                    Toast.makeText(MusicDetailActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MusicData.music_play_mode = ConstVal.PLAY_MODE_LIST_LOOP;
                    msc_dtl_loop.setImageResource(R.mipmap.player_cycle_pressed);
                    Toast.makeText(MusicDetailActivity.this, "循环播放", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences.Editor editor = defaultSet.edit();
                editor.putInt(KEY_PLAYMODE, MusicData.music_play_mode);
                editor.commit();
                break;

            case R.id.msc_dtl_ib_like:

                break;

            case R.id.msc_dtl_ib_download:

                break;

            case R.id.msc_dtl_ib_share:

                break;
        }
    }

    public class GetDetailReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int result = intent.getIntExtra("requestCode", -1);
            DecimalFormat format = new DecimalFormat("00");
            switch (result)
            {
                case ConstVal.DETAIL_UPDATE:
                    initData();
                    break;

                case ConstVal.DETAIL_PROGRESS:
                    progress = intent.getIntExtra("progress", 0);
                    msc_dtl_sb_prg.setProgress(progress);
                    progress /= 1000;
                    msc_dtl_tv_duration.setText(format.format(progress / 60) + ":" + format.format(
                            progress % 60));
                    break;

                case ConstVal.DETAIL_DURATION:
                    duration = intent.getIntExtra("duration", -1);
                    msc_dtl_sb_prg.setMax(duration);
                    duration /= 1000;
                    msc_dtl_tv_total.setText(format.format(duration / 60) + ":" + format.format(
                            duration % 60));
                    break;

                case ConstVal.DETAIL_STATE:
                    if (MusicData.music_play_state == ConstVal.PLAY_STATE_PAUSE)
                    {
                        msc_dtl_play.setImageResource(R.mipmap.player_start_pressed);
                    }
                    else if (MusicData.music_play_state == ConstVal.PLAY_STATE_PLAYING)
                    {
                        msc_dtl_play.setImageResource(R.mipmap.player_pause_pressed);
                    }
                    break;
            }
        }
    }
}
