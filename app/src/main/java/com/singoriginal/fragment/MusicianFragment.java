package com.singoriginal.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.singoriginal.R;
import com.singoriginal.activity.HeadIconActivity;
import com.singoriginal.adapter.MusicianListAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Musician;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicianFragment extends Fragment
{
    private ListView musician_lv_show;
    private String link;
    private int page;
    private ArrayList<Musician> list;
    private MusicianListAdapter adapter;

    public static MusicianFragment NewInstance(String link, int page)
    {
        MusicianFragment frag = new MusicianFragment();
        Bundle bundle = new Bundle();
        bundle.putString("link", link);
        bundle.putInt("page", page);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        link = bundle.getString("link");
        page = bundle.getInt("page");
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

    @Override
    public void onDestroy()
    {
        if (adapter != null)
        {
            getContext().unregisterReceiver(adapter.getReceiver());
            MusicianListAdapter.setNextItem(null);
            MusicianListAdapter.setSelectItem(null);
        }
        super.onDestroy();
    }

    private void initView(View view)
    {
        musician_lv_show = (ListView) view.findViewById(R.id.musician_lv_show);
        musician_lv_show.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Musician msc = list.get((int) id);
                Intent intent = new Intent(getContext(), HeadIconActivity.class);
                intent.putExtra("SIM", msc.getI());
                intent.putExtra("SU", msc.getNN());
                intent.putExtra("SUID", msc.getID() + "");
                startActivity(intent);
            }
        });
    }

    private void initData()
    {
        Handler hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what)
                {
                    case ConstVal.MUSICIAN_CODE:
                        String js = GsonUtil.getJsonArray(json);
                        list = new ArrayList<>();
                        try
                        {
                            JSONArray ary = new JSONArray(js);
                            for (int i = 0; i < ary.length(); i++)
                            {
                                Musician msc = new Gson().fromJson(ary.get(i).toString().replace("[]", "{}"), Musician.class);
                                list.add(msc);
                            }
                            adapter = new MusicianListAdapter(getContext(), list, page);
                            musician_lv_show.setAdapter(adapter);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        Request request = new Request.Builder().url(link).build();
        OkHttpUtil.enqueue(getContext(), hdl, ConstVal.MUSICIAN_CODE, request);
    }

}
