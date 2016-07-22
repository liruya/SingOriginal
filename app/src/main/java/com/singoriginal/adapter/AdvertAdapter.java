package com.singoriginal.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.singoriginal.model.Advert;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * 音乐.推荐.轮播图数据适配器
 * Created by lanouhn on 16/7/20.
 */
public class AdvertAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<Advert> advertList;
    private int height;

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public AdvertAdapter(Context context, ArrayList<Advert> advertList)
    {
        this.context = context;
        this.advertList = advertList;
    }

    @Override
    public int getCount()
    {
        return advertList == null ? 0 : (advertList.size()*2000);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        int pos = (position)%advertList.size();
        ImageView imgView = new ImageView(context);
        Picasso.with(context).load(advertList.get(pos).getImgUrl()).into(imgView);
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imgView);
        return imgView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    /**
     * 测量控件高度
     * @param view
     * @return
     */
    private int getViewHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }
}
