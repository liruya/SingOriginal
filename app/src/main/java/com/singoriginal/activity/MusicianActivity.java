package com.singoriginal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.adapter.MusicianViewAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.MusicianFragment;
import com.singoriginal.model.MusicData;

import java.util.ArrayList;

public class MusicianActivity extends AppCompatActivity
{
    private View musician_inc_hdr;
    private ViewPager musician_vp_show;
    private ArrayList<Fragment> frags;
    private MusicianViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musician);

        initView();
        initData();
    }

    private void initView()
    {
        musician_inc_hdr = findViewById(R.id.musician_inc_hdr);
        musician_vp_show = (ViewPager) findViewById(R.id.musician_vp_show);

        musician_inc_hdr.setBackgroundColor(ConstVal.colorDKGreen);
        final ImageButton ib_back = (ImageButton) musician_inc_hdr.findViewById(R.id.hdr_ib_srch);
        ImageButton ib_msc = (ImageButton) musician_inc_hdr.findViewById(R.id.hdr_ib_music);
        final RadioGroup rg_show = (RadioGroup) musician_inc_hdr.findViewById(R.id.hdr_rg_show);
        RadioButton rb_first = (RadioButton) musician_inc_hdr.findViewById(R.id.hdr_rb_first);
        musician_inc_hdr.findViewById(R.id.hdr_rb_second).setVisibility(View.GONE);
        RadioButton rb_third = (RadioButton) musician_inc_hdr.findViewById(R.id.hdr_rb_third);
        ib_back.setImageResource(R.mipmap.client_back);
        rb_first.setText("推荐");
        rb_third.setText("新入驻");

        ib_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        ib_msc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (MusicData.musicList != null && MusicData.musicList.size() > 0)
                {
                    Intent intent = new Intent(MusicianActivity.this, MusicDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        rg_show.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.hdr_rb_first:
                        musician_vp_show.setCurrentItem(0);
                        break;

                    case R.id.hdr_rb_third:
                        musician_vp_show.setCurrentItem(1);
                        break;
                }
            }
        });

        musician_vp_show.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position)
                {
                    case 0:
                        rg_show.check(R.id.hdr_rb_first);
                        break;

                    case 1:
                        rg_show.check(R.id.hdr_rb_third);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        rg_show.check(R.id.hdr_rb_first);
    }

    private void initData()
    {
        frags = new ArrayList<>();
        frags.add(MusicianFragment.NewInstance(ConstVal.RECMD_MUSICIAN_LINK + ConstVal.VERSION, 0));
        frags.add(MusicianFragment.NewInstance(ConstVal.NEW_MUSICIAN_LINK + ConstVal.VERSION, 1));
        adapter = new MusicianViewAdapter(getSupportFragmentManager(), frags);
        musician_vp_show.setAdapter(adapter);
    }

}
