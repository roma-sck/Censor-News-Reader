package com.example.sck.censornewsreader.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sck.censornewsreader.db.DbSaveService;
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

    private PullToRefreshView mPullToRefreshView;
    private NewsDataSource mDatasource;
    private List<Collection1> mNewsList;
    private ListView mNewsListView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventBus.getDefault().register(this);

        ititPullToRefresh();

        mDatasource = new NewsDataSource(getActivity());
        mDatasource.open();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.listnews_fragment, container, false);
        mNewsListView = (ListView) view.findViewById(android.R.id.list);
        return view;
    }

    /**
     * pull-to-refresh ( yalantis.phoenix )
     */
    private void ititPullToRefresh() {
        mPullToRefreshView = (PullToRefreshView) getActivity().findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiRequest.updateNews();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 0);
            }
        });
    }

    /**
     * update mNewsList, save updated news to DB
     *
     * @param allnews
     */
    public void onEvent(Example allnews) {
        mPullToRefreshView.setRefreshing(false);
        mNewsList = allnews.getResults().getCollection1();
        mNewsListView.setAdapter(new NewsListAdapter(getActivity(), mNewsList));
        // news saving process in IntentService
        DbSaveService.setNewsList(mNewsList);
        getActivity().startService(new Intent(getActivity(), DbSaveService.class));
    }

    /**
     * display retrofit error, update mNewsList from DB
     *
     * @param msg Retrofit error message
     */
    public void onEvent(String msg) {
        mPullToRefreshView.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

        Cursor cursor = loadFromDB();
        String[] from = new String[]{NewsDBHelper.COLUMN_TITLE, NewsDBHelper.COLUMN_DATE, NewsDBHelper.COLUMN_DEFAULTIMAGE};
        int[] to = new int[]{R.id.newsTitle, R.id.newsDate, R.id.newsImage};
        mNewsListView.setAdapter(new SimpleCursorAdapter(getActivity(), R.layout.list_item, cursor, from, to, 0));
    }

    protected Cursor loadFromDB() {
        Toast.makeText(getActivity(), R.string.dwnld_from_db, Toast.LENGTH_LONG).show();
        return mDatasource.getAllData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        String selectedNewsLink;
        if (mNewsList != null) {
            selectedNewsLink = mNewsList.get(pos).getTitle().getHref();
        } else {
            // get saved_url of selected news from db
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
}
