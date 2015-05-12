package com.example.sck.censornewsreader;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sck.censornewsreader.fragments.WebViewFragment;

public class DetailsNewsActivity extends Activity {

    private ProgressBar progress;
    // initialize fragment
    private WebViewFragment wvf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        progress = (ProgressBar) findViewById(R.id.progress);

        // get url selected news from NewsListFragment
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");

        if(url != null) {
            wvf = new WebViewFragment();
            // initialize currentURL WebViewFragment
            wvf.init(url);
            // call FragmentManager, add fragment to activity, commit transaction
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(android.R.id.content, wvf, url);
            fragmentTransaction.commit();
        } else { Toast.makeText(this, "url is null", Toast.LENGTH_LONG).show(); }
    }
}

