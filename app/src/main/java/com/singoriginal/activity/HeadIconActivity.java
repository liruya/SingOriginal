package com.singoriginal.activity;

import android.annotation.TargetApi;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.singoriginal.R;
import com.singoriginal.adapter.HeadIconVpAdapter;
import com.singoriginal.adapter.InfoAdapter;
import com.singoriginal.adapter.MySongAdapter;
import com.singoriginal.adapter.SongDetailsAdapter;
import com.singoriginal.constant.ConstVal;
import com.singoriginal.fragment.CollectionSongFragment;
import com.singoriginal.fragment.HeadIconMessageFragment;
import com.singoriginal.fragment.HeadIconSongFragment;
import com.singoriginal.fragment.HeadIconWorkFragment;
import com.singoriginal.fragment.MusicianFragment;
import com.singoriginal.fragment.MySongFragment;
import com.singoriginal.model.HeadIconInfo;
import com.singoriginal.model.HeadIconWork;
import com.singoriginal.model.SongDetails;
import com.singoriginal.util.GsonUtil;
import com.singoriginal.util.OkHttpUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HeadIconActivity extends AppCompatActivity {

    private RadioButton tv_second;
    private ImageButton imageBack;
    private View incView;
    private View headIconView;
    private ArrayList<View> list;//上方的viewpager中的view集合
    private ViewPager headIcon_viewPager_up;//上方的viewpager
    private HeadIconVpAdapter vpAdapter;//上方viewPager的adapter
    private List<String> LoList;//tabLayout上的文字集合
    private List<Fragment> fragments;//下方viewpager中的view集合，加入fragment
    private ViewPager headIcon_viewPager_down;//下方的viewpager
    private ImageView headIcon_pointLeft;
    private ImageView headIcon_pointRight;
    private TabLayout headIcon_tabLayout;
    private ViewStub headIcon_viewStub;

    private ViewPager headIcon_viewPager;

    private ImageView info_iv_usr;
    private TextView info_tv_id;
    private TextView info_page1_attention;
    private TextView info_page1_fans;
    private TextView info_page2_city;
    private TextView info_page2_summary;
    private String SIM;
    private String SU;
    private String SUID;
    private String infoUrl;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_icon);

        Intent intent = getIntent();
        SIM = intent.getStringExtra("SIM");
        SU = intent.getStringExtra("SU");
        SUID = intent.getStringExtra("SUID");
        infoUrl = ConstVal.HEADICON_INFO_HTTP_PATH + SUID +
                ConstVal.HEADICON_INFO_HTTP_PARAM1 + ConstVal.HEADICON_INFO_HTTP_PARAM2 + "&version=" + ConstVal.VERSION2;

        Log.i("info", infoUrl);
        initView();
        initData();
        setDataToView();
        initEvent();
    }

    private void setDataToView() {

        vpAdapter = new HeadIconVpAdapter(getSupportFragmentManager(), this, LoList, fragments);
        headIcon_viewPager_down.setAdapter(vpAdapter);
        headIcon_tabLayout.setupWithViewPager(headIcon_viewPager_down);
    }

    private void initData() {

        fragments = new ArrayList<Fragment>();

        fragments.add(NewInstanceOne(SUID));
        fragments.add(NewInstanceTwo(SUID));
        fragments.add(new HeadIconMessageFragment());

        String[] text = {"作品", "歌单", "留言板"};

        LoList = new ArrayList<>();

        for (int i = 0; i < text.length; i++) {

            LoList.add(text[i]);
        }

//        int iconHight = getViewHeight(headIcon_viewPager_up);
//        int llHight = getViewHeight(incView);
//        int spHight = iconHight - llHight;
//        //创建一个layoutparams对象
//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) headIcon_space.getLayoutParams();
//        linearParams.height = spHight;
//        headIcon_space.setLayoutParams(linearParams);

        Picasso.with(this).load(SIM)
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .into(info_iv_usr);
        info_tv_id.setText(SU);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case ConstVal.ADVERT_CODE:
                        String json = (String) msg.obj;
                        HeadIconInfo infoList = new Gson().fromJson(GsonUtil.getJsonArray(json), HeadIconInfo.class);

                        info_page1_attention.setText(infoList.getTFD() + "");
                        info_page1_fans.setText(infoList.getTFS() + "");

                        Picasso.with(HeadIconActivity.this).load(infoList.getUBG())
                                .placeholder(R.mipmap.loading_picture216x150)
                                .error(R.mipmap.loading_picture216x150).into(new Target() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                headIcon_viewPager.setBackground(new BitmapDrawable(bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

                        if (infoList.getC() != null)
                            info_page2_city.setText("城市 : " + infoList.getC());
                        if (infoList.getM() != null)
                            info_page2_summary.setText("简介 : " + infoList.getM());

                        break;
                }
            }
        };

        //创建OkHttpClient请求
        final Request request = new Request.Builder().url(infoUrl).build();
        OkHttpUtil.enqueue(this, handler, ConstVal.ADVERT_CODE, request);
    }

    //加载绑定的fragment
    public static HeadIconWorkFragment NewInstanceOne(String SUID) {
        HeadIconWorkFragment frag = new HeadIconWorkFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SUID", SUID);
        frag.setArguments(bundle);
        return frag;
    }

    public static HeadIconMessageFragment NewInstanceTwo(String SUID) {
        HeadIconMessageFragment frag = new HeadIconMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SUID", SUID);
        frag.setArguments(bundle);
        return frag;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initEvent() {

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headIcon_viewPager_up.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        headIcon_pointLeft.setImageResource(R.mipmap.dot_f);
                        headIcon_pointRight.setImageResource(R.mipmap.dot_n);
                        break;
                    case 1:
                        headIcon_pointLeft.setImageResource(R.mipmap.dot_n);
                        headIcon_pointRight.setImageResource(R.mipmap.dot_f);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {

        headIconView = findViewById(R.id.headIcon_scroll);
        headIcon_viewPager_down = (ViewPager) headIconView.findViewById(R.id.headIcon_viewPager_down);
        headIcon_tabLayout = (TabLayout) headIconView.findViewById(R.id.headIcon_tabLayout);

        headIcon_viewPager_up = (ViewPager) findViewById(R.id.headIcon_viewPager_up);
        headIcon_pointLeft = (ImageView) findViewById(R.id.headIcon_pointLeft);
        headIcon_pointRight = (ImageView) findViewById(R.id.headIcon_pointRight);

        headIcon_viewPager = (ViewPager) findViewById(R.id.headIcon_viewPager);

//        headIcon_viewStub = (ViewStub) findViewById(R.id.headIcon_viewStub);

        //页面公用标题头初始化
        incView = findViewById(R.id.headIcon_title);
        imageBack = (ImageButton) findViewById(R.id.hdr_ib_srch);
        imageBack.setImageResource(R.mipmap.client_back);
        incView.findViewById(R.id.hdr_rb_first).setVisibility(View.GONE);
        tv_second = (RadioButton) incView.findViewById(R.id.hdr_rb_second);
        tv_second.setTextColor(ConstVal.colorWhite);
        tv_second.setSingleLine(true);
        tv_second.setText(getString(R.string.mysonglist));
        tv_second.setChecked(true);
        incView.findViewById(R.id.hdr_rb_third).setVisibility(View.GONE);
        //上方的viewPager
        list = new ArrayList<>();

        View view1 = LayoutInflater.from(this).inflate(R.layout.info_page1, null);

        info_iv_usr = (ImageView) view1.findViewById(R.id.info_iv_usr);
        info_tv_id = (TextView) view1.findViewById(R.id.info_tv_id);
        info_page1_attention = (TextView) view1.findViewById(R.id.info_page1_attention);
        info_page1_fans = (TextView) view1.findViewById(R.id.info_page1_fans);
        info_iv_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HeadIconActivity.this, "可以点击", Toast.LENGTH_SHORT).show();
            }
        });

        View view2 = LayoutInflater.from(this).inflate(R.layout.info_page2, null);
        info_page2_city = (TextView) view2.findViewById(R.id.info_page2_city);
        info_page2_summary = (TextView) view2.findViewById(R.id.info_page2_summary);
        list.add(view1);
        list.add(view2);
        InfoAdapter adapter = new InfoAdapter(list);
//        headIcon_viewPager_up.setAdapter(adapter);
        headIcon_viewPager.setAdapter(adapter);
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
