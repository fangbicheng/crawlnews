package com.fang.crawlnews.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.CommonDialog;
import com.fang.crawlnews.widget.HeaderLayout;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/8/11.
 */
public class PersonalActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private RelativeLayout rlLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.personal_headerlayout);
        rlLogout = (RelativeLayout) findViewById(R.id.personal_layout_logout);

        headerLayout.setTitle("账号管理");
        rlLogout.setOnClickListener(new mOnClickListener());
    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.personal_layout_logout:
                    new CommonDialog(PersonalActivity.this).builder()
                        .setTitle("账号管理").setContentMsg("您确定退出登录吗？")
                        .setNegativeBtn("取消", new CommonDialog.OnNegativeListener() {
                            @Override
                            public void onNegative(View view) {

                            }
                        })
                        .setPositiveBtn("确定", new CommonDialog.OnPositiveListener() {
                            @Override
                            public void onPositive(View view) {
                                BmobUser.logOut();
                                ToastUtil.showShort("退出成功");
                                finish();
                            }
                        })
                        .show();
                    break;
                default:
                    break;
            }
        }
    }
}
