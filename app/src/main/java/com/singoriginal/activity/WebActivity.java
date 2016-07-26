package com.singoriginal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.singoriginal.R;

public class WebActivity extends AppCompatActivity
{
    private WebView web_show;
    private ImageButton tit_ib_back;
    private TextView tit_tv_tit;
    private ImageButton tit_ib_msc;

    private String title;
    private String link;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        link = intent.getStringExtra("LinkUrl");
        initView();
        initEvent();
    }

    private void initView()
    {
        View web_inc_tit = findViewById(R.id.web_inc_tit);
        tit_tv_tit = (TextView) web_inc_tit.findViewById(R.id.tit_tv_tit);
        tit_ib_back = (ImageButton) web_inc_tit.findViewById(R.id.tit_ib_back);
        tit_ib_msc = (ImageButton) web_inc_tit.findViewById(R.id.tit_ib_msc);

        //        tit_tv_tit.setMovementMethod(ScrollingMovementMethod.getInstance());
        web_show = (WebView) findViewById(R.id.web_show);
//        web_show.setNetworkAvailable(true);
        showWebView(web_show, link);
        tit_tv_tit.setText(web_show.getTitle());
    }

    private void showWebView(final WebView web, String url)
    {
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setDefaultTextEncodingName("utf-8");
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        web.loadUrl(url);
        web.setWebChromeClient(new WebChromeClient()
        {
            /**
             * 获取到链接标题
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
                tit_tv_tit.setText(view.getTitle());
            }
        });

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
            }

            /**
             * 使用webview组件来响应url加载事件 而不是默认浏览器加载
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                web.loadUrl(url);
                return true;
            }
        });
    }

    private void initEvent()
    {
        tit_ib_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        tit_ib_msc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}
