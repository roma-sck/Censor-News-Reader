package com.example.sck.censornewsreader.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.models.Collection1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class NewsDataSource {

    private SQLiteDatabase mDatabase;
    private NewsDBHelper mDbHelper;

    public NewsDataSource(Context context) {
            mDbHelper = new NewsDBHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        if (mDbHelper != null) mDbHelper.close();
    }

    /**
     * add updated all news
     *
     * @param newsList Updated News List
     */
    public void addNewsToDB(List<Collection1> newsList) {
        delAllData();

        if(newsList != null) {
            String dafault_image = "android.resource://com.example.sck.censornewsreader/" + R.drawable.img_place;
            String link_prefix = "m.";

            for (int i = 0; i < newsList.size(); i++) {
                String title = newsList.get(i).getTitle().getText();
                String date = newsList.get(i).getDate();
                StringBuilder sb = new StringBuilder(newsList.get(i).getTitle().getHref());
                // change link to mobile version
                sb.insert(7, link_prefix);
                String link = sb.toString();
                String saved_url = urlToString(link);
                String img_link = newsList.get(i).getImage().getSrc();

                addNewsItem(title, date, link, saved_url, dafault_image, img_link);
            }
        }
    }

    /**
     * add one news to DB
     *
     * @param title News Title
     * @param date News added date and time
     * @param link News link
     * @param saved_url News saved to string detail
     * @param dafault_image Image from resourses (logo)
     * @param img_link Link to image (if has)
     */
    public void addNewsItem(String title, String date, String link, String saved_url, String dafault_image, String img_link) {
        ContentValues cv = new ContentValues();
        cv.put(NewsDBHelper.COLUMN_TITLE, title);
        cv.put(NewsDBHelper.COLUMN_DATE, date);
        cv.put(NewsDBHelper.COLUMN_LINK, link);
        cv.put(NewsDBHelper.COLUMN_SAVEDURL, saved_url);
        cv.put(NewsDBHelper.COLUMN_DEFAULTIMAGE, dafault_image);
        cv.put(NewsDBHelper.COLUMN_IMAGELINK, img_link);
        mDatabase.insert(NewsDBHelper.TABLE_NEWS, null, cv);
    }

    /**
     * save html page to String for full autonomy in offline mode
     *
     * @param link News link
     */
    protected String urlToString(String link) {
        StringBuffer sb;
        String savedPage = null;
        try {
            URL url = new URL(link);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));

            int numCharsRead;
            char[] charArray = new char[1024];
            sb = new StringBuffer();
            while ((numCharsRead = br.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            br.close();
            savedPage = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedPage;
    }

    /**
     * clear DB
     */
    public void delAllData() {
        mDatabase.delete(NewsDBHelper.TABLE_NEWS, null, null);
    }

    /**
     * get Cursor with all saved news from DB
     *
     * @return Cursor with all data
     */
    public Cursor getAllData() {
        return mDatabase.query(NewsDBHelper.TABLE_NEWS, null, null, null, null, null, null);
    }
}

