package com.singoriginal.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.HeadIconMessageFragment;
import com.singoriginal.fragment.NoAttentionChannelFragment;

import java.util.List;

public class SongCommentActivity extends AppCompatActivity {

    private ImageButton imageBack;
    private String SID;
    private String SIDUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_comment);

        Intent intent = getIntent();
        SID = intent.getStringExtra("SID");
        SIDUrl = ConstVal.SONGCOMMENT_HTTP_PATH + SID
                + ConstVal.SONGCOMMENT_HTTP_PARAM + "&version" + ConstVal.VERSION2;

        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.song_comment_frameLayout, HeadIconMessageFragment.NewInstanceTwo(SIDUrl)).commit();

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
    }

    private void initView() {

        //页面公用标题头初始化
        View incView = findViewById(R.id.song_comment_header);

        imageBack = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        imageBack.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.songComment));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
