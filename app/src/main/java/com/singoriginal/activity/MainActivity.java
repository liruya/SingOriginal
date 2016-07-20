package com.singoriginal;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

import com.singoriginal.fragment.MusicFragment;

public class MainActivity extends FragmentActivity
{
    private FrameLayout main_fl_show;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView()
    {
        main_fl_show = (FrameLayout) findViewById(R.id.main_fl_show);
        MusicFragment musicFrag = new MusicFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fl_show, musicFrag).commit();
//        main_fl_show.addView(new MusicFragment());
    }
}
