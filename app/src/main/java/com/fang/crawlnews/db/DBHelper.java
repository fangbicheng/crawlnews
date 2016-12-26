package com.fang.crawlnews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/11.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "news";
    private static final String CREATE_NEWS = "create table news(" +
            "id integer primary key autoincrement, " +
            "image1 varchar(200), " +
            "image2 varchar(200), " +
            "image3 varchar(200), " +
            "title varchar(200), " +
            "source varchar(200), " +
            "link varchar(200), " +
            "type integer, " +
            "category varchar(20))";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
