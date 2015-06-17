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
    private static final String WEBVIEW_MIME = "text/html";
    private static final String WEBVIEW_ENCODING = "UTF-8";

    /**
     * get url from DetailsNewsActivity
     *
     * @param url Selected news link
     */
    public void init(String url) {
        mCurrentURL = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.web_fragment, container, false);
        if (mCurrentURL != null) {
            WebView wv = (WebView) v.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new MyWebClient());
            if( !mCurrentURL.contains("<")) {
                wv.loadUrl(mCurrentURL);
            } else {
                wv.loadDataWithBaseURL("", mCurrentURL, WEBVIEW_MIME, WEBVIEW_ENCODING, "");
            }
        }
        return v;
    }

    private class MyWebClient extends WebViewClient {
        /**
         * goes to news url
         *
         * @param view WebView
         * @param url News link
         * @return  return true means the host application handles the url, while return false means the current WebView handles the url.
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}

