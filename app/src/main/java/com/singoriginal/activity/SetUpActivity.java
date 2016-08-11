package com.singoriginal.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class SetUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton image;
    private LinearLayout set_ll_alter;
    private LinearLayout set_ll_authentication;
    private LinearLayout set_ll_bind;
    private LinearLayout set_ll_clear;
    private LinearLayout set_ll_path;
    private LinearLayout set_ll_select;
    private LinearLayout set_ll_timeClose;
    private LinearLayout set_ll_receiveInfo;
    private LinearLayout set_ll_feedback;
    private LinearLayout set_ll_checkNew;
    private LinearLayout set_ll_share;
    private LinearLayout set_ll_help;
    private LinearLayout set_ll_about;
    private LinearLayout set_ll_protect;
    private TextView set_tv_exit;
    private ImageView set_iv_receiveInfo;
    private ImageView set_iv_timeClose;
    private boolean isReceiveInfo = true;
    private boolean isStartTime = true;
    private LinearLayout set_ll_timeAll;
    private View set_view;
    private TextView set_tv_time;
    private ImageView set_iv_timeOne;
    private ImageView set_iv_timeTwo;
    private ImageView set_iv_timeThree;
    private ImageView set_iv_timeFour;
    private ImageView set_iv_timeFive;
    private TextView set_tv_15min;
    private TextView set_tv_30min;
    private TextView set_tv_45min;
    private TextView set_tv_60min;
    private TextView set_tv_90min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

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
        set_ll_alter.setOnClickListener(this);
        set_ll_authentication.setOnClickListener(this);
        set_ll_bind.setOnClickListener(this);
        set_ll_clear.setOnClickListener(this);
        set_ll_path.setOnClickListener(this);
        set_ll_select.setOnClickListener(this);
        set_ll_timeClose.setOnClickListener(this);
        set_ll_receiveInfo.setOnClickListener(this);
        set_ll_feedback.setOnClickListener(this);
        set_ll_checkNew.setOnClickListener(this);
        set_ll_share.setOnClickListener(this);
        set_ll_help.setOnClickListener(this);
        set_ll_about.setOnClickListener(this);
        set_ll_protect.setOnClickListener(this);
        set_tv_exit.setOnClickListener(this);
        set_iv_timeOne.setOnClickListener(this);
        set_iv_timeTwo.setOnClickListener(this);
        set_iv_timeThree.setOnClickListener(this);
        set_iv_timeFour.setOnClickListener(this);
        set_iv_timeFive.setOnClickListener(this);
        set_tv_15min.setOnClickListener(this);
        set_tv_30min.setOnClickListener(this);
        set_tv_45min.setOnClickListener(this);
        set_tv_60min.setOnClickListener(this);
        set_tv_90min.setOnClickListener(this);
    }

    private void initView() {

        set_ll_alter = (LinearLayout) findViewById(R.id.set_ll_alter);
        set_ll_authentication = (LinearLayout) findViewById(R.id.set_ll_authentication);
        set_ll_bind = (LinearLayout) findViewById(R.id.set_ll_bind);
        set_ll_clear = (LinearLayout) findViewById(R.id.set_ll_clear);
        set_ll_path = (LinearLayout) findViewById(R.id.set_ll_path);
        set_ll_select = (LinearLayout) findViewById(R.id.set_ll_select);
        set_ll_timeClose = (LinearLayout) findViewById(R.id.set_ll_timeClose);
        set_ll_receiveInfo = (LinearLayout) findViewById(R.id.set_ll_receiveInfo);
        set_ll_feedback = (LinearLayout) findViewById(R.id.set_ll_feedback);
        set_ll_checkNew = (LinearLayout) findViewById(R.id.set_ll_checkNew);
        set_ll_share = (LinearLayout) findViewById(R.id.set_ll_share);
        set_ll_help = (LinearLayout) findViewById(R.id.set_ll_help);
        set_ll_about = (LinearLayout) findViewById(R.id.set_ll_about);
        set_ll_protect = (LinearLayout) findViewById(R.id.set_ll_protect);
        set_tv_exit = (TextView) findViewById(R.id.set_tv_exit);
        set_iv_receiveInfo = (ImageView) findViewById(R.id.set_iv_receiveInfo);
        set_iv_timeClose = (ImageView) findViewById(R.id.set_iv_timeClose);
        set_ll_timeAll = (LinearLayout) findViewById(R.id.set_ll_timeAll);
        set_view = findViewById(R.id.set_view);
        set_tv_time = (TextView) findViewById(R.id.set_tv_time);
        set_iv_timeOne = (ImageView) findViewById(R.id.set_iv_timeOne);
        set_iv_timeTwo = (ImageView) findViewById(R.id.set_iv_timeTwo);
        set_iv_timeThree = (ImageView) findViewById(R.id.set_iv_timeThree);
        set_iv_timeFour = (ImageView) findViewById(R.id.set_iv_timeFour);
        set_iv_timeFive = (ImageView) findViewById(R.id.set_iv_timeFive);
        set_tv_15min = (TextView) findViewById(R.id.set_tv_15min);
        set_tv_30min = (TextView) findViewById(R.id.set_tv_30min);
        set_tv_45min = (TextView) findViewById(R.id.set_tv_45min);
        set_tv_60min = (TextView) findViewById(R.id.set_tv_60min);
        set_tv_90min = (TextView) findViewById(R.id.set_tv_90min);

        //页面公用标题头初始化
        View incView = findViewById(R.id.set_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.settings));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.set_ll_alter:
                intent.setClass(this, AlterActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_authentication:
                intent.setClass(this, RealAuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_bind:
                intent.setClass(this, BindActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_clear:
                View view = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null);
                TextView dialog_tv_content = (TextView) view.findViewById(R.id.dialog_tv_content);
                dialog_tv_content.setText(R.string.isClearCache);
                TextView dialog_tv_cancel = (TextView) view.findViewById(R.id.dialog_tv_cancel);
                TextView dialog_tv_sure = (TextView) view.findViewById(R.id.dialog_tv_sure);
                final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).show();
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
                break;
            case R.id.set_ll_path:

                break;
            case R.id.set_ll_select:
                intent.setClass(this, QualitySelectActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_timeClose:
                if (isStartTime) {
                    set_iv_timeClose.setImageResource(R.mipmap.timer_off);
                    set_ll_timeAll.setVisibility(View.VISIBLE);
                    set_iv_timeOne.performClick();
                    set_tv_time.setVisibility(View.VISIBLE);
                    set_view.setVisibility(View.GONE);
                } else {
                    set_iv_timeClose.setImageResource(R.mipmap.timer_on);
                    set_tv_time.setVisibility(View.INVISIBLE);
                    set_ll_timeAll.setVisibility(View.GONE);
                    set_view.setVisibility(View.VISIBLE);
                }
                isStartTime = !isStartTime;
                break;
            case R.id.set_ll_receiveInfo:
                if (isReceiveInfo) {
                    set_iv_receiveInfo.setImageResource(R.mipmap.timer_off);
                    Toast.makeText(this, "允许接收推送消息", Toast.LENGTH_SHORT).show();
                } else {
                    set_iv_receiveInfo.setImageResource(R.mipmap.timer_on);
                    Toast.makeText(this, "不允许接收推送消息", Toast.LENGTH_SHORT).show();
                }
                isReceiveInfo = !isReceiveInfo;
                break;
            case R.id.set_ll_feedback:
                intent.setClass(this, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_checkNew:

                break;
            case R.id.set_ll_share:

                break;
            case R.id.set_ll_help:
                intent.setClass(this, HelpCenterActivity.class);
                intent.putExtra("SURL", ConstVal.HELPCENTER_HTTP_PATH);
                startActivity(intent);
//                intent.setClass(this, HelpCenterActivity.class);
//                startActivity(intent);
                break;
            case R.id.set_ll_about:
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.set_ll_protect:
                intent.setClass(this, CopyrightActivity.class);
                intent.putExtra("SURL", ConstVal.PROTECT_HTTP_PATH);
                startActivity(intent);
//                intent.setClass(this, CopyrightActivity.class);
//                startActivity(intent);
                break;
            case R.id.set_tv_exit:
                View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null);
                TextView dialog_tv_cancel2 = (TextView) view2.findViewById(R.id.dialog_tv_cancel);
                TextView dialog_tv_sure2 = (TextView) view2.findViewById(R.id.dialog_tv_sure);
                final AlertDialog dialog2 = new AlertDialog.Builder(this).setView(view2).show();
                //将对话框的大小按屏幕大小的百分比设置
                Window dialogWindow2 = dialog2.getWindow();
                WindowManager m2 = getWindowManager();
                Display d2 = m2.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p2 = dialogWindow2.getAttributes(); // 获取对话框当前的参数值
//                p2.height = (int) (d2.getHeight() * 0.6); // 高度设置为屏幕的0.6
                p2.width = (int) (d2.getWidth() * 0.7); // 宽度设置为屏幕的0.65
                dialogWindow2.setAttributes(p2);

                dialog_tv_cancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                break;
            case R.id.set_iv_timeOne:
                set_iv_timeOne.setImageResource(R.mipmap.timer_off_seek_bar_thumb);
                set_tv_time.setVisibility(View.VISIBLE);
                set_tv_time.setText("14:59");
                set_tv_15min.setTextColor(ConstVal.COLOR_BLACK);
                set_tv_30min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_45min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_60min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_90min.setTextColor(ConstVal.COLOR_GRAY);
                Toast.makeText(this, "15分钟后关闭", Toast.LENGTH_SHORT).show();
                set_iv_timeTwo.setImageResource(0);
                set_iv_timeThree.setImageResource(0);
                set_iv_timeFour.setImageResource(0);
                set_iv_timeFive.setImageResource(0);
                break;
            case R.id.set_iv_timeTwo:
                set_iv_timeTwo.setImageResource(R.mipmap.timer_off_seek_bar_thumb);
                set_tv_time.setVisibility(View.VISIBLE);
                set_tv_time.setText("29:59");
                set_tv_30min.setTextColor(ConstVal.COLOR_BLACK);
                set_tv_15min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_45min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_60min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_90min.setTextColor(ConstVal.COLOR_GRAY);
                Toast.makeText(this, "30分钟后关闭", Toast.LENGTH_SHORT).show();
                set_iv_timeOne.setImageResource(0);
                set_iv_timeThree.setImageResource(0);
                set_iv_timeFour.setImageResource(0);
                set_iv_timeFive.setImageResource(0);
                break;
            case R.id.set_iv_timeThree:
                set_iv_timeThree.setImageResource(R.mipmap.timer_off_seek_bar_thumb);
                set_tv_time.setVisibility(View.VISIBLE);
                set_tv_time.setText("44:59");
                set_tv_45min.setTextColor(ConstVal.COLOR_BLACK);
                set_tv_15min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_30min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_60min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_90min.setTextColor(ConstVal.COLOR_GRAY);
                Toast.makeText(this, "45分钟后关闭", Toast.LENGTH_SHORT).show();
                set_iv_timeOne.setImageResource(0);
                set_iv_timeTwo.setImageResource(0);
                set_iv_timeFour.setImageResource(0);
                set_iv_timeFive.setImageResource(0);
                break;
            case R.id.set_iv_timeFour:
                set_iv_timeFour.setImageResource(R.mipmap.timer_off_seek_bar_thumb);
                set_tv_time.setVisibility(View.VISIBLE);
                set_tv_time.setText("59:59");
                set_tv_60min.setTextColor(ConstVal.COLOR_BLACK);
                set_tv_15min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_30min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_45min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_90min.setTextColor(ConstVal.COLOR_GRAY);
                Toast.makeText(this, "60分钟后关闭", Toast.LENGTH_SHORT).show();
                set_iv_timeOne.setImageResource(0);
                set_iv_timeTwo.setImageResource(0);
                set_iv_timeThree.setImageResource(0);
                set_iv_timeFive.setImageResource(0);
                break;
            case R.id.set_iv_timeFive:
                set_iv_timeFive.setImageResource(R.mipmap.timer_off_seek_bar_thumb);
                set_tv_time.setVisibility(View.VISIBLE);
                set_tv_time.setText("89:59");
                set_tv_90min.setTextColor(ConstVal.COLOR_BLACK);
                set_tv_15min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_30min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_45min.setTextColor(ConstVal.COLOR_GRAY);
                set_tv_60min.setTextColor(ConstVal.COLOR_GRAY);
                Toast.makeText(this, "90分钟后关闭", Toast.LENGTH_SHORT).show();
                set_iv_timeOne.setImageResource(0);
                set_iv_timeTwo.setImageResource(0);
                set_iv_timeThree.setImageResource(0);
                set_iv_timeFour.setImageResource(0);
                break;
        }
    }
}
