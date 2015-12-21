package com.example.sck.censornewsreader.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sck.censornewsreader.App;
import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.fragments.WebViewFragment;

public class DetailsNewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        // get url selected news from NewsListFragment
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");

        if(url != null) {
            WebViewFragment webFragment = WebViewFragment.newInstance(url);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content, webFragment);
            fragmentTransaction.commit();
        } else {
            Toast.makeText(App.getContext(), R.string.url_null, Toast.LENGTH_LONG).show();
        }
    }
}

