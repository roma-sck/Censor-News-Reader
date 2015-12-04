package com.example.sck.censornewsreader.db;

import android.app.IntentService;
import android.content.Intent;

import com.example.sck.censornewsreader.models.Collection1;

import java.util.List;

public class DbSaveService extends IntentService {

    private static final String TAG = "DbSaveService";
    private static List<Collection1> mNewsList;
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public DbSaveService() {
        super(TAG);
    }

    public static void setNewsList(List<Collection1> list) {
        mNewsList = list;
    }
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        NewsDataSource dataSource = new NewsDataSource(getBaseContext());
        dataSource.open();
        dataSource.addNewsToDB(mNewsList);
        dataSource.close();
    }
}