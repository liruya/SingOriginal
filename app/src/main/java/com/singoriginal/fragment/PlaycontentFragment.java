package com.singoriginal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.singoriginal.R;
import com.singoriginal.constant.ConstVal;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaycontentFragment extends Fragment
{
    private String img;
    private String content;
    private int textcolor;

    public static PlaycontentFragment newInstance(String img, String content, int textcolor)
    {
        PlaycontentFragment frag = new PlaycontentFragment();
        String result = content;
        Bundle bundle = new Bundle();
        bundle.putString("imgurl", img);
        bundle.putString("content", result);
        bundle.putInt("textcolor", textcolor);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_playcontent, null);

        Bundle bundle = getArguments();

        img = bundle.getString("imgurl");
        content = bundle.getString("content");
        textcolor = bundle.getInt("textcolor");

        if (img != null && (!img.equals("")) )
        {
            ImageView playcontent_pic = (ImageView) view.findViewById(R.id.playcontent_pic);
            Picasso.with(getContext())
                   .load(img)
                   .resize(ConstVal.SCREEN_WIDTH, ConstVal.SCREEN_WIDTH)
                   .centerInside()
                   .into(playcontent_pic);
        }
        TextView playcontent_tv_text = (TextView) view.findViewById(R.id.playcontent_tv_text);
        playcontent_tv_text.setText(content);
        playcontent_tv_text.setTextColor(textcolor);

        return view;
    }

}
