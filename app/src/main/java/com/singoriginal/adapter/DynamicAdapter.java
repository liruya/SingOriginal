package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.model.DynamicSquare;
import com.singoriginal.model.DynamicSquareContent;
import com.singoriginal.model.DynamicSquareUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lanouhn on 16/7/21.
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {

    private Context context;
    private List<DynamicSquare> dataList;
    private Boolean isPress;
    private String tm;

    public DynamicAdapter(Context context, List<DynamicSquare> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //找到布局文件，并创建ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_recycler, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;

//        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dynamic_recycler, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Gson gson = new Gson();

        DynamicSquare dynamic = dataList.get(position);
        final DynamicSquareContent content = gson.fromJson(dynamic.getContent(), DynamicSquareContent.class);

        holder.item_dynamic_nickname.setText(dynamic.getUser().getNN());
        //获取时间并且转换时间格式
        Date date = fromDnetTicksToJdate(dynamic.getCreatetime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        tm = df.format(date);

        try {
            long millionSeconds = sdf.parse(time).getTime();
            holder.item_dynamic_time.setText(getStandardDate(millionSeconds, tm));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.item_dynamic_content.setText(content.getContent());
        holder.item_dynamic_songName.setText(content.getSongName());
        holder.item_dynamic_laudNum.setText(content.getLikes() + "");
        holder.item_dynamic_commentNum.setText(content.getComments() + "");

        //判断歌曲类型是原创还是翻唱
        if (content.getSongType() == 1)
            holder.item_dynamic_songType.setText(R.string.publishOriginal);
        else holder.item_dynamic_songType.setText(R.string.publishCover);

        Picasso.with(context).load(dynamic.getUser().getI())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(holder.item_dynamic_icon);

        //判断获取内容的行数
        if (content.getContent() != null && !content.getContent().equals("")) {
//            int lines = holder.item_dynamic_content.getLineCount();// 无用
            holder.item_dynamic_content.setText(content.getContent());
            if (content.getContent().trim().length() > 80) {
                holder.item_dynamic_content.setLines(3);
                holder.item_dynamic_more.setVisibility(View.VISIBLE);
            } else
                holder.item_dynamic_more.setVisibility(View.GONE);
        } else
            holder.item_dynamic_content.setText(content.getContent());

        holder.item_dynamic_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.item_dynamic_content.setText(content.getContent());
                holder.item_dynamic_more.setVisibility(View.GONE);
                int lines = holder.item_dynamic_content.getLineCount();
                holder.item_dynamic_content.setLines(lines);
                holder.item_dynamic_shrink.setVisibility(View.VISIBLE);
            }
        });

        holder.item_dynamic_shrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.item_dynamic_content.setLines(3);
                holder.item_dynamic_shrink.setVisibility(View.GONE);
                holder.item_dynamic_more.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 将C#的ticks值转换成Java的Date对象
     *
     * @param ticks
     * @return
     */
    public static Date fromDnetTicksToJdate(long ticks) {

        TimeZone timeZone = TimeZone.getDefault();
        long TICKS_AT_EPOCH = 621355968000000000L;
        long TICKS_PER_MILLISECOND = 10000;
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis((ticks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND);
        calendar.setTimeInMillis(calendar.getTimeInMillis() - calendar.getTimeZone().getRawOffset());
        return calendar.getTime();
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getStandardDate(long timeStr, String tm) {

        StringBuffer sb = new StringBuffer();

//        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - timeStr;
        long mill = (long) Math.ceil(time / 1000);//秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
//        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

//        if (day - 1 > 0) {
//            sb.append(day + "天");
//        } else
        if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append(tm);
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚") && hour < 24) {
            sb.append("前");
        }
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 创建一个ViewHolder类，继承于RecyclerView.ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //声明item布局当中的控件
        private ImageView item_dynamic_icon;
        private TextView item_dynamic_nickname;
        private TextView item_dynamic_time;
        private TextView item_dynamic_textIcon;
        private TextView item_dynamic_content;
        private TextView item_dynamic_songName;
        private TextView item_dynamic_laudNum;
        private TextView item_dynamic_commentNum;
        private TextView item_dynamic_songType;

        private TextView item_dynamic_more;
        private TextView item_dynamic_shrink;

        // 自定义ViewHolder的构造方法，用于找到item当中的控件
        public ViewHolder(View itemView) {
            super(itemView);

            item_dynamic_icon = (ImageView) itemView.findViewById(R.id.item_dynamic_icon);
            item_dynamic_nickname = (TextView) itemView.findViewById(R.id.item_dynamic_nickname);
            item_dynamic_time = (TextView) itemView.findViewById(R.id.item_dynamic_time);
            item_dynamic_textIcon = (TextView) itemView.findViewById(R.id.item_dynamic_textIcon);
            item_dynamic_content = (TextView) itemView.findViewById(R.id.item_dynamic_content);
            item_dynamic_songName = (TextView) itemView.findViewById(R.id.item_dynamic_songName);
            item_dynamic_laudNum = (TextView) itemView.findViewById(R.id.item_dynamic_laudNum);
            item_dynamic_commentNum = (TextView) itemView.findViewById(R.id.item_dynamic_commentNum);
            item_dynamic_songType = (TextView) itemView.findViewById(R.id.item_dynamic_songType);

            item_dynamic_more = (TextView) itemView.findViewById(R.id.item_dynamic_more);
            item_dynamic_shrink = (TextView) itemView.findViewById(R.id.item_dynamic_shrink);
        }
    }
}
