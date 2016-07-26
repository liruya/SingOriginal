package com.singoriginal.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.singoriginal.R;
import com.singoriginal.constant.CommanVal;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.DynamicFragment;
import com.singoriginal.fragment.DynamicSquareFragment;
import com.singoriginal.fragment.NotloginFragment;

public class PublishDynamicActivity extends AppCompatActivity {

    private ImageView publish_iv_back;
    private EditText publish_et;
    private TextView publish_tv_publish;
    private TextView publish_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);

        initView();
        initData();
        initEvent();
    }

    private void initData() {

    }

    private void initEvent() {

        publish_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        publish_tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publish_et.getText().toString().length() == 0) {

                    Toast.makeText(PublishDynamicActivity.this, "动态内容为空不能发布哦", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(PublishDynamicActivity.this, "动态已发布", Toast.LENGTH_SHORT).show();
                }
            }
        });

        publish_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (publish_et.getText().toString().length() >= 0)
                    publish_tv.setText((150 - publish_et.getText().toString().length()) + "");
            }
        });
    }

    private void initView() {

        publish_iv_back = (ImageView) findViewById(R.id.publish_iv_back);
        publish_et = (EditText) findViewById(R.id.publish_et);
        publish_tv_publish = (TextView) findViewById(R.id.publish_tv_publish);
        publish_tv = (TextView) findViewById(R.id.publish_tv);
    }
}
