package com.singoriginal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.singoriginal.R;
import com.singoriginal.activity.FirstLandingIndexActivity;

import java.util.List;

/**
 * Created by lanouhn on 16/7/19.
 */
public class PagerIndexAdapter extends PagerAdapter {

    private List<FirstLandingIndexActivity.Picture> list;
    private Context context;

    public PagerIndexAdapter(Context context, List<FirstLandingIndexActivity.Picture> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //将PagerIndexItem的布局动态加载进来
        View view = View.inflate(context, R.layout.pager_index_item, null);

        ImageView pager_index_imageTop = (ImageView) view.findViewById(R.id.pager_index_imageTop);
        ImageView pager_index_imageBottom = (ImageView) view.findViewById(R.id.pager_index_imageBottom);

        int newPos = position % list.size();
        FirstLandingIndexActivity.Picture picture = list.get(newPos);

        int PicResID = picture.getPicResID();
        int PicResIDBottom = picture.getPicResIDBottom();

        pager_index_imageTop.setImageResource(PicResID);
        pager_index_imageBottom.setImageResource(PicResIDBottom);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);

        container.removeView((View) object);
    }
}
