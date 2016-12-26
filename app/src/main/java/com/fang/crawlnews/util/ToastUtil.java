package com.fang.crawlnews.util;

import android.content.Context;
import android.widget.Toast;

import com.fang.crawlnews.MyApplication;


public final class ToastUtil {

    private ToastUtil() {

    }

    public static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    public static void show(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void showShort(int resId) {
        showShort(MyApplication.getContext().getString(resId));
    }

    public static void showShort(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        showLong(MyApplication.getContext().getString(resId));
    }

    public static void showLong(String msg) {
        Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}