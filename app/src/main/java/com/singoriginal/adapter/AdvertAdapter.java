package com.singoriginal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.singoriginal.activity.SongListActivity;
import com.singoriginal.activity.WebActivity;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Advert;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * 音乐.推荐.轮播图数据适配器
 * Created by lanouhn on 16/7/20.
 */
public class AdvertAdapter extends PagerAdapter
{
    private Context context;
    private ArrayList<Advert> advertList;

    public AdvertAdapter(Context context, ArrayList<Advert> advertList)
    {
        this.context = context;
        this.advertList = advertList;
    }

    @Override
    public int getCount()
    {
        return advertList == null ? 0 : (advertList.size() * 2000);
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        final int pos = (position) % advertList.size();
        final Advert advert = advertList.get(pos);
        ImageView imgView = new ImageView(context);
        Picasso.with(context).load(advert.getImgUrl())
               .resize(ConstVal.SCREEN_WIDTH, (int) (ConstVal.SCREEN_WIDTH * 0.4))
               .centerCrop().into(imgView);
        container.addView(imgView);
        imgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent;

                if (advert.getBehaviorType().equals("4"))
                {
                    intent = new Intent(context, WebActivity.class);
                    intent.putExtra("LinkUrl", advert.getLinkUrl());
                    context.startActivity(intent);
                }
                else if (advert.getBehaviorType().equals("2"))
                {
                    intent = new Intent(context, SongListActivity.class);
                    intent.putExtra("LinkUrl", advert.getLinkUrl());
                    intent.putExtra("title", advert.getTitle());
                    context.startActivity(intent);
                }
            }
        });
        return imgView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

}
