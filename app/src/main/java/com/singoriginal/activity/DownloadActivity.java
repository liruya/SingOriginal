package com.singoriginal.activity;

import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.adapter.DownloadAdapter;
import com.singoriginal.adapter.PagerWorkAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.AccompanimentFragment;
import com.singoriginal.fragment.AlreadyDownloadFragment;
import com.singoriginal.fragment.CoverFragment;
import com.singoriginal.fragment.DownloadingFragment;
import com.singoriginal.fragment.OriginalFragment;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private ImageButton image;

    private TabLayout download_tabLayout;
    private ViewPager download_viewPager;
    private DownloadAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();
        initData();
        setDataToView();
        initEvent();
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();

        fragments.add(new AlreadyDownloadFragment());
        fragments.add(new DownloadingFragment());

        String[] text = {"已下载", "正在下载"};

        list = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {

            list.add(text[i]);
        }
    }

    private void setDataToView() {

        adapter = new DownloadAdapter(getSupportFragmentManager(), this, list, fragments);
        download_viewPager.setAdapter(adapter);
        download_tabLayout.setupWithViewPager(download_viewPager);
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

        download_viewPager = (ViewPager) findViewById(R.id.download_viewPager);
        download_tabLayout = (TabLayout) findViewById(R.id.download_tabLayout);
        //页面公用标题头初始化
        View incView = findViewById(R.id.download_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.download));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
