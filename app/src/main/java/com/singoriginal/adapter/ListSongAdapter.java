package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.model.AdvertSong;

import java.util.ArrayList;

/**
 * 乐库.推荐.轮播图.详情数据适配器
 * Created by lanouhn on 16/7/25.
 */
public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.SongViewHolder>
{
    private Context context;
    private ArrayList<AdvertSong> advertSongs;

    public ListSongAdapter(Context context, ArrayList<AdvertSong> advertSongs)
    {
        this.context = context;
        this.advertSongs = advertSongs;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        SongViewHolder holder = new SongViewHolder(LayoutInflater
                                                           .from(context)
                                                           .inflate(R.layout.item_songlist, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position)
    {
        AdvertSong advertSong = advertSongs.get(position);
        holder.tv_title.setText(advertSong.getSN());
        holder.tv_author.setText(advertSong.getUser().getNN());
        holder.ib_more.setImageResource(R.mipmap.player_more_selected);
    }

    @Override
    public int getItemCount()
    {
        return advertSongs == null ? 0 : advertSongs.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_title;
        private TextView tv_author;
        private ImageButton ib_more;
        public SongViewHolder(View itemView)
        {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.itemsong_tv_title);
            tv_author = (TextView) itemView.findViewById(R.id.itemsong_tv_athor);
            ib_more = (ImageButton) itemView.findViewById(R.id.itemsong_ib_more);
        }
    }
}
