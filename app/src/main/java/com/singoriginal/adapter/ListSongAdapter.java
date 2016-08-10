package com.singoriginal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.dialog.SongmoreDialog;
import com.singoriginal.model.AdvertSong;
import com.singoriginal.model.DailyRecmd;
import com.singoriginal.model.Music;
import com.singoriginal.model.NewSong;
import com.singoriginal.model.PopularSong;
import com.singoriginal.model.RankSong;
import com.singoriginal.util.MusicUtil;

import java.util.ArrayList;

/**
 * 乐库.推荐.轮播图.详情数据适配器
 * Created by lanouhn on 16/7/25.
 */
public class  ListSongAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Object> advertSongs;
    private int code;

    public ListSongAdapter(Context context, ArrayList<Object> advertSongs, int code)
    {
        this.context = context;
        this.advertSongs = advertSongs;
        this.code = code;
    }

    @Override
    public int getCount()
    {
        return advertSongs == null ? 0 : advertSongs.size();
    }

    @Override
    public Object getItem(int position)
    {
        return advertSongs.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SongViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_songlist, null);
            holder = new SongViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.itemsong_tv_title);
            holder.tv_author = (TextView) convertView.findViewById(R.id.itemsong_tv_athor);
            holder.ib_more = (ImageButton) convertView.findViewById(R.id.itemsong_ib_more);
            holder.cb = convertView.findViewById(R.id.itemsong_view);
            convertView.setTag(holder);
        }
        else
        {
            holder = (SongViewHolder) convertView.getTag();
        }
        Object object = advertSongs.get(position);
        final Music msc = MusicUtil.convertMusicType(context, object);
        String title = "";
        String author = "";
        switch (code)
        {
            case ConstVal.SONGLIST_DETAIL_CODE:
                AdvertSong advertSong = (AdvertSong) object;
                title = advertSong.getSN();
                author = advertSong.getUser().getNN();
                break;

            case ConstVal.RANKFC_CODE:
            case ConstVal.RANKYC_CODE:
                RankSong rankSong = (RankSong) object;
                title = rankSong.getSN();
                author = rankSong.getUser().getNN();
                break;

            case ConstVal.RANKTP_CODE:
                NewSong newSong = (NewSong) object;
                title = newSong.getSN();
                author = newSong.getUser().getNN();
                break;

            case ConstVal.RANKPOP_CODE:
                PopularSong popularSong = (PopularSong) object;
                title = popularSong.getSN();
                author = popularSong.getUser().getNN();
                break;

            case ConstVal.DAILYRECMD_CODE:
                DailyRecmd dailyRecmd = (DailyRecmd) object;
                title = dailyRecmd.getRecommendName();
                author = dailyRecmd.getNickName();
                break;
        }
        holder.tv_title.setText(title);
        holder.tv_title.setTextColor(ConstVal.COLOR_SHALLOWBLACK);
        holder.tv_author.setText(author);
        holder.tv_author.setTextColor(ConstVal.COLOR_GRAY);
        holder.ib_more.setImageResource(R.mipmap.player_more_selected);
        holder.cb.setVisibility(View.INVISIBLE);
        holder.ib_more.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SongmoreDialog.showDialog(context,
                                          msc);
            }
        });
        return convertView;
    }

    class SongViewHolder
    {
        private TextView tv_title;
        private TextView tv_author;
        private ImageButton ib_more;
        private View cb;
    }
}
