package com.fang.crawlnews.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.crawlnews.bean.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class NewsDao {
    private DBHelper dbHelper;

    public NewsDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addNews(List<News> newsList) {
        String sql = "insert into news (image1, image2, image3, title, source, link, type, category) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (News news : newsList) {
            db.execSQL(sql, new Object[] {news.getImage1(), news.getImage2(), news.getImage3(),
                news.getTitle(), news.getSource(), news.getLink(), news.getType(), news.getCategory()});
        }

        db.close();
    }

    public void deleteNews(String category) {
        String sql = "delete from news where category = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql, new String[] {category});
        db.close();
    }

    public List<News> queryNews(String category, int currentPage) {
        List<News> newsList = new ArrayList<>();

        int offset = 10 * currentPage;
        String sql = "select * from news where category = ? limit ?, ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{category, offset + "", offset + 10 + ""});

        while (cursor.moveToNext()) {
            News news = new News();

            news.setImage1(cursor.getString(1));
            news.setImage2(cursor.getString(2));
            news.setImage3(cursor.getString(3));
            news.setTitle(cursor.getString(4));
            news.setSource(cursor.getString(5));
            news.setLink(cursor.getString(6));
            news.setType(cursor.getInt(7));
            news.setCategory(cursor.getString(8));

            newsList.add(news);
        }

        cursor.close();
        db.close();

        return newsList;
    }
}
