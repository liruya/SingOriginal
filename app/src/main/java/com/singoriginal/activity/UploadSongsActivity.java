package com.singoriginal.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;

public class UploadSongsActivity extends AppCompatActivity {

    private ImageButton image;
    private ImageView uploadSongs_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_songs);

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
        uploadSongs_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(UploadSongsActivity.this).inflate(R.layout.dialog_custom, null);
                TextView dialog_tv_cancel = (TextView) view.findViewById(R.id.dialog_tv_cancel);
                TextView dialog_tv_sure = (TextView) view.findViewById(R.id.dialog_tv_sure);
                dialog_tv_sure.setText("实名认证");
                TextView dialog_tv_content = (TextView) view.findViewById(R.id.dialog_tv_content);
                dialog_tv_content.setText("需实名认证才可以上传歌曲");
                final AlertDialog dialog = new AlertDialog.Builder(UploadSongsActivity.this).setView(view).show();
                //将对话框的大小按屏幕大小的百分比设置
                Window dialogWindow = dialog.getWindow();
                WindowManager m = getWindowManager();
                Display d2 = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//                p.height = (int) (d2.getHeight() * 0.6); // 高度设置为屏幕的0.6
                p.width = (int) (d2.getWidth() * 0.7); // 宽度设置为屏幕的0.65
                dialogWindow.setAttributes(p);

                dialog_tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void initView() {

        uploadSongs_iv = (ImageView) findViewById(R.id.uploadSongs_iv);
        //页面公用标题头初始化
        View incView = findViewById(R.id.upload_header);

        image = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        image.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.INVISIBLE);

        RadioButton tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setText(getString(R.string.upload_song));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.INVISIBLE);

        incView.findViewById(R.id.hdr_ib_music).setVisibility(View.INVISIBLE);

        int color = ConstVal.colorDKGreen;
        incView.setBackgroundColor(color);
    }
}
