package com.fang.crawlnews.activity;

import android.os.Bundle;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.widget.HeaderLayout;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AboutActivity extends BaseActivity {
    private HeaderLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.setting_headerlayout);
        headerLayout.setTitle("关于");
    }
}
