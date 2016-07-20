package com.singoriginal.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.singoriginal.R;

public class IndexActivity extends AppCompatActivity {

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0x101) {

                if (msg.arg1 > 0) {

                } else {
                    Intent intent = new Intent(IndexActivity.this, FirstLandingIndexActivity.class);
                    startActivity(intent);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initData();
    }

    private void initData() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i >= 0; i--) {
                    Message msg = handler.obtainMessage();
                    msg.what = 0x101;
                    msg.arg1 = i;
                    SystemClock.sleep(1000);
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }


}
