package com.fang.crawlnews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.fragment.MeFragment;
import com.fang.crawlnews.util.DataCleanUtil;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.CommonDialog;
import com.fang.crawlnews.widget.HeaderLayout;
import com.fang.crawlnews.widget.SwitchButton;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SettingActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private RelativeLayout rlAccount, rlCache, rlUpdate, rlApp, rlAbout;
    private SwitchButton sbPush, sbCollect;
    private TextView tvCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.setting_headerlayout);
        rlAccount = (RelativeLayout) findViewById(R.id.setting_layout_account);
        rlCache = (RelativeLayout) findViewById(R.id.setting_layout_cache);
        rlUpdate = (RelativeLayout) findViewById(R.id.setting_layout_update);
        rlApp = (RelativeLayout) findViewById(R.id.setting_layout_app);
        rlAbout = (RelativeLayout) findViewById(R.id.setting_layout_about);
        sbPush = (SwitchButton) findViewById(R.id.setting_sb_push);
        sbCollect = (SwitchButton) findViewById(R.id.setting_sb_collect);
        tvCache = (TextView) findViewById(R.id.setting_tv_cache);

        headerLayout.setTitle("设置");
        rlAccount.setOnClickListener(new mOnClickListener());
        rlCache.setOnClickListener(new mOnClickListener());
        rlUpdate.setOnClickListener(new mOnClickListener());
        rlApp.setOnClickListener(new mOnClickListener());
        rlAbout.setOnClickListener(new mOnClickListener());
        sbPush.setOpened(true);

        tvCache.setText(DataCleanUtil.getCacheSize(new File("/data/data/" +
                SettingActivity.this.getPackageName() + "/cache")));
    }


    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.setting_layout_account:
                    startActivityAfterLogin(PersonalActivity.class);
                    break;
                case R.id.setting_layout_cache:
                    new CommonDialog(SettingActivity.this).builder()
                            .setTitle("清除缓存").setContentMsg("是否要清除所有缓存？")
                            .setNegativeBtn("取消", new CommonDialog.OnNegativeListener() {
                                @Override
                                public void onNegative(View view) {

                                }
                            })
                            .setPositiveBtn("确定", new CommonDialog.OnPositiveListener() {
                                @Override
                                public void onPositive(View view) {
                                    DataCleanUtil.deleteAllCache(SettingActivity.this);
                                    tvCache.setText("0.0B");
                                    ToastUtil.showShort("清除成功");
                                }
                            })
                            .show();
                    break;
                case R.id.setting_layout_update:
                    ToastUtil.showShort("已经是最新版本了");
                    break;
                case R.id.setting_layout_about:
                    intent = new Intent(SettingActivity.this, AboutActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 启动界面前，判断是否登录
     **/
    private void startActivityAfterLogin(Class<?> cls) {
        if (BmobUser.getCurrentUser() == null) {
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SettingActivity.this, cls);
            startActivity(intent);
        }
    }
}
