package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.model.DynamicSquare;
import com.singoriginal.model.DynamicSquareContent;
import com.singoriginal.model.DynamicSquareUser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lanouhn on 16/7/21.
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {

    private Context context;
    private List<DynamicSquare> dataList;
    private Boolean isPress;

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
        DynamicSquareContent content = gson.fromJson(dynamic.getContent(), DynamicSquareContent.class);

        holder.item_dynamic_nickname.setText(dynamic.getUser().getNN());
        holder.item_dynamic_time.setText(dynamic.getCreatetime() + "");

//        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//        String time = sdf.format(new Date(dynamic.getUser().getCT()));
//        holder.item_dynamic_time.setText(time);

//        holder.item_dynamic_textIcon.setText(content.getMemo());
        holder.item_dynamic_content.setText(content.getContent());
        holder.item_dynamic_songName.setText(content.getSongName());
        holder.item_dynamic_laudNum.setText(content.getComments() + "");
        holder.item_dynamic_commentNum.setText(content.getLikes() + "");

        Picasso.with(context).load(dynamic.getUser().getI())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(holder.item_dynamic_icon);

        if (content.getContent().length() > 75) {

            holder.item_dynamic_more.setVisibility(View.VISIBLE);
        }

        holder.item_dynamic_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.item_dynamic_content.setMaxLines(500);
                holder.item_dynamic_more.setVisibility(View.GONE);
                holder.item_dynamic_shrink.setVisibility(View.VISIBLE);
            }
        });

        holder.item_dynamic_shrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.item_dynamic_content.setMaxLines(3);
                holder.item_dynamic_shrink.setVisibility(View.GONE);
                holder.item_dynamic_more.setVisibility(View.VISIBLE);
            }
        });
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

            item_dynamic_more = (TextView) itemView.findViewById(R.id.item_dynamic_more);
            item_dynamic_shrink = (TextView) itemView.findViewById(R.id.item_dynamic_shrink);
        }
    }
}
