package com.example.sck.censornewsreader;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sck.censornewsreader.api_request.CensorNewsClient;
import com.example.sck.censornewsreader.fragments.NewsListFragment;

public class MainActivity extends ActionBarActivity {

    // id for menu items
    private final int MENU_ABOUT_ID = 1;
    private final int MENU_QUIT_ID = 2;

    private NewsListFragment newsListFragment;
    private static final String TAG = "NewsListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            // set activity layout
            setContentView(R.layout.activity_main);

            CensorNewsClient.getClient().updateNews();

            addFragment();
    }

    // add fragments to activity layout
    protected void addFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(TAG);
        if (newsListFragment == null) {
            newsListFragment = new NewsListFragment();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(android.R.id.content, newsListFragment);
        fragmentTransaction.commit();
    }

    // create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ABOUT_ID, 0, "About");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    // processing taps on the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ABOUT_ID:
                Toast.makeText(this, "Yalantis task by roma.sck@gmail.com", Toast.LENGTH_LONG).show();
                break;
            case MENU_QUIT_ID:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
