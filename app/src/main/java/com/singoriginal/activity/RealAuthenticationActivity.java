package com.singoriginal.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.singoriginal.R;

public class RealAuthenticationActivity extends AppCompatActivity {

    private RadioGroup authentication_rg;
    private TextView authentication_tv_cancel;
    private TextView authentication_tv_commit;
    private RadioButton authentication_rb_ID;
    private RadioButton authentication_rb_soldier;
    private RadioButton authentication_rb_student;
    private RadioButton authentication_rb_passport;
    private RadioButton authentication_rb_business;
    private ImageView authentication_iv_selectOne;
    private ImageView authentication_iv_selectTwo;
    private ImageView authentication_iv_selectThree;
    private ImageView authentication_iv_selectFour;
    private ImageView authentication_iv_selectFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_authentication);

        initView();
        initEvent();
    }

    private void initEvent() {

        authentication_tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出dialog
                View view = LayoutInflater.from(RealAuthenticationActivity.this).inflate(R.layout.dialog_custom, null);
                TextView dialog_tv_content = (TextView) view.findViewById(R.id.dialog_tv_content);
                dialog_tv_content.setText(R.string.isExitCommit);
                TextView dialog_tv_cancel = (TextView) view.findViewById(R.id.dialog_tv_cancel);
                TextView dialog_tv_sure = (TextView) view.findViewById(R.id.dialog_tv_sure);
                final AlertDialog dialog = new AlertDialog.Builder(RealAuthenticationActivity.this).setView(view).show();
                //将对话框的大小按屏幕大小的百分比设置
                Window dialogWindow = dialog.getWindow();
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.65
                dialogWindow.setAttributes(p);

                dialog_tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog_tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });

        authentication_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.authentication_rb_ID:
                        authentication_iv_selectOne.setVisibility(View.VISIBLE);
                        authentication_iv_selectTwo.setVisibility(View.INVISIBLE);
                        authentication_iv_selectThree.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFour.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFive.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.authentication_rb_soldier:
                        authentication_iv_selectOne.setVisibility(View.INVISIBLE);
                        authentication_iv_selectTwo.setVisibility(View.VISIBLE);
                        authentication_iv_selectThree.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFour.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFive.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.authentication_rb_student:
                        authentication_iv_selectOne.setVisibility(View.INVISIBLE);
                        authentication_iv_selectTwo.setVisibility(View.INVISIBLE);
                        authentication_iv_selectThree.setVisibility(View.VISIBLE);
                        authentication_iv_selectFour.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFive.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.authentication_rb_passport:
                        authentication_iv_selectOne.setVisibility(View.INVISIBLE);
                        authentication_iv_selectTwo.setVisibility(View.INVISIBLE);
                        authentication_iv_selectThree.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFour.setVisibility(View.VISIBLE);
                        authentication_iv_selectFive.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.authentication_rb_business:
                        authentication_iv_selectOne.setVisibility(View.INVISIBLE);
                        authentication_iv_selectTwo.setVisibility(View.INVISIBLE);
                        authentication_iv_selectThree.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFour.setVisibility(View.INVISIBLE);
                        authentication_iv_selectFive.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        authentication_rg.check(R.id.authentication_rb_ID);
    }

    private void initView() {

        authentication_rg = (RadioGroup) findViewById(R.id.authentication_rg);
        authentication_tv_cancel = (TextView) findViewById(R.id.authentication_tv_cancel);
        authentication_tv_commit = (TextView) findViewById(R.id.authentication_tv_commit);
        authentication_rb_ID = (RadioButton) findViewById(R.id.authentication_rb_ID);
        authentication_rb_soldier = (RadioButton) findViewById(R.id.authentication_rb_soldier);
        authentication_rb_student = (RadioButton) findViewById(R.id.authentication_rb_student);
        authentication_rb_passport = (RadioButton) findViewById(R.id.authentication_rb_passport);
        authentication_rb_business = (RadioButton) findViewById(R.id.authentication_rb_business);
        authentication_iv_selectOne = (ImageView) findViewById(R.id.authentication_iv_selectOne);
        authentication_iv_selectTwo = (ImageView) findViewById(R.id.authentication_iv_selectTwo);
        authentication_iv_selectThree = (ImageView) findViewById(R.id.authentication_iv_selectThree);
        authentication_iv_selectFour = (ImageView) findViewById(R.id.authentication_iv_selectFour);
        authentication_iv_selectFive = (ImageView) findViewById(R.id.authentication_iv_selectFive);
    }
}
