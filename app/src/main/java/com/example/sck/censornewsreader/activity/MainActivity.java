package com.example.sck.censornewsreader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sck.censornewsreader.R;
import com.example.sck.censornewsreader.api.ApiRequest;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiRequest.updateNews();
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
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "roma.sck@gmail.com" });
        mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Censor News Reader report: ");
        mailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "\n I like your app.");
        startActivity(Intent.createChooser(mailIntent, "Send via:"));
    }

    private void createAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogTitleAbout);
        builder.setMessage(R.string.dialogMsgAbout);
        AlertDialog ad = builder.create();
        ad.show();
    }
}
