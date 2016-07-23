package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.TopRank;
import com.singoriginal.util.RtfUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lanouhn on 16/7/23.
 */
public class TopRankAdapter extends RecyclerView.Adapter<TopRankAdapter.TopRankViewHolder>
{
    private Context context;
    private ArrayList<TopRank> tops;

    public TopRankAdapter(Context context, ArrayList<TopRank> tops)
    {
        this.context = context;
        this.tops = tops;
    }

    @Override
    public TopRankViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TopRankViewHolder holder =
                new TopRankViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rank, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(TopRankViewHolder holder, int position)
    {
        TopRank top = tops.get(position);
        String[] songs = top.getSongs();
        Picasso.with(context).load(top.getPhoto()).into(holder.iv_pic);
        for (int i = 0; i < songs.length; i++)
        {
            if (i >= holder.tvs.size())
            {
                break;
            }
            SpannableStringBuilder style = new SpannableStringBuilder();
            String song = songs[i];
            String sing;
            String author = "";
            if (song.contains("-"))
            {
                sing = song.substring(0, song.lastIndexOf("-"));
                author = " - " + song.substring(song.lastIndexOf("-") + 1);
            }
            else
            {
                sing = song;
            }
            style = RtfUtil.getRtf(style, (i + 1) + "  ", ConstVal.COLOR_GRAY, 12);
            style = RtfUtil.getRtf(style, sing, ConstVal.COLOR_SHALLOWBLACK, 14);
            style = RtfUtil.getRtf(style, author, ConstVal.COLOR_GRAY, 12);
            holder.tvs.get(i).setText(style, TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount()
    {
        return tops == null ? 0 : tops.size();
    }

    class TopRankViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv_pic;
        private ArrayList<TextView> tvs = new ArrayList<>();

        public TopRankViewHolder(View itemView)
        {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.item_top_iv);
            tvs.add((TextView) itemView.findViewById(R.id.item_top_tv1));
            tvs.add((TextView) itemView.findViewById(R.id.item_top_tv2));
            tvs.add((TextView) itemView.findViewById(R.id.item_top_tv3));
        }
    }
}
