package com.example.sck.censornewsreader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.api.ApiRequest;
import com.example.sck.censornewsreader.fragments.NewsListFragment;

public class MainActivity extends Activity {

    public final static String SPREFS_NAME = "SAVE_TO_DB_FLAG";

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

        boolean flag = getFlagValueFromSP(SPREFS_NAME);
        menu.findItem(R.id.action_save_to_db).setChecked(flag);

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
            case R.id.action_save_to_db:
                if(item.isChecked()) {
                    item.setChecked(false);
                    saveFlagValueInSP(SPREFS_NAME, false);
                } else {
                    item.setChecked(true);
                    saveFlagValueInSP(SPREFS_NAME, true);
                }
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

    private boolean getFlagValueFromSP(String key) {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(SPREFS_NAME,
                android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    private void saveFlagValueInSP(String key, boolean value){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(SPREFS_NAME,
                android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
