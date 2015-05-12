package com.example.sck.censornewsreader.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sck.censornewsreader.R;

public class WebViewFragment extends Fragment {

    private String currentURL;
    private WebView wv;

    // get url from DetailsNewsActivity
    public void init(String url) {
        currentURL = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.web_fragment, container, false);
        if (currentURL != null) {
            wv = (WebView) v.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);    // on JavaScript in webview
            wv.setWebViewClient(new MyWebClient());     // client choise to go to currentURL
            if( !currentURL.contains("<")) {
                wv.loadUrl(currentURL);
            } else {
                wv.loadDataWithBaseURL("", currentURL, "text/html", "UTF-8", "");
            }
        }
        return v;
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
            // if returns false, can follow links in WebView; if true - no
        }
    }
}

