package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.constant.CommanVal;
import com.singoriginal.constant.ConstVal;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_my, null);
        initView(view);
        return view;
    }

    /**
     * "我的"页面视图初始化
     * @param view
     */
    private void initView(View view)
    {
        //如果已登录则显示个人信息页面,否则显示登录注册页面 标题头及页面主体均不相同
        //页面公用标题头初始化
        View incView = view.findViewById(R.id.my_inc_hdr);
        incView.findViewById(R.id.hdr_ib_srch).setVisibility(View.INVISIBLE);
        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.GONE);
        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setText(getString(R.string.me));
        tv_second.setChecked(true);
        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.GONE);
        int color = CommanVal.isLogin ? ConstVal.COLOR_HYALINE : ConstVal.COLOR_DARKGREEN;
        incView.setBackgroundColor(color);
        //"我的"主体页面
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager()
                                                               .beginTransaction();
        Fragment frag;
        if (CommanVal.isLogin)
        {
            frag = new MyinfoFragment();
        }
        else
        {
            frag = new NotloginFragment();
        }
        beginTransaction.replace(R.id.my_fl_show, frag).commit();
    }
}
