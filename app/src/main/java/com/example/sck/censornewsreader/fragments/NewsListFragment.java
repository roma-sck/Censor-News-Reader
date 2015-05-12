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

import com.example.sck.censornewsreader.api_request.CensorNewsClient;
import com.example.sck.censornewsreader.DetailsNewsActivity;
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

    public static final int REFRESH_DELAY = 2000;
    private PullToRefreshView mPullToRefreshView;
    private NewsDataSource datasource;
    private List<Collection1> newsList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventBus.getDefault().register(this);

        ititPullToRefresh();

        datasource = new NewsDataSource(getActivity());
        datasource.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate layout for this fragment
        return inflater.inflate(R.layout.listnews_fragment, container, false);
    }

    private void ititPullToRefresh() {
        // pull-to-refresh ( yalantis.phoenix )
        mPullToRefreshView = (PullToRefreshView) getView().findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CensorNewsClient.getClient().updateNews();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    // update newsList
    public void onEvent(Example allnews) {
        // hide mPullToRefreshView; get newsList; set view adapter
        mPullToRefreshView.setRefreshing(false);
        newsList = allnews.getResults().getCollection1();
        ListView newsListView = (ListView) getView().findViewById(android.R.id.list);
        newsListView.setAdapter(new NewsListAdapter(getActivity(), newsList));
        // add data to DB in async task
        new DBsave().execute();
    }

    // display retrofit error
    public void onEvent(String msg) {
        mPullToRefreshView.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        // load data from DB nad show in newsListView
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
            // get url selected news
            selectedNewsLink = newsList.get(pos).getTitle().getHref();
        } else {
            // get saved_url selected news from db
            Cursor cursor = (Cursor) l.getItemAtPosition(pos);
            selectedNewsLink = cursor.getString(cursor.getColumnIndexOrThrow(NewsDBHelper.COLUMN_SAVEDURL));
        }
        // submit url in second activity through intent
        Intent intent = new Intent(getActivity(), DetailsNewsActivity.class);
        intent.putExtra("url", selectedNewsLink);
        // start new activity
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        datasource.close();
        super.onDestroyView();
    }

    protected Cursor loadFromBD() {
        Toast.makeText(getActivity(), "Data downloaded from DB", Toast.LENGTH_LONG).show();
        return datasource.getAllData();
    }

    private class DBsave extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            datasource.addNewsToDB(newsList);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

}
