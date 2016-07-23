package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Hotlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lanouhn on 16/7/23.
 */
public class SonglistAdapter extends RecyclerView.Adapter<SonglistAdapter.SonglistViewHolder>
{
    private Context context;
    private ArrayList<Hotlist> list;

    public SonglistAdapter(Context context, ArrayList<Hotlist> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public SonglistViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        SonglistViewHolder holder = new SonglistViewHolder(LayoutInflater
                                                                   .from(context)
                                                                   .inflate(R.layout.item_song, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(SonglistViewHolder holder, int position)
    {
        Hotlist hot = list.get(position);
        Picasso.with(context).load(hot.getPicture()).into(holder.iv_show);
        holder.tv_name.setText(hot.getTitle());
        holder.tv_num.setText(hot.getPlayCount()+"");
    }

    @Override
    public int getItemCount()
    {
        return list == null ? 0 : list.size();
    }

    class SonglistViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv_show;
        private TextView tv_name;
        private TextView tv_num;
        public SonglistViewHolder(View itemView)
        {
            super(itemView);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ConstVal.SCREEN_WIDTH/2, ConstVal.SCREEN_WIDTH/2);
            itemView.findViewById(R.id.song_item_rtl).setLayoutParams(lp);
            iv_show = (ImageView) itemView.findViewById(R.id.song_iv_show);
            tv_name = (TextView) itemView.findViewById(R.id.song_tv_name);
            tv_num = (TextView) itemView.findViewById(R.id.song_tv_num);
        }
    }
}
