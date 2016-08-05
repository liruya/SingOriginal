package com.singoriginal.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.singoriginal.R;
import com.singoriginal.model.SongBrief;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 下载工具类
 * Created by lanouhn on 16/8/4.
 */
public class DownloadUtil
{
    private ArrayList dlList = new ArrayList();
    private static DownloadManager downloadManager;
    private static DownloadManager.Query query;
    private static DownloadManager.Request request;
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    /**
     * 启动下载音乐
     * @param context
     * @param url 音乐网络地址
     * @param filename 要保存的文件名
     * @return
     */
    public static long startDownloadMusic(Context context, String url, String filename)
    {
        //如果未创建DownloadManager对象则创建
        if (downloadManager == null)
        {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        Uri uri = Uri.parse(url);
        final long idx;
        //判断网络连接是否有效
        if (uri == null)
        {
            Toast.makeText(context, "无效的下载地址", Toast.LENGTH_SHORT).show();
            return -1;
        }
        //判断网络是否可用
        if (!NetUtil.isNetworkAvailable(context))
        {
            Toast.makeText(context, "网络连接不可用", Toast.LENGTH_SHORT).show();
            return -1;
        }
        else
        {
            //判断是否移动网络
            if (NetUtil.getNetType().equals("MOBILE"))
            {
                Toast.makeText(context, "移动网络不可下载", Toast.LENGTH_SHORT).show();
                return -1;
            }
        }
        //创建请求
        request = new DownloadManager.Request(uri);
        //移动网络下是否允许漫游下载
        request.setAllowedOverRoaming(false);
        //在哪种网络下下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏
        request.setTitle("下载");
        request.setDescription(filename);
        //设置通知在下载过程和下载完成后可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //允许MediaScanner扫描到这个文件夹
        request.allowScanningByMediaScanner();
        //设置下载路径及文件名
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_MUSIC, filename);
        //返回唯一的下载ID
        idx = downloadManager.enqueue(request);
        Runnable command = new Runnable()
        {
            @Override
            public void run()
            {
                DownloadInfo info = new DownloadInfo();
                DecimalFormat df = new DecimalFormat("00.0%");
                while (!info.completed)
                {
                    info = queryDownload(idx);
                    Log.e("TAG", "run: " + df.format(info.progress));
                }
            }
        };
        scheduledExecutorService.scheduleAtFixedRate(command, 100, 500, TimeUnit.MILLISECONDS);
        Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
        return idx;
    }

    private static DownloadInfo queryDownload(long idx)
    {
        DownloadInfo info = null;

        query = new DownloadManager.Query();
        query.setFilterById(idx);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst())
        {
            int currSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
            String filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            boolean completed;
            if (status == DownloadManager.STATUS_SUCCESSFUL)
            {
                completed = true;
            }
            else
            {
                completed = false;
            }
            float progress = (float)currSize/totalSize;
            info = new DownloadInfo(idx, fileName, filePath, currSize, totalSize, progress, completed);
        }
        return info;
    }


//    public class DownloadReceiver extends BroadcastReceiver
//    {
//
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            if (reference == -1)
//            {
//                return;
//            }
//            query = new DownloadManager.Query();
//            query.setFilterById(reference);
//            Cursor cursor = downloadManager.query(query);
//            if (cursor.moveToFirst())
//            {
//                int currSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
//                int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
//                String filename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
//                float progress = (float)currSize/totalSize;
//            }
//        }
//    }

    public static void showDownloadDialog(final Context context, String title, SongBrief brief)
    {
        int[] resid = {R.id.dlg_rb_kl, R.id.dlg_rb_klm, R.id.dlg_rb_klm128};
        DecimalFormat df = new DecimalFormat("0.0");
        final String[] items = new String[]{"流畅音质(" + df.format((float)brief.getData().getLqsize() / 1000000) + " M)",
                                      "清晰音质(" + df.format((float)brief.getData().getHqsize()/1000000) + " M)",
                                      "高清音质(" + df.format((float)brief.getData().getSqsize()/1000000) + " M)"};
        final String[] links = new String[]{brief.getData().getLqurl(),
                                            brief.getData().getHqurl(),
                                            brief.getData().getSqurl()};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置dialog视图
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_download, null);
        TextView tv_title = (TextView) view.findViewById(R.id.dlg_tv_title);
        tv_title.setText(title);
        for (int i = 0; i < 3; i++)
        {
            RadioButton rb = (RadioButton) view.findViewById(resid[i]);
            if (links[i] == null || links[i].equals(""))
            {
                rb.setText(items[i].replace("(0.0 M)", ""));
                rb.setEnabled(false);
            }
            else
            {
                rb.setText(items[i]);
            }
        }
        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.dlg_rg_show);
        Button dld = (Button) view.findViewById(R.id.dlg_tv_dld);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
        window.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        dialog.show();
        dld.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String link = null;
                int idx = rg.getCheckedRadioButtonId();
                if (idx == -1)
                {
                    Toast.makeText(context, "请选择要下载的歌曲类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (idx)
                {
                    case R.id.dlg_rb_kl:
                        link = links[0];
                        break;

                    case R.id.dlg_rb_klm:
                        link = links[1];
                        break;

                    case R.id.dlg_rb_klm128:
                        link = links[2];
                        break;
                }
                long index = startDownloadMusic(context, link, link.substring(link.lastIndexOf("/") + 1));
                dialog.dismiss();
            }
        });
    }

    static class DownloadInfo
    {
        private long idx;
        private String fileName;
        private String filePath;
        private long currentSize;
        private long totalSize;
        private float progress;
        private boolean completed;

        public DownloadInfo()
        {
        }

        public DownloadInfo(long idx,
                            String fileName,
                            String filePath,
                            long currentSize,
                            long totalSize,
                            float progress,
                            boolean completed)
        {
            this.idx = idx;
            this.fileName = fileName;
            this.filePath = filePath;
            this.currentSize = currentSize;
            this.totalSize = totalSize;
            this.progress = progress;
            this.completed = completed;
        }

        public boolean isCompleted()
        {
            return completed;
        }

        public void setCompleted(boolean completed)
        {
            this.completed = completed;
        }

        public long getCurrentSize()
        {
            return currentSize;
        }

        public void setCurrentSize(long currentSize)
        {
            this.currentSize = currentSize;
        }

        public String getFileName()
        {
            return fileName;
        }

        public void setFileName(String fileName)
        {
            this.fileName = fileName;
        }

        public String getFilePath()
        {
            return filePath;
        }

        public void setFilePath(String filePath)
        {
            this.filePath = filePath;
        }

        public long getIdx()
        {
            return idx;
        }

        public void setIdx(long idx)
        {
            this.idx = idx;
        }

        public long getTotalSize()
        {
            return totalSize;
        }

        public void setTotalSize(long totalSize)
        {
            this.totalSize = totalSize;
        }
    }
}
