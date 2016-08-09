package com.singoriginal.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.MusicData;
import com.singoriginal.model.Musician;
import com.singoriginal.util.MusicUtil;
import com.singoriginal.util.RtfUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * musician数据适配器
 * Created by lanouhn on 16/7/28.
 */
public class MusicianListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Musician> list;
    private int page;
    private int pg = -1;
    private int idx = -1;
    private static ImageButton selectItem;
    private static ImageButton nextItem;
    private MuscianReceiver receiver;

    public MuscianReceiver getReceiver()
    {
        return receiver;
    }

    public static ImageButton getNextItem()
    {
        return nextItem;
    }

    public static void setNextItem(ImageButton nextItem)
    {
        MusicianListAdapter.nextItem = nextItem;
    }

    public static ImageButton getSelectItem()
    {
        return selectItem;
    }

    public static void setSelectItem(ImageButton selectItem)
    {
        MusicianListAdapter.selectItem = selectItem;
    }

    public MusicianListAdapter(Context context, ArrayList<Musician> list, int page)
    {
        this.context = context;
        this.list = list;
        this.page = page;
        receiver = new MuscianReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(context.getPackageName() + ".MUSICIAN_RECEIVER");
        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_musician, null);
            holder.civ_icon = (CircleImageView) convertView.findViewById(R.id.itmsc_civ_icon);
            holder.tv_author = (TextView) convertView.findViewById(R.id.itmsc_tv_name);
            holder.tv_team = (TextView) convertView.findViewById(R.id.itmsc_tv_team);
            holder.tv_song = (TextView) convertView.findViewById(R.id.itmsc_tv_song);
            holder.tb_att = (ToggleButton) convertView.findViewById(R.id.itmsc_tb_att);
            holder.tb_play = (ImageButton) convertView.findViewById(R.id.itmsc_tb_play);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
//        final Handler hdl = new Handler()
//        {
//            @Override
//            public void handleMessage(Message msg)
//            {
//                super.handleMessage(msg);
//                switch (msg.what)
//                {
//                    case 0:
//                        holder.tb_play.getAnimation().cancel();
//                        holder.tb_play.setImageResource(R.mipmap.musician_pause);
//                        break;
//                }
//            }
//        };
        final Musician msc = list.get(position);
        SpannableStringBuilder builder = RtfUtil.getRtf(null, msc.getM(), ConstVal.COLOR_SHALLOWBLACK, 35);
        builder = RtfUtil.getRtf(builder, "  人气: " + ((msc.getYCRQ()+msc.getFCRQ())/10000) + "万", ConstVal.COLOR_GRAY, 30);
        Picasso.with(context)
               .load(msc.getI())
               .placeholder(R.mipmap.default_image)
               .resize(256, 256)
               .centerCrop()
               .into(holder.civ_icon);
        holder.tv_author.setText(msc.getNN());
        holder.tv_team.setText(builder, TextView.BufferType.SPANNABLE);
        holder.tv_song.setText("最新作品:" + msc.getSong().getSN());
        holder.tb_play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //没有播放的歌曲
                if (idx != position || pg != page)
                {
                    if (msc.getSong().getSK() == null || msc.getSong().getID() == 0)
                    {
                        Toast.makeText(context, "歌曲不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (MusicData.musicList == null || MusicData.musicList.size() == 0)
                    {
                        MusicData.musicList = new ArrayList<>();
                    }
                    MusicData.musicList.clear();
                    MusicData.musicList.add(MusicUtil.convertMusicType(context, msc));
                    MusicData.music_play_idx = 0;
                    nextItem = holder.tb_play;
                    MusicUtil.playStart(context);
                    idx = position;
                    pg = page;
//                    if (selectItem != null)
//                    {
//                        selectItem.getAnimation().cancel();
//                        selectItem.setImageResource(R.mipmap.musician_play);
//                    }
//                    holder.tb_play.setImageResource(R.mipmap.loading_grade);
//                    Animation rotate = AnimationUtils.loadAnimation(context, R.anim.musician_progressbar);
//                    holder.tb_play.startAnimation(rotate);
//                    MusicUtil.playStart(context);
//                    new Thread(new Runnable()
//                    {
//                        @Override
//                        public void run()
//                        {
//                            while (MusicData.music_play_state != ConstVal.PLAY_STATE_PLAYING)
//                            {
//                                try
//                                {
//                                    Thread.sleep(200);
//                                } catch (InterruptedException e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//                            hdl.sendEmptyMessage(0);
//                        }
//                    }).start();
                }
                else
                {
//                    if (MusicData.music_play_state == ConstVal.PLAY_STATE_PLAYING)
//                    {
//                        holder.tb_play.setImageResource(R.mipmap.musician_play);
//                    }
//                    else if (MusicData.music_play_state == ConstVal.PLAY_STATE_PAUSE)
//                    {
//                        holder.tb_play.setImageResource(R.mipmap.musician_pause);
//                    }
//                    else
//                    {
//                        return;
//                    }
                    if (MusicData.music_play_state != ConstVal.PLAY_STATE_PREPARE)
                    {

                        nextItem = holder.tb_play;
                        MusicUtil.playToggle(context);
                        idx = position;
                        pg = page;
                    }
                }

//                idx = position;
//                selectItem = holder.tb_play;
            }
        });
        return convertView;
    }

    class ViewHolder
    {
        private CircleImageView civ_icon;
        private TextView tv_author;
        private TextView tv_team;
        private TextView tv_song;
        private ToggleButton tb_att;
        private ImageButton tb_play;
    }

    class MuscianReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (nextItem == null)
            {
                return;
            }
            if (MusicData.music_play_state == ConstVal.PLAY_STATE_PREPARE)
            {
                nextItem.setImageResource(R.mipmap.loading_grade);
                Animation rotate = AnimationUtils.loadAnimation(context, R.anim.musician_progressbar);
                nextItem.startAnimation(rotate);
                selectItem = nextItem;
                return;
            }
            if (selectItem != null)
            {
                Animation animation = selectItem.getAnimation();
                if (animation != null)
                {
                    animation.cancel();
                }
                if (MusicData.music_play_state == ConstVal.PLAY_STATE_PAUSE)
                {
                    selectItem.setImageResource(R.mipmap.musician_play);
                }
                else if (MusicData.music_play_state == ConstVal.PLAY_STATE_PLAYING)
                {
                    selectItem.setImageResource(R.mipmap.musician_pause);
                }
            }
        }
    }
}
