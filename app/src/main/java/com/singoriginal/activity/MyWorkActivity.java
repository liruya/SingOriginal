package com.singoriginal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class MyWorkActivity extends AppCompatActivity {

    private ImageButton imageBack;
    private ImageButton imageUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);

        initView();
        initEvent();
    }

    private void initEvent() {

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyWorkActivity.this, UploadSongsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {

        //页面公用标题头初始化
        View incView = findViewById(R.id.my_work_header);

        imageBack = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        imageBack.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.mysong));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        imageUpload = (ImageButton) incView.findViewById(R.id.hdr_ib_music);
        imageUpload.setImageResource(R.mipmap.work_song_upload);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
