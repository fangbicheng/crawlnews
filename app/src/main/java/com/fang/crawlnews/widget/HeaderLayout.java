package com.fang.crawlnews.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fang.crawlnews.R;

/**
 * Created by Administrator on 2016/8/6.
 */
public class HeaderLayout extends RelativeLayout {
    private ImageButton btnBack;
    private TextView tvTitle;

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.common_header, this);
        btnBack = (ImageButton) findViewById(R.id.common_header_ibtn_back);
        tvTitle = (TextView) findViewById(R.id.common_header_tv_title);

        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }
}
