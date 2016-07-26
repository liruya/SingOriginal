package com.singoriginal.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.singoriginal.onclickinterface.ChannelOnItemClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by lanouhn on 16/7/20.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private Context context;
    private List<Channel> dataList;
    //item点击事件
    private ChannelOnItemClickListener coicl;

    public void setMyOnItemClickListener(ChannelOnItemClickListener coicl) {

        this.coicl = coicl;
    }

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Channel channel = dataList.get(position);
        holder.item_channel_text.setText(channel.getNA());

        Picasso.with(context).load(channel.getIM())
                .placeholder(R.mipmap.loading_picture216x150)
                .error(R.mipmap.loading_picture216x150)
                .into(holder.item_channel_icon);

        //如果接口对象不为空，则对Item设置监听
        if (coicl != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当item被点击时执行
                    //getLayoutPosition方法是获取当前为第几个item
                    int position = holder.getLayoutPosition();
                    //调用接口对象来传递参数
                    coicl.myOnItemClickListener(holder.itemView, position);
                }
            });
        }
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
