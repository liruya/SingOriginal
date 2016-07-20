package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singoriginal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoPage2Fragment extends Fragment
{


    public InfoPage2Fragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.info_page2, null);
        return view;
    }

}
