package com.singoriginal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * 音乐.推荐.轮播图数据适配器
 * Created by lanouhn on 16/7/20.
 */
public class AdvertAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<String> imgLinks;

    public AdvertAdapter(Context context, ArrayList<String> imgLinks)
    {
        this.context = context;
        this.imgLinks = imgLinks;
    }

    @Override
    public int getCount()
    {
        return imgLinks == null ? 0 : imgLinks.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        ImageView imgView = new ImageView(context);
        Picasso.with(context).load(imgLinks.get(position)).into(imgView);
        container.addView(imgView);
        return imgView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

}
