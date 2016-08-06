package com.singoriginal.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.MusicData;
import com.singoriginal.util.MusicUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment
{
    private SimpleAdapter adapter;
    private TextView playlist_title;
    private ImageButton playlist_select;
    private ListView playlist_lv_show;

    private ShowSelectItemReceiver receiver = new ShowSelectItemReceiver();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_playlist, null);

        playlist_title = (TextView) view.findViewById(R.id.playlist_title);
        playlist_select = (ImageButton) view.findViewById(R.id.playlist_select);
        playlist_lv_show = (ListView) view.findViewById(R.id.playlist_lv_show);

        playlist_lv_show.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount)
            {
                showSelectItem(MusicData.music_play_idx);
            }
        });

        playlist_lv_show.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                MusicData.music_play_idx = position;
                MusicUtil.playStart(getContext());
                MusicUtil.playGetDetail(getContext());
            }
        });

        initData();
        showSelectItem(MusicData.music_play_idx);

        IntentFilter filter = new IntentFilter();
        filter.addAction(getContext().getPackageName() + ".SHOW_SELECT_RECEIVER");
        getContext().registerReceiver(receiver, filter);

        return view;
    }

    @Override
    public void onDestroy()
    {
        getContext().unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void showSelectItem(int position)
    {
        int firstIdx = playlist_lv_show.getFirstVisiblePosition();
        int lastIdx = playlist_lv_show.getLastVisiblePosition();
        TextView tv_idx;
        TextView tv_song;
        TextView tv_singer;
        for (int i = 0; i < playlist_lv_show.getChildCount(); i++)
        {
            int color;
            View view = playlist_lv_show.getChildAt(i);
            tv_idx = (TextView) view.findViewById(R.id.item_playlist_idx);
            tv_song = (TextView) view.findViewById(R.id.item_playlist_song);
            tv_singer = (TextView) view.findViewById(R.id.item_playlist_singer);
            if (i == position - firstIdx)
            {
                color = ConstVal.COLOR_DARKGREEN;
                tv_singer.setTextColor(color);
            }
            else
            {
                color = ConstVal.COLOR_WHITE;
                tv_singer.setTextColor(ConstVal.COLOR_GRAY);
            }
            tv_idx.setTextColor(color);
            tv_song.setTextColor(color);

        }
    }

    private void initData()
    {
        playlist_title.setText("共" + MusicData.musicList.size() + "首");

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < MusicData.musicList.size(); i++)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("index", i + 1);
            map.put("songname", MusicData.musicList.get(i).getSongname());
            map.put("author", " - " + MusicData.musicList.get(i).getUsername());
            list.add(map);
        }
        adapter = new SimpleAdapter(getContext(),
                                    list,
                                    R.layout.item_playlist,
                                    new String[]{"index", "songname", "author"},
                                    new int[]{R.id.item_playlist_idx, R.id.item_playlist_song, R.id.item_playlist_singer});
        playlist_lv_show.setAdapter(adapter);
    }

    class ShowSelectItemReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            int position = MusicData.music_play_idx;
            showSelectItem(position);
        }
    }
}
