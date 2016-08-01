package com.singoriginal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.activity.SongListActivity;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.TopRank;
import com.singoriginal.util.RtfUtil;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        final TopRank top = tops.get(position);
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
            style = RtfUtil.getRtf(style, (i + 1) + "  ", ConstVal.COLOR_GRAY, 36);
            style = RtfUtil.getRtf(style, sing, ConstVal.COLOR_SHALLOWBLACK, 42);
            style = RtfUtil.getRtf(style, author, ConstVal.COLOR_GRAY, 36);
            holder.tvs.get(i).setText(style, TextView.BufferType.SPANNABLE);
            int h = getViewHeight(holder.itemView);
            ViewGroup.LayoutParams lp = holder.iv_pic.getLayoutParams();
            lp.width = h;
            holder.iv_pic.setLayoutParams(lp);

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String id = top.getId();
                    String link = "";
                    String title = "";
                    int code = 0;
                    Intent intent = new Intent(context, SongListActivity.class);
                    switch (id)
                    {
                        case "fc":
                            link = ConstVal.RANKDETAIL_LINK
                                   + "&id=" + id + "&pagesize=20&pageindex=1";
                            title = "翻唱排行榜";
                            code = ConstVal.RANKFC_CODE;
                            break;

                        case "yc":
                            link = ConstVal.RANKDETAIL_LINK
                                   + "&id=" + id + "&pagesize=20&pageindex=1";
                            title = "原创排行榜";
                            code = ConstVal.RANKYC_CODE;
                            break;

                        case "list23":
                            link = ConstVal.RANKDETAIL_LINK
                                   + "&id=" + id + "&pagesize=20&pageindex=1";
                            title = "新歌top50";
                            code = ConstVal.RANKTP_CODE;
                            break;

                        case "list25":
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                            String dt = sdf.format(new Date(System.currentTimeMillis()));
                            link = ConstVal.POPULAR_LINK
                                   + "&time=" + dt + "&limit=20&maxid=0";
                            title = "本周实况";
                            code = ConstVal.RANKPOP_CODE;
                            intent.putExtra("imgLink", top.getPhotoBig());
                            break;
                    }

                    intent.putExtra("LinkUrl", link);
                    intent.putExtra("title", title);
                    intent.putExtra("code", code);
                    context.startActivity(intent);
                }
            });
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

    /**
     * 测量控件高度
     * @param view
     * @return
     */
    private int getViewHeight(View view)
    {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }
}
