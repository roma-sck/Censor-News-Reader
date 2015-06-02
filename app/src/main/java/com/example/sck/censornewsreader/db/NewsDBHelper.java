package com.example.sck.censornewsreader.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NEWS = "censor_news";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_SAVEDURL = "saved_url";
    public static final String COLUMN_DEFAULTIMAGE = "dafault_image";
    public static final String COLUMN_IMAGELINK = "img_link";

    private static final String DATABASE_NAME = "censor_news.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + TABLE_NEWS +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null, " +
            COLUMN_DATE + " text not null, " +
            COLUMN_LINK + " text not null, " +
            COLUMN_SAVEDURL + " text not null, " +
            COLUMN_DEFAULTIMAGE + " text not null, " +
            COLUMN_IMAGELINK + " text " +
            ");";

    public NewsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {}

}
