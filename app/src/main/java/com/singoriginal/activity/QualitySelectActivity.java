package com.singoriginal.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class QualitySelectActivity extends AppCompatActivity {

    private ImageButton image;
    private RadioGroup quality_rg_online;
    private RadioGroup quality_rg_default;
    private RadioButton quality_rb_onlineAutomatic;
    private RadioButton quality_rb_onlineSmooth;
    private RadioButton quality_rb_onlineClear;
    private RadioButton quality_rb_onlineLive;
    private RadioButton quality_rb_defaultAutomatic;
    private RadioButton quality_rb_defaultSmooth;
    private RadioButton quality_rb_defaultClear;
    private RadioButton quality_rb_defaultLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_select);

        initView();
        initEvent();
    }

    private void initEvent() {

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        quality_rg_online.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.quality_rb_onlineAutomatic:

                        quality_rb_onlineAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_onlineSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_onlineSmooth:
                        quality_rb_onlineSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_onlineAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_onlineClear:
                        quality_rb_onlineSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_onlineLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_onlineLive:
                        quality_rb_onlineSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_onlineLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        break;
                }
            }
        });
        quality_rg_online.check(R.id.quality_rb_onlineAutomatic);
        quality_rg_default.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.quality_rb_defaultAutomatic:
                        quality_rb_defaultAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_defaultSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_defaultSmooth:
                        quality_rb_defaultAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_defaultClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_defaultClear:
                        quality_rb_defaultAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        quality_rb_defaultLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.quality_rb_defaultLive:
                        quality_rb_defaultAutomatic.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultSmooth.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultClear.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        quality_rb_defaultLive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.quality_yes, 0);
                        break;
                }
            }
        });
        quality_rg_default.check(R.id.quality_rb_defaultAutomatic);
    }

    private void initView() {

        quality_rg_online = (RadioGroup) findViewById(R.id.quality_rg_online);
        quality_rg_default = (RadioGroup) findViewById(R.id.quality_rg_default);
        quality_rb_onlineAutomatic = (RadioButton) findViewById(R.id.quality_rb_onlineAutomatic);
        quality_rb_onlineSmooth = (RadioButton) findViewById(R.id.quality_rb_onlineSmooth);
        quality_rb_onlineClear = (RadioButton) findViewById(R.id.quality_rb_onlineClear);
        quality_rb_onlineLive = (RadioButton) findViewById(R.id.quality_rb_onlineLive);
        quality_rb_defaultAutomatic = (RadioButton) findViewById(R.id.quality_rb_defaultAutomatic);
        quality_rb_defaultSmooth = (RadioButton) findViewById(R.id.quality_rb_defaultSmooth);
        quality_rb_defaultClear = (RadioButton) findViewById(R.id.quality_rb_defaultClear);
        quality_rb_defaultLive = (RadioButton) findViewById(R.id.quality_rb_defaultLive);
        //页面公用标题头初始化
        View incView = findViewById(R.id.quality_select_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.qualitySelect));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        incView.findViewById(R.id.hdr_ib_music).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
