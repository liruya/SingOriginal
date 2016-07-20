package com.singoriginal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 16/7/20.
 */
public class ChannelAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Channel> dataList;

    public ChannelAdapter(Context context, List<Channel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    /**
     * 创建自定义的ViewHolder，绑定item布局文件
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //找到布局文件，并创建ViewHolder
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_channel_recycler, null));
    }

    /**
     * 将数据绑定给ViewHolder中的控件
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder holder1 = null;

        Channel channel = dataList.get(position);
        holder1.item_channel_text.setText(channel.getItem_channel_text());

        Picasso.with(context).load(ConstVal.CHANNEL_HTTP_PATH).fit()
                .placeholder(R.mipmap.loading_picture216x150)
                .error(R.mipmap.loading_picture216x150)
                .into(holder1.item_channel_icon);
    }

    /**
     * 获取数据集合长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * 创建一个ViewHolder类，继承于RecyclerView.ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //声明item布局当中的控件
        private ImageView item_channel_icon;
        private TextView item_channel_text;

        // 自定义ViewHolder的构造方法，用于找到item当中的控件
        public ViewHolder(View itemView) {
            super(itemView);

            item_channel_icon = (ImageView) itemView.findViewById(R.id.item_channel_icon);
            item_channel_text = (TextView) itemView.findViewById(R.id.item_channel_text);
        }
    }
}
