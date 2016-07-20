package com.singoriginal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
        return view;
    }

    private void initView(View view) {

        //如果已登录则显示个人信息页面,否则显示登录注册页面 标题头及页面主体均不相同
        //页面公用标题头初始化
        View incView = view.findViewById(R.id.dynamic_include_header);

        ImageButton image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.find_dynamic_send_prs);

        RadioButton tv_first = (RadioButton) incView.findViewById(R.id.hdr_rb_first);
        tv_first.setText(getString(R.string.dynamic));
        tv_first.setChecked(true);

        incView.findViewById(R.id.hdr_rb_second).setVisibility(View.GONE);

        RadioButton tv_third = (RadioButton) incView.findViewById(R.id.hdr_rb_third);
        tv_third.setText(getString(R.string.square));

        int color = CommanVal.isLogin ? ConstVal.colorHyaline : ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
        Toast.makeText(getContext(), CommanVal.isLogin + "", Toast.LENGTH_SHORT).show();
        //"频道"主体页面
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        Fragment frag;
        if (CommanVal.isLogin) {
            frag = new MyinfoFragment();
        } else {
            frag = new NotloginFragment();
        }
        beginTransaction.replace(R.id.dynamic_frameLayout, frag).commit();
    }

}
