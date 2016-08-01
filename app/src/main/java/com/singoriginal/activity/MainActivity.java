package com.singoriginal.activity;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.ChannelFragment;
import com.singoriginal.fragment.DynamicFragment;
import com.singoriginal.fragment.MusicFragment;
import com.singoriginal.fragment.MyFragment;
import com.singoriginal.util.MusicUtil;

public class MainActivity extends FragmentActivity {
    private FrameLayout main_fl_show;
    private RadioGroup main_rg_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSysParam();
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy()
    {
        MusicUtil.unbindMusicService(this);
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initService();
    }

    /**
     * 获取系统参数
     */
    private void initSysParam()
    {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        ConstVal.SCREEN_WIDTH = metrics.widthPixels;
        ConstVal.SCREEN_HEIGHT = metrics.heightPixels;
    }

    private void initService()
    {
        ServiceConnection svcConn = MusicUtil.creatServiceConnection(this);
        MusicUtil.bindMusicService(this, svcConn);
    }

    /**
     * 初始化页面上的组件
     */
    private void initView() {
        main_fl_show = (FrameLayout) findViewById(R.id.main_fl_show);
        main_rg_show = (RadioGroup) findViewById(R.id.main_rg_show);
    }

    /**
     * 事件初始化
     */
    private void initEvent() {
        main_rg_show.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MusicFragment musicFrag = new MusicFragment();
                ChannelFragment chnFrag = new ChannelFragment();
                DynamicFragment dynFrag = new DynamicFragment();
                MyFragment myFrag = new MyFragment();
                FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
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
