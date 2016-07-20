package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singoriginal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment
{
    private ViewPager recmd_vp_show;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        return view;
    }

    private void initView(View view)
    {
        recmd_vp_show = (ViewPager) view.findViewById(R.id.recmd_vp_show);
    }

}
