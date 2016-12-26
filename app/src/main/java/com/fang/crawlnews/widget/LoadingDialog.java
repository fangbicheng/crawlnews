package com.fang.crawlnews.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fang.crawlnews.R;

/**
 * Created by Administrator on 2016/8/3.
 */
public class LoadingDialog extends ProgressDialog {
    private ImageView imageView;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        setCancelable(true);  // 设置当返回键按下是否关闭对话框
        setCanceledOnTouchOutside(true);  // 设置当点击对话框以外区域是否关闭对话框

        imageView = (ImageView) findViewById(R.id.dialog_loading_iv_icon);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.dialog_bg_loading);
        imageView.startAnimation(animation);
    }
}
