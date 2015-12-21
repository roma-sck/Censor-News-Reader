package com.example.sck.censornewsreader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.api.ApiRequest;
import com.example.sck.censornewsreader.fragments.NewsListFragment;
import com.example.sck.censornewsreader.fragments.WebViewFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewsListFragment();

        ApiRequest.updateNews();
    }

    private void addNewsListFragment() {
        NewsListFragment newsListFragment = new NewsListFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.newslist_fragment, newsListFragment);
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
            case R.id.action_email:
                createSendMailIntent();
                break;
            case R.id.action_about:
                createAboutDialog();
                break;
            case R.id.action_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createSendMailIntent() {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts(getString(R.string.mailto), getString(R.string.my_mail), null));
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        mailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_text));
        startActivity(Intent.createChooser(mailIntent, getString(R.string.email_dialog_header)));
    }

    private void createAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogTitleAbout);
        builder.setMessage(R.string.dialogMsgAbout);
        AlertDialog ad = builder.create();
        ad.show();
    }
}
