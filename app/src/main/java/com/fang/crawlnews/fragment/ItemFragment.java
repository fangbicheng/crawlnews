package com.fang.crawlnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.activity.DetailsActivity;
import com.fang.crawlnews.adapter.NewsAdapter;
import com.fang.crawlnews.base.BaseFragment;
import com.fang.crawlnews.bean.News;
import com.fang.crawlnews.db.NewsDao;
import com.fang.crawlnews.util.NetworkUtil;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.LoadingDialog;
import com.fang.crawlnews.widget.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ItemFragment extends BaseFragment implements XListView.IXListViewListener {
    private XListView xListView;
    private LoadingDialog loadingDialog;

    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;

    private NewsDao newsDao;

    private boolean isPrepared;     //界面初始化完成标志
    private String category;        //新闻类别

    private int currentPage = 0;
    private static final int STATE_INIT = 1;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        initData();
        initView(view);
        isPrepared = true;
        lazyLoad();

        return view;
    }


    /**
     * 初始化数据，获取新闻类别
     **/
    private void initData() {
        Bundle bundle = getArguments();
        category = bundle.getString("arg");
    }

    private void initView(View view) {
        loadingDialog = new LoadingDialog(getActivity());
        xListView = (XListView) view.findViewById(R.id.item_listview);

        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        xListView.setOnItemClickListener(new mOnItemClickListener());

        newsDao = new NewsDao(getActivity());
    }


    @Override
    public void onRefresh() {
        queryData(STATE_REFRESH, category);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        queryData(STATE_MORE, category);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        queryData(STATE_INIT, category);
    }


    /**
     * 查询后台数据
     **/
    private void queryData(final int actionType, final String category) {
        if (actionType == STATE_INIT || actionType == STATE_REFRESH) {
            currentPage = 0;
        } else if (actionType == STATE_MORE) {
            currentPage++;
        }

        if (NetworkUtil.isNetworkConnected(getActivity())) {
            loadingDialog.show();

            BmobQuery<News> query = new BmobQuery<>();
            query.addWhereEqualTo("category", category);

            query.setSkip(currentPage * 10);
            //每次查询10条数据
            query.setLimit(10);

            //执行查询方法
            query.findObjects(new FindListener<News>() {
                @Override
                public void done(List<News> object, BmobException e) {
                    if (e == null) {
                        loadingDialog.dismiss();

                        if (actionType == STATE_INIT || actionType == STATE_REFRESH) {
                            newsList.clear();
                            newsList.addAll(object);
                            adapter = new NewsAdapter(getActivity(), newsList);
                            xListView.setAdapter(adapter);

                            newsDao.deleteNews(category);
                            newsDao.addNews(newsList);
                        } else if (actionType == STATE_MORE) {
                            newsList.addAll(object);
                            adapter.notifyDataSetChanged();

                            newsDao.addNews(object);
                        }
                    } else {
                        //Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        } else {
            ToastUtil.showShort("当前无网络，请稍后再试");
            if (actionType == STATE_INIT || actionType == STATE_REFRESH) {
                newsList = newsDao.queryNews(category, currentPage);
                adapter = new NewsAdapter(getActivity(), newsList);
                xListView.setAdapter(adapter);
            } else if (actionType == STATE_MORE) {
                newsList.addAll(newsDao.queryNews(category, currentPage));
                adapter.notifyDataSetChanged();
            }
        }
    }


    private class mOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("title", adapter.getItem(position - 1).getTitle());
            intent.putExtra("link", adapter.getItem(position - 1).getLink());
            intent.putExtra("news", adapter.getItem(position - 1));
            startActivity(intent);
        }
    }
}
