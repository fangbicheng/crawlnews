package com.fang.crawlnews.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import com.fang.crawlnews.R;
import com.fang.crawlnews.base.BaseActivity;
import com.fang.crawlnews.bean.Comment;
import com.fang.crawlnews.bean.News;
import com.fang.crawlnews.util.KeyBoardUtil;
import com.fang.crawlnews.util.ToastUtil;
import com.fang.crawlnews.widget.InputDialog;
import com.fang.crawlnews.widget.LoadingDialog;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2016/8/4.
 */
public class DetailsActivity extends BaseActivity {
    private ImageButton btnBack;
    private ImageButton btnMore;
    private ImageButton btnCollection;
    private ImageButton btnShare;
    private EditText etComment;
    private WebView webView;
    private LoadingDialog loadingDialog;

    private String title, link;
    private News news;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initData();
        initView();

        webView.loadUrl(link);
    }

    private void initData() {
        title = getIntent().getStringExtra("title");
        link = getIntent().getStringExtra("link");
        news = (News) getIntent().getSerializableExtra("news");
    }

    private void initView() {
        btnBack = (ImageButton) findViewById(R.id.details_ibtn_back);
        btnMore = (ImageButton) findViewById(R.id.details_ibtn_more);
        btnCollection = (ImageButton) findViewById(R.id.details_ibtn_collection);
        btnShare = (ImageButton) findViewById(R.id.details_ibtn_share);
        etComment = (EditText) findViewById(R.id.details_et_comment);
        webView = (WebView) findViewById(R.id.details_webview);
        loadingDialog = new LoadingDialog(DetailsActivity.this);

        btnBack.setOnClickListener(new mOnClickListener());
        btnMore.setOnClickListener(new mOnClickListener());
        btnCollection.setOnClickListener(new mOnClickListener());
        btnShare.setOnClickListener(new mOnClickListener());
        etComment.setOnClickListener(new mOnClickListener());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new mWebViewClient());

        collectNews(news, true);    //收藏按钮界面初始化

    }

    private class mOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.details_ibtn_back:
                    finish();
                    break;
                case R.id.details_ibtn_more:
                    showShare(true, title, link);
                    break;
                case R.id.details_ibtn_collection:
                    collectNews(news, false);
                    break;
                case R.id.details_ibtn_share:
                    showShare(false, title, link);
                    break;
                case R.id.details_et_comment:
                    showComment();
                default:
                    break;
            }
        }
    }

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingDialog.dismiss();
        }
    }


    private void addComment(String content, News news) {
        BmobUser user = BmobUser.getCurrentUser();

        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setNews(news);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtil.showShort("发表成功");
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }

        });
    }


    /**
     * 增加收藏
     **/
    private void addCollection(News news) {
        BmobUser user = BmobUser.getCurrentUser();

        BmobRelation relation = new BmobRelation();
        relation.add(user);

        news.setCollection(relation);
        news.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.showShort("收藏成功");
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }
        });
    }


    /**
     * 取消收藏
     **/
    private void deleteCollection(News news) {
        BmobUser user = BmobUser.getCurrentUser();

        BmobRelation relation = new BmobRelation();
        relation.remove(user);

        news.setCollection(relation);
        news.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.showShort("收藏已取消");
                } else {
                    Log.i("bmob", e.getMessage());
                }
            }
        });
    }


    /**
     * 一键收藏新闻
     **/
    private void collectNews(final News news, final boolean isInitView) {
        if (BmobUser.getCurrentUser() != null) {
            BmobQuery<BmobUser> query = new BmobQuery<>();
            query.addWhereRelatedTo("collection", new BmobPointer(news));
            query.findObjects(new FindListener<BmobUser>() {
                @Override
                public void done(List<BmobUser> object, BmobException e) {
                    if (e == null) {
                        for (BmobUser user : object) {
                            if (user.getUsername().equals(BmobUser.getCurrentUser().getUsername())) {
                                if (! isInitView) {
                                    btnCollection.setBackgroundResource(R.drawable.details_btn_collection_selector);
                                    deleteCollection(news);
                                } else {
                                    btnCollection.setBackgroundResource(R.drawable.details_btn_collection_selected);
                                }
                                return;
                            }
                        }
                        if (! isInitView) {
                            btnCollection.setBackgroundResource(R.drawable.details_btn_collection_selected);
                            addCollection(news);
                        } else {
                            btnCollection.setBackgroundResource(R.drawable.details_btn_collection_selector);
                        }
                    } else {
                        Log.i("bmob", e.getMessage());
                    }
                }
            });
        } else {
            if (! isInitView) {
                ToastUtil.showShort("您还没登录呢");
            }
            return;
        }
    }


    /**
     * 一键分享新闻
     **/
    private void showShare(boolean isShowMore, String title, String link) {
        if (BmobUser.getCurrentUser() != null) {
            ShareSDK.initSDK(this);
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();

            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(getString(R.string.app_name));
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(link);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(title);
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(link);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment("我是测试评论文本");
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(link);


            if (isShowMore) {
                Bitmap collection = BitmapFactory.decodeResource(DetailsActivity.this.getResources(),
                        R.drawable.ssdk_oks_classic_collection);
                String collectionLabel = "收藏";
                View.OnClickListener collectionListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        collectNews(news, false);
                    }
                };

                Bitmap report = BitmapFactory.decodeResource(DetailsActivity.this.getResources(),
                        R.drawable.ssdk_oks_classic_report);
                String reportLabel = "投诉";
                View.OnClickListener reportListener = new View.OnClickListener() {
                    public void onClick(View v) {

                    }
                };
                oks.setCustomerLogo(collection, collectionLabel, collectionListener);
                oks.setCustomerLogo(report, reportLabel, reportListener);
            }

            // 启动分享GUI
            oks.show(this);
        } else {
            ToastUtil.showShort("您还没登录呢");
        }
    }

    private void showComment() {
        if (BmobUser.getCurrentUser() != null) {
            InputDialog inputDialog = new InputDialog(DetailsActivity.this).builder();
            inputDialog.setTitle("发表评论")
                    .setPositiveBtn("发表", new InputDialog.OnPositiveListener() {
                        @Override
                        public void onPositive(View view, String inputMsg) {
                            addComment(inputMsg, news);
                        }
                    })
                    .setNegativeBtn("取消", null)
                    .setContentMsg("");
            inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
            inputDialog.show();
        } else {
            ToastUtil.showShort("您还没登录呢");
        }
    }

}
