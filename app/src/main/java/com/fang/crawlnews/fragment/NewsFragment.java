package com.fang.crawlnews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.adapter.NewsAdapter;
import com.fang.crawlnews.adapter.NewsFragmentPagerAdapter;
import com.fang.crawlnews.base.BaseFragment;
import com.fang.crawlnews.bean.News;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class NewsFragment extends Fragment {
    private ViewPager viewPager;
    private TabPageIndicator indicator;

    private FragmentPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initView(view);

        return view;
    }


    /**
     * 初始化新闻导航界面
     **/
    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.news_pager);
        indicator = (TabPageIndicator) view.findViewById(R.id.news_indicator);

        adapter = new NewsFragmentPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
    }

}