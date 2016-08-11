package com.singoriginal.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.activity.HeadIconActivity;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Music;
import com.singoriginal.model.SongBrief;
import com.singoriginal.util.DownloadUtil;
import com.singoriginal.util.OkHttpUtil;
import com.singoriginal.util.ShareUtil;

import okhttp3.Request;

/**
 * 播放列表更多
 * Created by lanouhn on 16/8/10.
 */
public class SongmoreDialog
{
    public static void showDialog(final Context context, final Music msc)
    {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_songmore);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        dialog.show();

        final Handler hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case  ConstVal.SONGBRIEF_CODE:
                        SongBrief brief = new Gson().fromJson(json, SongBrief.class);
                        dialog.dismiss();
                        DownloadUtil.showDownloadDialog(context,
                                                        msc.getSongname() + " - " + msc.getUsername(),
                                                        brief);
                        break;
                }
            }
        };

        //收藏
        dialog.findViewById(R.id.dlg_songmore_like).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                showCollectDialog(context);
            }
        });

        //下载歌曲
        dialog.findViewById(R.id.dlg_songmore_download).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String url = ConstVal.GETSONGURL_LINK
                             + "songid=" + msc.getSongid()
                             + "&songtype=" + msc.getSongtype();
                Request request = new Request.Builder().url(url).build();
                OkHttpUtil.enqueue(context, hdl, ConstVal.SONGBRIEF_CODE, request);
            }
        });

        //歌曲评论
        dialog.findViewById(R.id.dlg_songmore_reply).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        //分享歌曲
        dialog.findViewById(R.id.dlg_songmore_share).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
//                showShareDialog(context, msc);
                String text = "我正在SingOriginal听 " + msc.getUsername() + " 的歌曲 " + msc.getSongname()
                              + ConstVal.SONG_INFO + msc.getSongtype() + "/" + msc.getSongid()
                              + ".html,你也快来听听吧.";
                ShareUtil.showShare(context, text, msc.getUserimg());
            }
        });

        //歌曲详情
        dialog.findViewById(R.id.dlg_songmore_detail).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        //会员主页
        dialog.findViewById(R.id.dlg_songmore_homepage).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("SIM", msc.getUserimg());
                intent.putExtra("SU", msc.getUsername());
                intent.putExtra("SUID", msc.getUserid() + "");
                dialog.dismiss();
                context.startActivity(intent);
            }
        });

        //取消 关闭dialog
        dialog.findViewById(R.id.dlg_songmore_cancel).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }

    private static void showCollectDialog(Context context)
    {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_collectsong);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        dialog.show();
    }

    public static void showShareDialog(final Context context, Music msc)
    {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_share);
        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        dialog.show();

        dialog.findViewById(R.id.dlg_share_link).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//
//                ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clipData = ClipData.newPlainText("分享 " + msc.getUsername() +" 的歌曲 "
//                                                          + msc.getSongname() + ConstVal.GETCURRENTDETAIL_LINK);
//                manager.setPrimaryClip(clipData);
                dialog.dismiss();
                Toast.makeText(context, "复制链接成功", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.findViewById(R.id.dlg_share_cancel).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }
}
