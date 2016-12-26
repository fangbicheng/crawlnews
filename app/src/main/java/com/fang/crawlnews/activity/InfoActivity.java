package com.fang.crawlnews.activity;

import android.os.Bundle;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.widget.HeaderLayout;

/**
 * Created by Administrator on 2016/8/7.
 */
public class InfoActivity extends BaseActivity {
    private HeaderLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.info_headerlayout);

        headerLayout.setTitle("消息中心");
    }
}
