package com.fang.crawlnews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.HeaderLayout;

/**
 * Created by Administrator on 2016/8/8.
 */
public class FeedbackActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.feedback_headerlayout);
        btnSubmit = (Button) findViewById(R.id.feedback_btn_submit);

        headerLayout.setTitle("意见反馈");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort("感谢您宝贵的意见");
                finish();
            }
        });
    }
}
