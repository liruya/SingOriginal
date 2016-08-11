package com.singoriginal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class CopyrightActivity extends AppCompatActivity {

    private ImageButton image;
    private WebView copyright_webView;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);

        Intent intent = getIntent();
        link = intent.getStringExtra("SURL");

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

        copyright_webView = (WebView) findViewById(R.id.copyright_webView);
        showWebView(copyright_webView, link);
        //页面公用标题头初始化
        View incView = findViewById(R.id.copyright_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.copyrightProtection));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        incView.findViewById(R.id.hdr_ib_music).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }

    private void showWebView(final WebView web, String url) {

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setDefaultTextEncodingName("utf-8");
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.loadUrl(url);

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            /**
             * 使用webview组件来响应url加载事件 而不是默认浏览器加载
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                web.loadUrl(url);
                return true;
            }
        });
    }
}
