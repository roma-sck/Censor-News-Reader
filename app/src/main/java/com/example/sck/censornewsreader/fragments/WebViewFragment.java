package com.example.sck.censornewsreader.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sck.censornewsreader.R;

public class WebViewFragment extends Fragment {

    private String mCurrentURL;
    private WebView wv;

    /**
     * get url from DetailsNewsActivity
     *
     * @param url
     */
    public void init(String url) {
        mCurrentURL = url;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.web_fragment, container, false);
        if (mCurrentURL != null) {
            wv = (WebView) v.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new MyWebClient());
            if( !mCurrentURL.contains("<")) {
                wv.loadUrl(mCurrentURL);
            } else {
                wv.loadDataWithBaseURL("", mCurrentURL, "text/html", "UTF-8", "");
            }
        }
        return v;
    }

    private class MyWebClient extends WebViewClient {
        /**
         * goes to news url
         *
         * @param view
         * @param url
         * @return  return true means the host application handles the url, while return false means the current WebView handles the url.
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}

