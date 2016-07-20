package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.singoriginal.R;
import com.singoriginal.adapter.InfoAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyinfoFragment extends Fragment
{
    private ArrayList<View> list;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_myinfo, null);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        list = new ArrayList<>();
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.info_page1, null);
        View view2 = LayoutInflater.from(getContext()).inflate(R.layout.info_page2, null);
        list.add(view1);
        list.add(view2);
        InfoAdapter adapter = new InfoAdapter(list);
        ViewPager my_vp_info = (ViewPager) view.findViewById(R.id.my_vp_info);
        my_vp_info.setAdapter(adapter);
        int h = getViewHeight(view1);
        //创建一个layoutparams对象
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) my_vp_info.getLayoutParams();
        linearParams.height = h;
        my_vp_info.setLayoutParams(linearParams);
    }

    /**
     * 测量控件高度
     * @param view
     * @return
     */
    private int getViewHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }

}
