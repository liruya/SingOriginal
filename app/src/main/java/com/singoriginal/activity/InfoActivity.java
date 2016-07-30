package com.singoriginal.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.adapter.PagerInfoAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.LeaveWordFragment;
import com.singoriginal.fragment.RelatedMeFragment;
import com.singoriginal.fragment.StationSmsFragment;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private TabLayout info_tabLayout;
    private ViewPager info_viewPager;
    private PagerInfoAdapter adapter;
    private List<String> list;
    private List<Fragment> fragments;
    private ImageView info_back;
    private TextView info_writeSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
        initData();
        setDataToView();
        initEvent();
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();

        fragments.add(new RelatedMeFragment());
        fragments.add(new LeaveWordFragment());
        fragments.add(new StationSmsFragment());

        String[] text = {"与我相关", "留言", "站内信"};

        list = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {

            list.add(text[i]);
        }
    }

    private void setDataToView() {

        adapter = new PagerInfoAdapter(getSupportFragmentManager(), this, list, fragments);
        info_viewPager.setAdapter(adapter);
        info_tabLayout.setupWithViewPager(info_viewPager);
    }

    private void initEvent() {

        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        info_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 2)
                    info_writeSms.setVisibility(View.VISIBLE);
                else
                    info_writeSms.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {

        info_back = (ImageView) findViewById(R.id.info_back);
        info_writeSms = (TextView) findViewById(R.id.info_writeSms);
        info_tabLayout = (TabLayout) findViewById(R.id.info_tabLayout);
        info_viewPager = (ViewPager) findViewById(R.id.info_viewPager);
    }
}
