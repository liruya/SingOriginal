package com.singoriginal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.activity.SearchActivity;
import com.singoriginal.adapter.MusicAdapter;
import com.singoriginal.constant.ConstVal;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private ArrayList<Fragment> frags;
    private MusicAdapter mscAdapter;
    private ImageButton imageSearch;
    private ImageButton imageMusic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, null);

        initView(view);
        initEvent();
        return view;
    }

    /**
     * music页面组件初始化
     *
     * @param view
     */
    private void initView(View view) {
        View incView = view.findViewById(R.id.msc_inc_hdr);
        incView.setBackgroundColor(ConstVal.COLOR_DARKGREEN);
        imageSearch = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        imageMusic = (ImageButton) incView.findViewById(R.id.hdr_ib_music);
        final RadioGroup hdr_rg_show = (RadioGroup) incView.findViewById(R.id.hdr_rg_show);
        final ViewPager msc_vp_show = (ViewPager) view.findViewById(R.id.msc_vp_show);
        msc_vp_show.setOffscreenPageLimit(0);
        hdr_rg_show.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hdr_rb_first:
                        msc_vp_show.setCurrentItem(0);
                        break;

                    case R.id.hdr_rb_second:
                        msc_vp_show.setCurrentItem(1);
                        break;

                    case R.id.hdr_rb_third:
                        msc_vp_show.setCurrentItem(2);
                        break;
                }
            }
        });

        msc_vp_show.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        hdr_rg_show.check(R.id.hdr_rb_first);
                        break;

                    case 1:
                        hdr_rg_show.check(R.id.hdr_rb_second);
                        break;

                    case 2:
                        hdr_rg_show.check(R.id.hdr_rb_third);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hdr_rg_show.check(R.id.hdr_rb_first);

        RecommendFragment recmdFrag = new RecommendFragment();
        SonglistFragment songFrag = new SonglistFragment();
        ToplistFragment topFrag = new ToplistFragment();
        frags = new ArrayList<>();
        frags.add(recmdFrag);
        frags.add(songFrag);
        frags.add(topFrag);
        mscAdapter = new MusicAdapter(getActivity().getSupportFragmentManager(), frags);
        msc_vp_show.setAdapter(mscAdapter);
    }

    private void initEvent() {
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intent);
            }
        });
        imageMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
