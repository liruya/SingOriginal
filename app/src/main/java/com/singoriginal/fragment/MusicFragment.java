package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment
{


    public MusicFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_music, null);

        initView(view);
        return view;
    }

    private void initView(View view)
    {
//        LayoutInflater.from(getContext()).inflate(R.layout.view_header, null);

        view.findViewById(R.id.msc_inc_hdr).setBackgroundColor(ConstVal.colorDKGreen);
    }

}
