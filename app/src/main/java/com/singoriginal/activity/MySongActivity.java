package com.singoriginal.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.adapter.MySongAdapter;
import com.singoriginal.adapter.PagerWorkAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.AccompanimentFragment;
import com.singoriginal.fragment.CollectionSongFragment;
import com.singoriginal.fragment.CoverFragment;
import com.singoriginal.fragment.MySongFragment;
import com.singoriginal.fragment.OriginalFragment;

import java.util.ArrayList;
import java.util.List;

public class MySongActivity extends AppCompatActivity {

    private ImageButton image;

    private TabLayout my_song_tabLayout;
    private ViewPager my_song_viewPager;
    private MySongAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_song);

        initView();
        initData();
        setDataToView();
        initEvent();
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();

        fragments.add(new MySongFragment());
        fragments.add(new CollectionSongFragment());

        String[] text = {"我的歌单", "收藏的歌单"};

        list = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {

            list.add(text[i]);
        }
    }

    private void setDataToView() {

        adapter = new MySongAdapter(getSupportFragmentManager(), this, list, fragments);
        my_song_viewPager.setAdapter(adapter);
        my_song_tabLayout.setupWithViewPager(my_song_viewPager);
    }

    private void initEvent() {

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        my_song_viewPager = (ViewPager) findViewById(R.id.my_song_viewPager);
        my_song_tabLayout = (TabLayout) findViewById(R.id.my_song_tabLayout);
        //页面公用标题头初始化
        View incView = findViewById(R.id.my_song_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.mysonglist));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
