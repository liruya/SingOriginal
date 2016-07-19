package com.singoriginal.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.singoriginal.R;
import com.singoriginal.adapter.PagerIndexAdapter;

import java.util.ArrayList;

public class IndexActivity extends AppCompatActivity {

    private ViewPager index_viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initView();
        initData();
    }

    private void initData() {

        ArrayList<Picture> dataList = new ArrayList<>();

        dataList.add(new Picture(R.mipmap.spec_1_top, R.mipmap.spec_1_bottom));
        dataList.add(new Picture(R.mipmap.spec_2_top, R.mipmap.spec_2_bottom));
        dataList.add(new Picture(R.mipmap.spec_3_top, R.mipmap.spec_3_bottom));

        //给viewPager绑定Adapter
        index_viewPager.setAdapter(new PagerIndexAdapter(IndexActivity.this, dataList));
        //设置默认的Pager
        index_viewPager.setCurrentItem(0);
    }

    private void initView() {

        index_viewPager = (ViewPager) findViewById(R.id.index_viewPager);
    }

    public final class Picture {

        private int pager_index_imageTop;
        private int pager_index_imageBottom;

        public Picture(int pager_index_imageBottom, int pager_index_imageTop) {
            this.pager_index_imageBottom = pager_index_imageBottom;
            this.pager_index_imageTop = pager_index_imageTop;
        }

        public int getPager_index_imageTop() {
            return pager_index_imageTop;
        }

        public void setPager_index_imageTop(int pager_index_imageTop) {
            this.pager_index_imageTop = pager_index_imageTop;
        }

        public int getPager_index_imageBottom() {
            return pager_index_imageBottom;
        }

        public void setPager_index_imageBottom(int pager_index_imageBottom) {
            this.pager_index_imageBottom = pager_index_imageBottom;
        }
    }
}
