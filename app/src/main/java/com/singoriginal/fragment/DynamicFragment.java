package com.singoriginal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singoriginal.R;

/**
 * Created by lanouhn on 16/7/19.
 */
public class DynamicFragment extends Fragment {

    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic_square, container, false);
    }
}
