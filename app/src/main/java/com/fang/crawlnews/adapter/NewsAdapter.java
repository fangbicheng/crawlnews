package com.fang.crawlnews.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.crawlnews.R;
import com.fang.crawlnews.bean.News;
import com.fang.crawlnews.util.ImageLoaderOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class NewsAdapter extends BaseAdapter {
    private static final int TYPE_NONE = 0;
    private static final int TYPE_SINGLE = 1;
    private static final int TYPE_MULTIPLE = 2;

    private Context context;
    private List<News> newsList;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;

        options = ImageLoaderOptions.getListOptions();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return newsList == null? 0 : newsList.size();
    }

    @Override
    public News getItem(int position) {
        if (newsList != null && newsList.size() != 0) {
            return newsList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return newsList.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);

        ViewHolderNone viewHolderNone;
        ViewHolderSingle viewHolderSingle;
        ViewHolderMultiple viewHolderMultiple;

        switch (getItemViewType(position)) {
            case TYPE_NONE:
                if (convertView == null) {
                    viewHolderNone = new ViewHolderNone();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_news_none, null);
                    viewHolderNone.tvTitle = (TextView) convertView.findViewById(R.id.item_news_none_tv_title);
                    viewHolderNone.tvSource = (TextView) convertView.findViewById(R.id.item_news_none_tv_source);
                    convertView.setTag(viewHolderNone);
                } else {
                    viewHolderNone = (ViewHolderNone) convertView.getTag();
                }

                viewHolderNone.tvTitle.setText(news.getTitle());
                viewHolderNone.tvSource.setText(news.getSource());
                break;
            case TYPE_SINGLE:
                if (convertView == null) {
                    viewHolderSingle = new ViewHolderSingle();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_news_single, null);
                    viewHolderSingle.ivIcon = (ImageView) convertView.findViewById(R.id.item_news_single_iv_icon);
                    viewHolderSingle.tvTitle = (TextView) convertView.findViewById(R.id.item_news_single_tv_title);
                    viewHolderSingle.tvSource = (TextView) convertView.findViewById(R.id.item_news_single_tv_source);
                    convertView.setTag(viewHolderSingle);
                } else {
                    viewHolderSingle = (ViewHolderSingle) convertView.getTag();
                }

                imageLoader.displayImage(news.getImage1(), viewHolderSingle.ivIcon, options);
                viewHolderSingle.tvTitle.setText(news.getTitle());
                viewHolderSingle.tvSource.setText(news.getSource());
                break;
            case TYPE_MULTIPLE:
                if (convertView == null) {
                    viewHolderMultiple = new ViewHolderMultiple();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_news_mutiple, null);
                    viewHolderMultiple.ivIcon1 = (ImageView) convertView.findViewById(R.id.item_news_multiple_iv_icon1);
                    viewHolderMultiple.ivIcon2 = (ImageView) convertView.findViewById(R.id.item_news_multiple_iv_icon2);
                    viewHolderMultiple.ivIcon3 = (ImageView) convertView.findViewById(R.id.item_news_multiple_iv_icon3);
                    viewHolderMultiple.tvTitle = (TextView) convertView.findViewById(R.id.item_news_multiple_tv_title);
                    viewHolderMultiple.tvSource = (TextView) convertView.findViewById(R.id.item_news_multiple_tv_source);
                    convertView.setTag(viewHolderMultiple);
                } else {
                    viewHolderMultiple = (ViewHolderMultiple) convertView.getTag();
                }

                imageLoader.displayImage(news.getImage1(), viewHolderMultiple.ivIcon1, options);
                imageLoader.displayImage(news.getImage2(), viewHolderMultiple.ivIcon2, options);
                imageLoader.displayImage(news.getImage3(), viewHolderMultiple.ivIcon3, options);
                viewHolderMultiple.tvTitle.setText(news.getTitle());
                viewHolderMultiple.tvSource.setText(news.getSource());
                break;
            default:
                break;
        }

        return convertView;
    }

    private final class ViewHolderNone {
        public TextView tvTitle;
        public TextView tvSource;
    }

    private final class ViewHolderSingle {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvSource;
    }

    private final class ViewHolderMultiple {
        public ImageView ivIcon1;
        public ImageView ivIcon2;
        public ImageView ivIcon3;
        public TextView tvTitle;
        public TextView tvSource;
    }
}
