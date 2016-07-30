package com.singoriginal.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.adapter.PagerInfoAdapter;
import com.singoriginal.adapter.PagerWorkAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.AccompanimentFragment;
import com.singoriginal.fragment.CoverFragment;
import com.singoriginal.fragment.LeaveWordFragment;
import com.singoriginal.fragment.OriginalFragment;
import com.singoriginal.fragment.RelatedMeFragment;
import com.singoriginal.fragment.StationSmsFragment;

import java.util.ArrayList;
import java.util.List;

public class MyWorkActivity extends AppCompatActivity {

    private ImageButton imageBack;
    private ImageButton imageUpload;

    private TabLayout work_tabLayout;
    private ViewPager work_viewPager;
    private PagerWorkAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        initView();
        initData();
        setDataToView();
        initEvent();
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();

        fragments.add(new OriginalFragment());
        fragments.add(new CoverFragment());
        fragments.add(new AccompanimentFragment());

        String[] text = {"原创", "翻唱", "伴奏"};

        list = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {

            list.add(text[i]);
        }
    }

    private void setDataToView() {

        adapter = new PagerWorkAdapter(getSupportFragmentManager(), this, list, fragments);
        work_viewPager.setAdapter(adapter);
        work_tabLayout.setupWithViewPager(work_viewPager);
    }

    private void initEvent() {

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkActivity.this, UploadSongsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {

        work_tabLayout = (TabLayout) findViewById(R.id.work_tabLayout);
        work_viewPager = (ViewPager) findViewById(R.id.work_viewPager);

        //页面公用标题头初始化
        View incView = findViewById(R.id.my_work_header);

        imageBack = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        imageBack.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.mysong));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        imageUpload = (ImageButton) incView.findViewById(R.id.hdr_ib_music);
        imageUpload.setImageResource(R.mipmap.work_song_upload);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
