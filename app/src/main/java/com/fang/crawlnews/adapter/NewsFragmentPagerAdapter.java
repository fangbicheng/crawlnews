package com.fang.crawlnews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fang.crawlnews.fragment.ItemFragment;
import com.fang.crawlnews.fragment.NewsFragment;

/**
 * Created by Administrator on 2016/8/1.
 */
public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String[] TITLE = new String[] { "热点", "社会", "娱乐", "体育", "财经",
            "国际", "军事", "科技", "汽车", "搞笑", "时尚", "旅游" };
    private static final String[] TITLE_NAME = new String[] { "hot", "society", "entertainment", "sports",
            "finance", "world", "military", "tech", "car", "funny", "fashion", "travel" };

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        Fragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString("arg", TITLE_NAME[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }
}
