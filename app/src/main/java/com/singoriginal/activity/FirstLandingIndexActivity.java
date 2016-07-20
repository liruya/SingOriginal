package com.singoriginal.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.adapter.PagerIndexAdapter;

import java.util.ArrayList;

public class FirstLandingIndexActivity extends AppCompatActivity {

    private ViewPager index_viewPager;
    private TextView index_tv_experience;
    private ImageView index_point1;
    private ImageView index_point2;
    private ImageView index_point3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_landing_first);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {

        index_tv_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FirstLandingIndexActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {

        ArrayList<Picture> dataList = new ArrayList<>();

        dataList.add(new Picture(R.mipmap.spec_1_bottom, R.mipmap.spec_1_top));
        dataList.add(new Picture(R.mipmap.spec_2_bottom, R.mipmap.spec_2_top));
        dataList.add(new Picture(R.mipmap.spec_3_bottom, R.mipmap.spec_3_top));

        //给viewPager绑定Adapter
        index_viewPager.setAdapter(new PagerIndexAdapter(FirstLandingIndexActivity.this, dataList));
        //设置默认的Pager
        index_viewPager.setCurrentItem(0);
        index_point1.setImageResource(R.mipmap.point_select);

        index_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {

                    case 0:
                        index_point1.setImageResource(R.mipmap.point_select);
                        index_point2.setImageResource(R.mipmap.point_unselect);
                        index_point3.setImageResource(R.mipmap.point_unselect);
                        break;
                    case 1:
                        index_point1.setImageResource(R.mipmap.point_unselect);
                        index_point2.setImageResource(R.mipmap.point_select);
                        index_point3.setImageResource(R.mipmap.point_unselect);
                        break;
                    case 2:
                        index_point1.setImageResource(R.mipmap.point_unselect);
                        index_point2.setImageResource(R.mipmap.point_unselect);
                        index_point3.setImageResource(R.mipmap.point_select);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {

        index_viewPager = (ViewPager) findViewById(R.id.index_viewPager);
        index_tv_experience = (TextView) findViewById(R.id.index_tv_experience);
        index_point1 = (ImageView) findViewById(R.id.index_point1);
        index_point2 = (ImageView) findViewById(R.id.index_point2);
        index_point3 = (ImageView) findViewById(R.id.index_point3);
    }

    public final class Picture {

        private int PicResID;
        private int PicResIDBottom;

        public Picture(int pager_index_imageBottom, int pager_index_imageTop) {
            this.PicResIDBottom = pager_index_imageBottom;
            this.PicResID = pager_index_imageTop;
        }

        public int getPicResID() {
            return PicResID;
        }

        public void setPicResID(int pager_index_imageTop) {
            this.PicResID = pager_index_imageTop;
        }

        public int getPicResIDBottom() {
            return PicResIDBottom;
        }

        public void setPicResIDBottom(int pager_index_imageBottom) {
            this.PicResIDBottom = pager_index_imageBottom;
        }
    }
}
