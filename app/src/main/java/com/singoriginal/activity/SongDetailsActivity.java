package com.singoriginal.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.ChannelAdapter;
import com.singoriginal.adapter.SongDetailsAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.model.Channel;
import com.singoriginal.model.SongDetails;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class SongDetailsActivity extends AppCompatActivity {

    private ImageButton imageBack;
    private RecyclerView song_details_recyclerView;
    private List<SongDetails> dataList;
    private SongDetailsAdapter adapter;
    private ImageView song_details_icon;
    private LinearLayout song_details_ll;
    private LinearLayout song_details_attentionLL;
    private RadioButton tv_second;
    private Space song_details_space;
    private TextView song_details_few;
    private TextView song_details_attention;
    private String detailUrl;
    private String IM;
    private String NA;
    private String NU;
    private String LI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        //获取到频道界面传递的数据
        Intent intent = getIntent();
        detailUrl = intent.getStringExtra("detailsUrl");
        IM = intent.getStringExtra("IM");
        NA = intent.getStringExtra("NA");
        NU = intent.getStringExtra("NU");
        LI = intent.getStringExtra("LI");

        initView();

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        song_details_recyclerView.setLayoutManager(llm);

        initData();
        initEvent();
    }

    private void initData() {

        dataList = new ArrayList<>();

        Gson gson = new Gson();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        dataList = new Gson().fromJson(GsonUtil.getJsonArray(json), new TypeToken<ArrayList<SongDetails>>() {
                        }.getType());

                        adapter = new SongDetailsAdapter(SongDetailsActivity.this, dataList);
                        song_details_recyclerView.setAdapter(adapter);

                        break;
                }
            }
        };

        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(detailUrl).build();
        OkHttpUtil.enqueue(this, handler, ConstVal.ADVERT_CODE, request);

        int iconHight = getViewHeight(song_details_icon);
        int llHight = getViewHeight(song_details_ll);
        int spHight = iconHight - llHight;
        //创建一个layoutparams对象
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) song_details_space.getLayoutParams();
        linearParams.height = spHight;
        song_details_space.setLayoutParams(linearParams);

        //获取标题和标题下的图片
        tv_second.setText(NA);
        Picasso.with(this).load(IM)
                .placeholder(R.mipmap.loading_picture216x150)
                .error(R.mipmap.loading_picture216x150)
                .into(song_details_icon);
        //获取歌曲数量和关注人数
        song_details_few.setText(NU);
        song_details_attention.setText(LI);
    }

    private void initEvent() {

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        song_details_attentionLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SongDetailsActivity.this, "你好", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        song_details_attentionLL = (LinearLayout) findViewById(R.id.song_details_attentionLL);
        song_details_icon = (ImageView) findViewById(R.id.song_details_icon);
        song_details_ll = (LinearLayout) findViewById(R.id.song_details_ll);
        song_details_space = (Space) findViewById(R.id.song_details_space);
        song_details_few = (TextView) findViewById(R.id.song_details_few);
        song_details_attention = (TextView) findViewById(R.id.song_details_attention);
        song_details_recyclerView = (RecyclerView) findViewById(R.id.song_details_recyclerView);
        //页面公用标题头初始化
        View incView = findViewById(R.id.song_details_header);

        imageBack = (ImageButton) incView.findViewById(R.id.hdr_ib_srch);
        imageBack.setImageResource(R.mipmap.client_back);

        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.GONE);

        tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setSingleLine(true);
        tv_second.setText(getString(R.string.mysonglist));

        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.GONE);
    }

    /**
     * 测量控件高度
     *
     * @param view
     * @return
     */
    private int getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }

}
