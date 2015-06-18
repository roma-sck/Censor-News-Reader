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

    private WebView mWebView;
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
        if (mWebView != null) {
            mWebView.destroy();
        }
        View v = inflater.inflate(R.layout.web_fragment, container, false);
        if (mCurrentURL != null) {
            mWebView = (WebView) v.findViewById(R.id.webPage);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new MyWebClient());
            if( !mCurrentURL.contains("<")) {
                mWebView .loadUrl(mCurrentURL);
            } else {
                mWebView.loadDataWithBaseURL("", mCurrentURL, WEBVIEW_MIME, WEBVIEW_ENCODING, "");
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

