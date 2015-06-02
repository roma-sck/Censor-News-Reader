package com.example.sck.censornewsreader.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sck.censornewsreader.api.ApiRequest;
import com.example.sck.censornewsreader.activity.DetailsNewsActivity;
import com.example.sck.censornewsreader.adapter.NewsListAdapter;
import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.db.NewsDBHelper;
import com.example.sck.censornewsreader.db.NewsDataSource;
import com.example.sck.censornewsreader.models.Collection1;
import com.example.sck.censornewsreader.models.Example;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class NewsListFragment extends ListFragment {

    private static boolean sFlagDbSave;
    public static final int REFRESH_DELAY = 2000;
    private PullToRefreshView mPullToRefreshView;
    private NewsDataSource mDatasource;
    private List<Collection1> newsList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sFlagDbSave = true;

        EventBus.getDefault().register(this);

        ititPullToRefresh();

        mDatasource = new NewsDataSource(getActivity());
        mDatasource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listnews_fragment, container, false);
    }

    /**
     * pull-to-refresh ( yalantis.phoenix )
     */
    private void ititPullToRefresh() {
        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiRequest.updateNews();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    /**
     * update newsList
     *
     * @param allnews
     */
    public void onEvent(Example allnews) {
        mPullToRefreshView.setRefreshing(false);
        newsList = allnews.getResults().getCollection1();
        ListView newsListView = (ListView) getView().findViewById(android.R.id.list);
        newsListView.setAdapter(new NewsListAdapter(getActivity(), newsList));
        // add data to DB in async task
        new DBsave().execute();
    }

    /**
     * display retrofit error, update newsList from DB
     *
     * @param msg
     */
    public void onEvent(String msg) {
        mPullToRefreshView.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

        Cursor cursor = loadFromBD();
        String[] from = new String[]{NewsDBHelper.COLUMN_TITLE, NewsDBHelper.COLUMN_DATE, NewsDBHelper.COLUMN_DEFAULTIMAGE};
        int[] to = new int[]{R.id.newsTitle, R.id.newsDate, R.id.newsImage};
        ListView newsListView = (ListView) getView().findViewById(android.R.id.list);
        newsListView.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.list_item, cursor, from, to, 0));
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        String selectedNewsLink;
        if (newsList != null) {
            selectedNewsLink = newsList.get(pos).getTitle().getHref();
        } else {
            // get saved_url selected news from db
            Cursor cursor = (Cursor) l.getItemAtPosition(pos);
            selectedNewsLink = cursor.getString(cursor.getColumnIndexOrThrow(NewsDBHelper.COLUMN_SAVEDURL));
        }
        Intent intent = new Intent(getActivity(), DetailsNewsActivity.class);
        intent.putExtra("url", selectedNewsLink);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        mDatasource.close();
        super.onDestroyView();
    }

    protected Cursor loadFromBD() {
        Toast.makeText(getActivity(), R.string.dwnld_from_db, Toast.LENGTH_LONG).show();
        return mDatasource.getAllData();
    }

    private class DBsave extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            if(sFlagDbSave == true) {
                mDatasource.addNewsToDB(newsList);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            sFlagDbSave = false;
        }
    }

}
