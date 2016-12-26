package com.fang.crawlnews;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;


/**
 * Created by Administrator on 2016/8/1.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initApp();
    }

    public static Context getContext() {
        return context;
    }

    private void initApp() {
        //初始化Bmob功能
        Bmob.initialize(this, "1f86d9db748abc4117c0f429aa833598");


        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
