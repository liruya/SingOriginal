package com.singoriginal.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.activity.HeadIconActivity;
import com.singoriginal.activity.SongCommentActivity;
import com.singoriginal.activity.SongListActivity;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.SongDetails;
import com.singoriginal.util.MusicUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 16/7/26.
 */
public class SongDetailsAdapter extends RecyclerView.Adapter<SongDetailsAdapter.ViewHolder> {

    private Context context;
    private List<SongDetails> dataList;
    private boolean isLike = true;
    private int vsIdx = -1;
    private boolean isPlay = true;

    public SongDetailsAdapter(Context context, List<SongDetails> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //找到布局文件，并创建ViewHolder
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_song_details_recycler, null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        SongDetails sDetails = dataList.get(position);
        holder.item_song_songName.setText(sDetails.getSN());
        holder.item_song_nickname.setText(sDetails.getSU());
        //判断是否含有置顶标签，如果含有则显示
        if (sDetails.getSIC().equals("1")) {
            holder.item_song_top.setVisibility(View.VISIBLE);
        } else holder.item_song_top.setVisibility(View.GONE);

        if (!sDetails.getSRC().equals("")) {
            holder.item_song_llThree.setVisibility(View.VISIBLE);
            holder.item_song_tab3.setImageResource(R.mipmap.offer_left);
            holder.item_song_comment3.setText(sDetails.getSRC());
        }
        if (!sDetails.getPLONE().equals("")) {
            holder.item_song_llOne.setVisibility(View.VISIBLE);
            holder.item_song_tab.setImageResource(R.mipmap.comment_left);
            holder.item_song_comment.setText(sDetails.getPLONE());
        }
        if (!sDetails.getPLTWO().equals("")) {
            holder.item_song_llTwo.setVisibility(View.VISIBLE);
            holder.item_song_tab2.setImageResource(R.mipmap.comment_left);
            holder.item_song_comment2.setText(sDetails.getPLTWO());
        }

        Picasso.with(context).load(sDetails.getSIM())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(holder.item_song_icon);

        holder.item_song_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("SIM", dataList.get(position).getSIM());
                intent.putExtra("SU", dataList.get(position).getSU());
                intent.putExtra("SUID", dataList.get(position).getSUID());
                context.startActivity(intent);
            }
        });

        holder.item_song_llDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongCommentActivity.class);
                intent.putExtra("SID", dataList.get(position).getSID());
                context.startActivity(intent);
            }
        });
        holder.item_song_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    holder.item_song_heart.setImageResource(R.mipmap.new_collection);
                } else {
                    holder.item_song_heart.setImageResource(R.mipmap.my_collect);
                }
                isLike = !isLike;
            }
        });

        holder.item_song_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView tv_song = (TextView) v.findViewById(R.id.item_song_songName);
                TextView tv_name = (TextView) v.findViewById(R.id.item_song_nickname);
                TextView tv_singer = (TextView) v.findViewById(R.id.item_song_singer);
                tv_song.setTextColor(ConstVal.COLOR_DARKGREEN);
                tv_name.setTextColor(ConstVal.COLOR_DARKGREEN);
                tv_singer.setTextColor(ConstVal.COLOR_DARKGREEN);

//                if (vsIdx == position) {
//                    tv_song.setTextColor(ConstVal.COLOR_DARKGREEN);
//                    tv_name.setTextColor(ConstVal.COLOR_DARKGREEN);
//                    tv_singer.setTextColor(ConstVal.COLOR_DARKGREEN);
//                } else {
//                    tv_song.setTextColor(ConstVal.COLOR_BLACK);
//                    tv_name.setTextColor(ConstVal.COLOR_BLACK);
//                    tv_singer.setTextColor(ConstVal.COLOR_BLACK);
//                }

                if (dataList != null && dataList.size() > 0) {
                    if (MusicData.musicList == null) {
                        MusicData.musicList = new ArrayList<>();
                    }
                    MusicData.musicList.clear();
                    for (SongDetails sd : dataList) {
                        MusicData.musicList.add(MusicUtil.convertMusicType(context, sd));
                    }
                    MusicData.music_play_idx = position;
                    MusicUtil.playStart(context);
                }
                vsIdx = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_song_icon;
        private TextView item_song_songName;
        private TextView item_song_nickname;
        private LinearLayout item_song_llDown;
        private RelativeLayout item_song_rl;
        private ImageView item_song_top;
        private ImageView item_song_heart;
        private TextView item_song_comment;
        private TextView item_song_comment2;
        private TextView item_song_comment3;
        private ImageView item_song_tab;
        private ImageView item_song_tab2;
        private ImageView item_song_tab3;
        private LinearLayout item_song_llOne;
        private LinearLayout item_song_llTwo;
        private LinearLayout item_song_llThree;

        public ViewHolder(View itemView) {
            super(itemView);

            item_song_icon = (ImageView) itemView.findViewById(R.id.item_song_icon);
            item_song_songName = (TextView) itemView.findViewById(R.id.item_song_songName);
            item_song_nickname = (TextView) itemView.findViewById(R.id.item_song_nickname);
            item_song_llDown = (LinearLayout) itemView.findViewById(R.id.item_song_llDown);
            item_song_rl = (RelativeLayout) itemView.findViewById(R.id.item_song_rl);
            item_song_top = (ImageView) itemView.findViewById(R.id.item_song_top);
            item_song_heart = (ImageView) itemView.findViewById(R.id.item_song_heart);
            item_song_comment = (TextView) itemView.findViewById(R.id.item_song_comment);
            item_song_comment2 = (TextView) itemView.findViewById(R.id.item_song_comment2);
            item_song_comment3 = (TextView) itemView.findViewById(R.id.item_song_comment3);
            item_song_tab = (ImageView) itemView.findViewById(R.id.item_song_tab);
            item_song_tab2 = (ImageView) itemView.findViewById(R.id.item_song_tab2);
            item_song_tab3 = (ImageView) itemView.findViewById(R.id.item_song_tab3);
            item_song_llOne = (LinearLayout) itemView.findViewById(R.id.item_song_llOne);
            item_song_llTwo = (LinearLayout) itemView.findViewById(R.id.item_song_llTwo);
            item_song_llThree = (LinearLayout) itemView.findViewById(R.id.item_song_llThree);
        }
    }

}
