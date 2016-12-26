package com.fang.crawlnews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fang.crawlnews.R;
import com.fang.crawlnews.adapter.NewsFragmentPagerAdapter;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.fragment.MeFragment;
import com.fang.crawlnews.fragment.NewsFragment;
import com.fang.crawlnews.fragment.RecommendFragment;
import com.viewpagerindicator.TabPageIndicator;

public class MainActivity extends FragmentActivity {
    private RadioGroup rgToolbar;
    private RadioButton rbNews;

    private NewsFragment newsFragment;
    private RecommendFragment recommendFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化界面
     **/
    private void initView() {
        rgToolbar = (RadioGroup) findViewById(R.id.main_rg_tabbar);
        rbNews = (RadioButton) findViewById(R.id.main_rb_news);

        rgToolbar.setOnCheckedChangeListener(new mOnCheckedChangeListener());
        rbNews.setChecked(true);
    }


    /**
     * RadioButton监听
     **/
    private class mOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            hideAllFragment(fragmentTransaction);
            switch (checkedId) {
                case R.id.main_rb_news:
                    if (newsFragment == null) {
                        newsFragment = new NewsFragment();
                        fragmentTransaction.add(R.id.main_layout_content, newsFragment);
                    } else {
                        fragmentTransaction.show(newsFragment);
                    }
                    break;
                case R.id.main_rb_recommend:
                    if (recommendFragment == null) {
                        recommendFragment = new RecommendFragment();
                        fragmentTransaction.add(R.id.main_layout_content, recommendFragment);
                    } else {
                        fragmentTransaction.show(recommendFragment);
                    }
                    break;
                case R.id.main_rb_me:
                    if (meFragment == null) {
                        meFragment = new MeFragment();
                        fragmentTransaction.add(R.id.main_layout_content, meFragment);
                    } else {
                        fragmentTransaction.show(meFragment);
                    }
                    break;
                default:
                    break;
            }
            fragmentTransaction.commit();
        }
    }

    /**
     * 隐藏所有的Fragment
     **/
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (newsFragment != null) fragmentTransaction.hide(newsFragment);
        if (recommendFragment != null) fragmentTransaction.hide(recommendFragment);
        if (meFragment != null) fragmentTransaction.hide(meFragment);
    }


    /**
     * 连续按两次返回键，退出APP
     **/
    private long exitTime;
    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }
}
