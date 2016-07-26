package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.model.Channel;
import com.singoriginal.model.SongDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 16/7/26.
 */
public class SongDetailsAdapter extends RecyclerView.Adapter<SongDetailsAdapter.ViewHolder> {

    private Context context;
    private List<SongDetails> dataList;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

        SongDetails sDetails = dataList.get(position);
        holder.item_song_songName.setText(sDetails.getSN());
        holder.item_song_nickname.setText(sDetails.getSU());
        holder.item_song_comment.setText(sDetails.getSRC());

        Picasso.with(context).load(sDetails.getSIM())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(holder.item_song_icon);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_song_icon;
        private TextView item_song_songName;
        private TextView item_song_nickname;
        private TextView item_song_comment;

        public ViewHolder(View itemView) {
            super(itemView);

            item_song_icon = (ImageView) itemView.findViewById(R.id.item_song_icon);
            item_song_songName = (TextView) itemView.findViewById(R.id.item_song_songName);
            item_song_nickname = (TextView) itemView.findViewById(R.id.item_song_nickname);
            item_song_comment = (TextView) itemView.findViewById(R.id.item_song_comment);
        }
    }

}
