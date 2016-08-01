package com.singoriginal.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Musician;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;
import com.singoriginal.util.RtfUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;

/**
 * musician数据适配器
 * Created by lanouhn on 16/7/28.
 */
public class MusicianListAdapter extends BaseAdapter
{
    private Context context;
    private String link;
    private ArrayList<Musician> list;

    public MusicianListAdapter(Context context, String link)
    {
        this.context = context;
        this.link = link;

        Handler hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case ConstVal.MUSICIAN_CODE:
                        String js = GsonUtil.getJsonArray(json);
                        list = new ArrayList<>();
                        try
                        {
                            JSONArray ary = new JSONArray(js);
                            for (int i = 0; i < ary.length(); i++)
                            {
                                Musician msc = new Gson().fromJson(ary.get(i).toString().replace("[]", "{}"), Musician.class);
                                list.add(msc);
                            }
                            notifyDataSetChanged();
//                            list = new Gson().fromJson(ary.get(0).toString().replace("[]", "{}"),
//                                                       new TypeToken<ArrayList<Musician>>(){}.getType());
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        Request request = new Request.Builder().url(link).build();
        OkHttpUtil.enqueue(context, hdl, ConstVal.MUSICIAN_CODE, request);
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_musician, null);
            holder.civ_icon = (CircleImageView) convertView.findViewById(R.id.itmsc_civ_icon);
            holder.tv_author = (TextView) convertView.findViewById(R.id.itmsc_tv_name);
            holder.tv_team = (TextView) convertView.findViewById(R.id.itmsc_tv_team);
            holder.tv_song = (TextView) convertView.findViewById(R.id.itmsc_tv_song);
            holder.tb_att = (ToggleButton) convertView.findViewById(R.id.itmsc_tb_att);
            holder.tb_play = (ToggleButton) convertView.findViewById(R.id.itmsc_tb_play);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Musician msc = list.get(position);
        SpannableStringBuilder builder = RtfUtil.getRtf(null, msc.getM(), ConstVal.COLOR_SHALLOWBLACK, 35);
        builder = RtfUtil.getRtf(builder, "  人气: " + ((msc.getYCRQ()+msc.getFCRQ())/10000) + "万", ConstVal.COLOR_GRAY, 30);
        Picasso.with(context).load(msc.getI()).resize(256, 256).centerCrop().into(holder.civ_icon);
        holder.tv_author.setText(msc.getNN());
        holder.tv_team.setText(builder, TextView.BufferType.SPANNABLE);
        holder.tv_song.setText("最新作品:" + msc.getSong().getSN());
        return convertView;
    }

    class ViewHolder
    {
        private CircleImageView civ_icon;
        private TextView tv_author;
        private TextView tv_team;
        private TextView tv_song;
        private ToggleButton tb_att;
        private ToggleButton tb_play;
    }
}
