package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.singoriginal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotloginFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notlogin, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        Button btnLogin = (Button) view.findViewById(R.id.nlgn_btn_login);
        Button btnReg = (Button) view.findViewById(R.id.nlgn_btn_reg);
    }

}
