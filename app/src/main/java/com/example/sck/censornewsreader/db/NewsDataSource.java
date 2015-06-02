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
import java.net.MalformedURLException;
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
     * @param newsList
     */
    public void addNewsToDB(List<Collection1> newsList) {
        delAllData();

        if(newsList != null) {
            String dafault_image = "android.resource://com.example.sck.censornewsreader/" + R.drawable.img_place;
            for (int i = 0; i < newsList.size(); i++) {
                String title = newsList.get(i).getTitle().getText();
                String date = newsList.get(i).getDate().get(0).getText() + " " + newsList.get(i).getDate().get(1).getText();
                String link = newsList.get(i).getTitle().getHref();
                String saved_url = urlToString(link);
                String img_link = newsList.get(i).getImage().getSrc();

                addNewsItem(title, date, link, saved_url, dafault_image, img_link);
            }
        }
    }

    /**
     * add one news to DB
     *
     * @param title
     * @param date
     * @param link
     * @param saved_url
     * @param dafault_image
     * @param img_link
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
     * @param URL
     */
    protected String urlToString(String URL) {
        StringBuffer sb = null;
        try {
            URL url = new URL(URL);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));

            int numCharsRead;
            char[] charArray = new char[1024];
            sb = new StringBuffer();
            while ((numCharsRead = br.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
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
     * @return Cursor
     */
    public Cursor getAllData() {
        return mDatabase.query(NewsDBHelper.TABLE_NEWS, null, null, null, null, null, null);
    }
}

