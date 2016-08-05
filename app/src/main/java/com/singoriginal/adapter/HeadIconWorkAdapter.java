package com.singoriginal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.model.HeadIconWork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lanouhn on 16/8/4.
 */
public class HeadIconWorkAdapter extends BaseAdapter {

    private Context context;
    private List<HeadIconWork.Data> workList;
    private String tm;

    public HeadIconWorkAdapter(Context context, List<HeadIconWork.Data> workList) {
        this.context = context;
        this.workList = workList;
    }

    @Override
    public int getCount() {
        return workList.size();
    }

    @Override
    public Object getItem(int position) {
        return workList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_headicon_work_list, null);
            holder = new ViewHolder();
            holder.item_headWork_songName = (TextView) convertView.findViewById(R.id.item_headWork_songName);
            holder.item_headWork_time = (TextView) convertView.findViewById(R.id.item_headWork_time);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        HeadIconWork.Data work = workList.get(position);

        holder.item_headWork_songName.setText(work.getSN());
        //获取时间并且转换时间格式
        Date date = fromDnetTicksToJdate(work.getCT());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(date);
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        tm = df.format(date);

        try {
            long millionSeconds = sdf.parse(time).getTime();
            holder.item_headWork_time.setText(getStandardDate(millionSeconds, tm));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
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

    public class ViewHolder {

        private TextView item_headWork_songName;
        private TextView item_headWork_time;
    }

}
