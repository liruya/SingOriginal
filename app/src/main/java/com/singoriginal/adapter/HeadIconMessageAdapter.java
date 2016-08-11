package com.singoriginal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.activity.HeadIconActivity;
import com.singoriginal.model.HeadIconMessage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 16/8/4.
 */
public class HeadIconMessageAdapter extends BaseAdapter {

    private Context context;
    private List<HeadIconMessage.Data> messageList;

    public HeadIconMessageAdapter(Context context, List<HeadIconMessage.Data> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_headicon_message_list, null);
            holder = new ViewHolder();
            holder.item_headMessage_icon = (ImageView) convertView.findViewById(R.id.item_headMessage_icon);
            holder.item_headMessage_nickname = (TextView) convertView.findViewById(R.id.item_headMessage_nickname);
            holder.item_headMessage_time = (TextView) convertView.findViewById(R.id.item_headMessage_time);
            holder.item_headMessage_content = (TextView) convertView.findViewById(R.id.item_headMessage_content);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final HeadIconMessage.Data.Comments comments = messageList.get(position).getComments()[0];

        holder.item_headMessage_nickname.setText(comments.getUser().getNN());
        holder.item_headMessage_time.setText(comments.getCreateTime());
        holder.item_headMessage_content.setText(comments.getContent());

        Picasso.with(context).load(comments.getUser().getI())
                .placeholder(R.mipmap.loading_picture216x150)
                .error(R.mipmap.loading_picture216x150).into(holder.item_headMessage_icon);
        holder.item_headMessage_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, HeadIconActivity.class);
                intent.putExtra("SIM", comments.getUser().getI());
                intent.putExtra("SU", comments.getUser().getNN());
                intent.putExtra("SUID", comments.getUser().getID() + "");
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {

        private ImageView item_headMessage_icon;
        private TextView item_headMessage_nickname;
        private TextView item_headMessage_time;
        private TextView item_headMessage_content;
    }

}
