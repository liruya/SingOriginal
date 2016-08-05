package com.singoriginal.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.singoriginal.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView search_iv_back;
    private ImageView search_iv_titleDelete;
    private TextView search_tv_search;
    private EditText search_et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initEvent();
    }

    private void initEvent() {

        search_iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (search_et_title.getText().toString().length() > 0) {
                    search_iv_titleDelete.setVisibility(View.VISIBLE);
                    search_tv_search.setBackgroundResource(R.drawable.shape_fortune);
                    search_tv_search.setEnabled(true);
                } else {
                    search_iv_titleDelete.setVisibility(View.INVISIBLE);
                    search_tv_search.setBackgroundResource(R.drawable.shape_search);
                    search_tv_search.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search_iv_titleDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "shanchu", Toast.LENGTH_SHORT).show();
                search_et_title.setText(null);
            }
        });
        search_tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initView() {

        search_iv_back = (ImageView) findViewById(R.id.search_iv_back);
        search_iv_titleDelete = (ImageView) findViewById(R.id.search_iv_titleDelete);
        search_tv_search = (TextView) findViewById(R.id.search_tv_search);
        search_et_title = (EditText) findViewById(R.id.search_et_title);
    }
}
