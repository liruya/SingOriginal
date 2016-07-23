package com.singoriginal.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
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
import com.singoriginal.util.OkHttpUtil;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {

    private RadioGroup titleRadioGroup;
    private RadioGroup squareRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dynamic, null);

        initView(view);
        initEvent(view);

        return view;
    }

    private void initEvent(final View view) {

        titleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hdr_rb_first:
                        View radView = view.findViewById(R.id.dynamic_radioGroup);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.dynamic_frameLayout, new NoAttentionMusicianFragment()).commit();
                        radView.setVisibility(View.GONE);
                        break;
                    case R.id.hdr_rb_third:
                        View radView2 = view.findViewById(R.id.dynamic_radioGroup);
                        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                        transaction2.replace(R.id.dynamic_frameLayout, new DynamicSquareFragment()).commit();
                        radView2.setVisibility(View.VISIBLE);
                        squareRadioGroup.check(R.id.dynamic_rb_hottest);
                        break;
                }
            }
        });
        titleRadioGroup.check(R.id.hdr_rb_first);

        squareRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                DynamicSquareFragment fragment = new DynamicSquareFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.dynamic_rb_newest:
                        transaction.replace(R.id.dynamic_frameLayout, fragment, "fragment");
//                        transaction.addToBackStack("fragment");
                        Bundle bundle = new Bundle();
                        bundle.putInt("code", 1);
                        fragment.setArguments(bundle);
                        transaction.commit();
                        break;
                    case R.id.dynamic_rb_hottest:
                        DynamicSquareFragment fragment2 = new DynamicSquareFragment();
                        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                        transaction2.replace(R.id.dynamic_frameLayout, fragment2, "fragment2");
//                        transaction2.addToBackStack("fragment2");
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("code", 0);
                        fragment2.setArguments(bundle2);
                        transaction2.commit();
                        break;
                }
            }
        });
    }

    private void initView(View view) {

        //如果已登录则显示个人信息页面,否则显示登录注册页面 标题头及页面主体均不相同
        //页面公用标题头初始化
        View incView = view.findViewById(R.id.dynamic_include_header);

        titleRadioGroup = (RadioGroup) incView.findViewById(R.id.hdr_rg_show);
        squareRadioGroup = (RadioGroup) view.findViewById(R.id.dynamic_radioGroup);

        ImageButton image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.find_dynamic_send_prs);

        RadioButton tv_first = (RadioButton) incView.findViewById(R.id.hdr_rb_first);
        tv_first.setText(getString(R.string.dynamic));

        incView.findViewById(R.id.hdr_rb_second).setVisibility(View.GONE);

        RadioButton tv_third = (RadioButton) incView.findViewById(R.id.hdr_rb_third);
        tv_third.setText(getString(R.string.square));

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
        Toast.makeText(getContext(), CommanVal.isLogin + "", Toast.LENGTH_SHORT).show();
        //"频道"主体页面
        FragmentTransaction beginTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        Fragment frag;
        if (CommanVal.isLogin) {
            frag = new DynamicSquareFragment();
        } else {
            frag = new NotloginFragment();
        }
        beginTransaction.replace(R.id.dynamic_frameLayout, frag).commit();
    }
}
