package com.singoriginal.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.fragment.ChannelFragment;
import com.singoriginal.fragment.DynamicSquareFragment;
import com.singoriginal.fragment.MusicFragment;
import com.singoriginal.fragment.MyFragment;

public class MainActivity extends FragmentActivity
{
    private FrameLayout main_fl_show;
    private RadioGroup main_rg_show;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView()
    {
        main_fl_show = (FrameLayout) findViewById(R.id.main_fl_show);
        main_rg_show = (RadioGroup) findViewById(R.id.main_rg_show);
    }

    private void initEvent()
    {
        main_rg_show.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                MusicFragment musicFrag = new MusicFragment();
                ChannelFragment chnFrag = new ChannelFragment();
                DynamicSquareFragment dynFrag = new DynamicSquareFragment();
                MyFragment myFrag = new MyFragment();
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId)
                {
                    case R.id.main_rb_msc:
                        beginTransaction.replace(R.id.main_fl_show, musicFrag).commit();
                        break;

                    case R.id.main_rb_chn:
                        beginTransaction.replace(R.id.main_fl_show, chnFrag).commit();
                        break;

                    case R.id.main_rb_dyn:
                        beginTransaction.replace(R.id.main_fl_show, dynFrag).commit();
                        break;

                    case R.id.main_rb_my:
                        beginTransaction.replace(R.id.main_fl_show, myFrag).commit();
                        break;
                }
            }
        });
        main_rg_show.check(R.id.main_rb_msc);
    }
}
