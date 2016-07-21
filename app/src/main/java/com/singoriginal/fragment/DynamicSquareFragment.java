package com.singoriginal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.singoriginal.R;
import com.singoriginal.constant.CommanVal;
import com.singoriginal.constant.ConstVal;

/**
 * Created by lanouhn on 16/7/19.
 */
public class DynamicSquareFragment extends Fragment {

    public DynamicSquareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dynamic_square, null);

        initView(view);
        initEvent(view);
        return view;
    }

    private void initEvent(View view) {

    }

    private void initView(View view) {

    }

}
