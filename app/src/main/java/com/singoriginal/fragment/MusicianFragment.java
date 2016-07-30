package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.singoriginal.R;
import com.singoriginal.adapter.MusicianListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicianFragment extends Fragment
{
    private ListView musician_lv_show;
    private String link;
    private MusicianListAdapter adapter;

    public static MusicianFragment NewInstance(String link)
    {
        MusicianFragment frag = new MusicianFragment();
        Bundle bundle = new Bundle();
        bundle.putString("link", link);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        link = bundle.getString("link");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_musician, null);

        initView(view);
        initData();
        return view;
    }

    private void initView(View view)
    {
        musician_lv_show = (ListView) view.findViewById(R.id.musician_lv_show);
    }

    private void initData()
    {
        adapter = new MusicianListAdapter(getContext(), link);
        musician_lv_show.setAdapter(adapter);
    }

}
