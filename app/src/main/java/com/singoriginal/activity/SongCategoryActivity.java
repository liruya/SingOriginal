package com.singoriginal.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.singoriginal.R;

import java.util.ArrayList;

public class SongCategoryActivity extends AppCompatActivity
{
    private static final String KEY_SONGTYPE = "歌曲分类";
    private SharedPreferences defaultSet;
    private View header;
    private View cate_inc_recmd;
    private TableLayout cate_tl;
    private String selectItem;
    private View selectView;
    private TextView selectTextView;
    private ImageView selectImageView;
    private Thread trd;
    private Handler hdl;

    private final String[] CATEGORY_STYLE = {"流行","爵士","小清新","轻音乐",
                                             "中国风","摇滚","DJ","古风",
                                             "武侠","钢琴曲","广场舞","民谣",
                                             "乡村"};
    private final String[] CATEGORY_EMOTION = {"感动", "寂寞", "安静", "温暖",
                                               "浪漫","治愈","伤感","想念",
                                               "激情","喜悦","失恋","怀念"};
    private final String[] CATEGORY_SCENE = {"夜晚","咖啡厅","夜店","旅行",
                                             "车载","阅读","一个人","KTV",};
    private final String[] CATEGORY_LANG = {"国语","英文","韩文","日文",
                                            "粤语","德语","西班牙语","闽南语",
                                            "法语"};
    private final String[] CATEGORY_OTHER = {"原创","翻唱","毕业","情歌",
                                             "经典","怀旧","爱情","励志",
                                             "儿歌","影视","男女对唱","歌词控",
                                             "动漫"};
    ArrayList<Category> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_category);

        initView();
        initData();
    }

    private void initView()
    {
        header = findViewById(R.id.cate_inc_hdr);
        cate_inc_recmd = findViewById(R.id.cate_inc_recmd);
        cate_tl = (TableLayout) findViewById(R.id.cate_tl);


        TextView hdr_tit = (TextView) header.findViewById(R.id.tit_tv_tit);
        hdr_tit.setText("选择歌曲分类");
        header.findViewById(R.id.tit_ib_msc).setVisibility(View.GONE);
        ImageButton hdr_back = (ImageButton) header.findViewById(R.id.tit_ib_back);
        hdr_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final TextView tv_recmd = (TextView) cate_inc_recmd.findViewById(R.id.item_cate_tv);
        final ImageView iv_sel = (ImageView) cate_inc_recmd.findViewById(R.id.item_cate_iv);
        cate_inc_recmd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectTextView.setBackgroundResource(R.drawable.text_border);
                selectImageView.setImageResource(0);
                tv_recmd.setBackgroundResource(R.drawable.text_border_dkgrn);
                iv_sel.setImageResource(R.mipmap.sing_songlist_type_selected);
                selectView = cate_inc_recmd;
                selectTextView = tv_recmd;
                selectImageView = iv_sel;
                SharedPreferences.Editor editor = defaultSet.edit();
                editor.putString(KEY_SONGTYPE, tv_recmd.getText().toString());
                editor.commit();
                hdl.sendEmptyMessageDelayed(0, 500);
            }
        });
        tv_recmd.setBackgroundResource(R.drawable.text_border_dkgrn);
        iv_sel.setImageResource(R.mipmap.sing_songlist_type_selected);
        selectView = cate_inc_recmd;
        selectTextView = tv_recmd;
        selectImageView = iv_sel;
    }

    private void initData()
    {
        defaultSet = PreferenceManager.getDefaultSharedPreferences(this);
        selectItem = defaultSet.getString(KEY_SONGTYPE, getResources().getString(R.string.recommend));
        list = new ArrayList<>();
        list.add(new Category("风格", CATEGORY_STYLE));
        list.add(new Category("情感", CATEGORY_EMOTION));
        list.add(new Category("场景", CATEGORY_SCENE));
        list.add(new Category("语种", CATEGORY_LANG));
        list.add(new Category("其他", CATEGORY_OTHER));

        for (Category cate : list)
        {
            TextView tv_title = new TextView(SongCategoryActivity.this);
            tv_title.setText(cate.getTitle());
            tv_title.setTextColor(Color.GRAY);
            tv_title.setPadding(10, 20, 10, 10);
            cate_tl.addView(tv_title);
            TableRow tr = new TableRow(SongCategoryActivity.this);
            tr.setWeightSum(4);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(0,
                                                                 ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                 1f);
            for (int i = 0; ; i++)
            {
                final View view = LayoutInflater.from(SongCategoryActivity.this).inflate(R.layout.item_cate, null);
                view.setLayoutParams(lp);
                final TextView tv = (TextView) view.findViewById(R.id.item_cate_tv);
                final ImageView iv = (ImageView) view.findViewById(R.id.item_cate_iv);
                if (i < cate.getItems().length)
                {
                    tv.setText(cate.getItems()[i]);
                    if (cate.getItems()[i].equals(selectItem))
                    {
                        selectTextView.setBackgroundResource(R.drawable.text_border);
                        selectImageView.setImageResource(0);
                        tv.setBackgroundResource(R.drawable.text_border_dkgrn);
                        iv.setImageResource(R.mipmap.sing_songlist_type_selected);
                        tv.setBackgroundResource(R.drawable.text_border_dkgrn);
                        iv.setImageResource(R.mipmap.sing_songlist_type_selected);
                        selectView = view;
                        selectTextView = tv;
                        selectImageView = iv;
                    }
                }
                else
                {
                    tv.setText(R.string.place_holder);
                    tv.setVisibility(View.INVISIBLE);
                }
                view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        selectTextView.setBackgroundResource(R.drawable.text_border);
                        selectImageView.setImageResource(0);
                        tv.setBackgroundResource(R.drawable.text_border_dkgrn);
                        iv.setImageResource(R.mipmap.sing_songlist_type_selected);
                        selectView = view;
                        selectTextView = tv;
                        selectImageView = iv;
                        SharedPreferences.Editor editor = defaultSet.edit();
                        editor.putString(KEY_SONGTYPE, tv.getText().toString());
                        editor.commit();
                        hdl.sendEmptyMessageDelayed(0, 500);
                    }
                });
                tr.addView(view);
                if ((i & 0x03) == 0x03)
                {
                    cate_tl.addView(tr);
                    if (i >= cate.getItems().length - 1)
                    {
                        break;
                    }
                    tr = new TableRow(SongCategoryActivity.this);
                    tr.setWeightSum(4);
                }
            }
        }

        hdl = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 0:
                        finish();
                        break;
                }
            }
        };
    }

    public class Category
    {
        String title;
        String[] items;

        public Category(String title, String[] items)
        {
            this.items = items;
            this.title = title;
        }

        public String[] getItems()
        {
            return items;
        }

        public void setItems(String[] items)
        {
            this.items = items;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }
}
