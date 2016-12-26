package com.fang.crawlnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.activity.DetailsActivity;
import com.fang.crawlnews.adapter.NewsAdapter;
import com.fang.crawlnews.bean.News;
import com.fang.crawlnews.widget.HeaderLayout;
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
public class RecommendFragment extends Fragment implements XListView.IXListViewListener {
    private TextView tvTitle;
    private XListView xListView;
    private LoadingDialog loadingDialog;

    private List<News> newsList = new ArrayList<>();
    private NewsAdapter adapter;

    private int currentPage = 0;
    private static final int STATE_INIT = 1;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);

        initView(view);
        queryData(STATE_INIT);

        return view;
    }

    private void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.recommend_tv_title);
        xListView = (XListView) view.findViewById(R.id.recommend_listview);
        loadingDialog = new LoadingDialog(getActivity());

        tvTitle.setText("推荐");
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        xListView.setOnItemClickListener(new mOnItemClickListener());
    }

    @Override
    public void onRefresh() {
        queryData(STATE_REFRESH);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    @Override
    public void onLoadMore() {
        queryData(STATE_MORE);
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    /**
     * 查询后台数据
     **/
    private void queryData(final int actionType) {
        loadingDialog.show();

        BmobQuery<News> query = new BmobQuery<>();
        query.addWhereEqualTo("category", "recommend");

        if (actionType == STATE_INIT || actionType == STATE_REFRESH) {
            currentPage = 0;
        } else if (actionType == STATE_MORE) {
            currentPage++;
            query.setSkip(currentPage * 10);
        }

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
                    } else if (actionType == STATE_MORE) {
                        newsList.addAll(object);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }
        });
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
