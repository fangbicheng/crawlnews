package com.fang.crawlnews.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.adapter.NewsAdapter;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.bean.News;
import com.fang.crawlnews.widget.HeaderLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/7.
 */
public class CollectionActivity extends BaseActivity {
    private HeaderLayout headerLayout;
    private ImageView ivTip;
    private TextView tvTip;
    private ListView listView;

    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();


        BmobQuery<News> query = new BmobQuery<>();

        //执行查询方法
        query.findObjects(new FindListener<News>() {
            @Override
            public void done(List<News> object, BmobException e) {
                if (e == null) {
                    newsList.clear();
                    for (final News news : object) {
                        BmobQuery<BmobUser> query = new BmobQuery<>();
                        query.addWhereRelatedTo("collection", new BmobPointer(news));
                        query.findObjects(new FindListener<BmobUser>() {
                            @Override
                            public void done(List<BmobUser> list, BmobException e) {
                                for (BmobUser user : list) {
                                    if (user.getUsername().equals(BmobUser.getCurrentUser().getUsername())) {
                                        newsList.add(news);
                                        break;
                                    }
                                }
                                adapter.notifyDataSetChanged();

                                if (adapter.getCount() == 0) {
                                    ivTip.setVisibility(View.VISIBLE);
                                    tvTip.setVisibility(View.VISIBLE);
                                } else {
                                    ivTip.setVisibility(View.GONE);
                                    tvTip.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }
        });
    }

    private void initView() {
        headerLayout = (HeaderLayout) findViewById(R.id.collection_headerlayout);
        ivTip = (ImageView) findViewById(R.id.collection_iv_tip);
        tvTip = (TextView) findViewById(R.id.collection_tv_tip);
        listView = (ListView) findViewById(R.id.collection_listview);

        headerLayout.setTitle("我的收藏");

        adapter = new NewsAdapter(CollectionActivity.this, newsList);
        listView.setAdapter(adapter);
    }
}
