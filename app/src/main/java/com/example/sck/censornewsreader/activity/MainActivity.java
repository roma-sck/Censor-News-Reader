package com.example.sck.censornewsreader.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.api.ApiRequest;
import com.example.sck.censornewsreader.fragments.NewsListFragment;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiRequest.updateNews();

        addFragment();
    }

    /**
     * add fragments to activity layout
     */
    protected void addFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        NewsListFragment mListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NewsListFragment.getFragmentTag());
        if (mListFragment == null) {
            mListFragment = NewsListFragment.newInstance();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.attach(mListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(getApplicationContext(), "by roma.sck@gmail.com", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
