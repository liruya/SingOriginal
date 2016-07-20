package com.singoriginal.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * "我的"页面个人信息viewPager适配器
 * Created by lanouhn on 16/7/20.
 */
public class InfoAdapter extends PagerAdapter
{
    private ArrayList<View> list;

    public InfoAdapter(ArrayList<View> list)
    {
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = list.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    //    public InfoAdapter(FragmentManager fm, ArrayList<Fragment> list)
//    {
//        super(fm);
//        this.list = list;
//    }
//
//    @Override
//    public Fragment getItem(int position)
//    {
//        return list.get(position);
//    }
//
//    @Override
//    public int getCount()
//    {
//        return list == null ? 0 : list.size();
//    }

}
