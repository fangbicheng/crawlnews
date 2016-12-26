package com.fang.crawlnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.activity.CollectionActivity;
import com.fang.crawlnews.activity.InfoActivity;
import com.fang.crawlnews.activity.FeedbackActivity;
import com.fang.crawlnews.activity.LoginActivity;
import com.fang.crawlnews.activity.PersonalActivity;
import com.fang.crawlnews.activity.SettingActivity;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.CommonDialog;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MeFragment extends Fragment {
    private ImageButton btnSetting;
    private ImageView ivUserHead;
    private TextView tvUsername;
    private RelativeLayout rlCollection, rlInfo, rlActivity, rlFeedback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        initView(view);

        return view;
    }


    private void initView(View view) {
        btnSetting = (ImageButton) view.findViewById(R.id.me_ibtn_setting);
        ivUserHead = (ImageView) view.findViewById(R.id.me_iv_user_head);
        tvUsername = (TextView) view.findViewById(R.id.me_tv_username);
        rlCollection = (RelativeLayout) view.findViewById(R.id.me_layout_collection);
        rlInfo = (RelativeLayout) view.findViewById(R.id.me_layout_info);
        rlActivity = (RelativeLayout) view.findViewById(R.id.me_layout_activity);
        rlFeedback = (RelativeLayout) view.findViewById(R.id.me_layout_feedback);

        btnSetting.setOnClickListener(new mOnClickListener());
        ivUserHead.setOnClickListener(new mOnClickListener());
        rlCollection.setOnClickListener(new mOnClickListener());
        rlInfo.setOnClickListener(new mOnClickListener());
        rlActivity.setOnClickListener(new mOnClickListener());
        rlFeedback.setOnClickListener(new mOnClickListener());
    }


    @Override
    public void onStart() {
        super.onStart();
        /** 判断当前是否有用户登录，设置显示文字内容 **/
        if (BmobUser.getCurrentUser() != null) {
            tvUsername.setText(BmobUser.getCurrentUser().getUsername());
        } else {
            tvUsername.setText(getResources().getString(R.string.me_click_login));
        }
    }


    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.me_iv_user_head:
                    startActivityAfterLogin(PersonalActivity.class);
                    break;
                case R.id.me_ibtn_setting:
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.me_layout_collection:
                    startActivityAfterLogin(CollectionActivity.class);
                    break;
                case R.id.me_layout_info:
                    startActivityAfterLogin(InfoActivity.class);
                    break;
                case R.id.me_layout_activity:
                    break;
                case R.id.me_layout_feedback:
                    startActivityAfterLogin(FeedbackActivity.class);
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
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), cls);
            startActivity(intent);
        }
    }
}
