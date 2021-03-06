package com.singoriginal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class LocalSongsActivity extends AppCompatActivity {

    private ImageButton image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_songs);

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
    }

    private void initView() {

        //页面公用标题头初始化
        View incView = findViewById(R.id.local_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.local_song));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        ImageButton image2 = (ImageButton) incView.findViewById(R.id.hdr_ib_music);
        image2.setImageResource(R.mipmap.play_more);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
