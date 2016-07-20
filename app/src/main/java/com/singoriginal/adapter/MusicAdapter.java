package com.singoriginal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by lanouhn on 16/7/20.
 */
public class MusicAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<Fragment> frags;

    public MusicAdapter(FragmentManager fm, ArrayList<Fragment> frags)
    {
        super(fm);
        this.frags = frags;
    }

    @Override
    public Fragment getItem(int position)
    {
        return frags.get(position);
    }

    @Override
    public int getCount()
    {
        return frags == null ? 0 : frags.size();
    }

}
