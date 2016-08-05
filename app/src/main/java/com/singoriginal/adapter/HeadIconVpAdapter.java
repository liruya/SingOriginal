package com.singoriginal.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by lanouhn on 16/8/3.
 */
public class HeadIconVpAdapter extends FragmentStatePagerAdapter{

    private List<String> list;
    private Context context;
    private List<Fragment> fragments;

    public HeadIconVpAdapter(FragmentManager fm) {
        super(fm);
    }

    public HeadIconVpAdapter(FragmentManager fm, Context context, List<String> list, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.list = list;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
