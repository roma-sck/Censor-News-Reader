package com.example.sck.censornewsreader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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
        final String mailto = "mailto";
        final String my_mail = "roma.sck@gmail.com";
        final String email_subject = "Censor News Reader report: ";
        final String email_text = "\n I like your app.";
        final String email_dialog_header = "Send email...";

        Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(mailto, my_mail, null));
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, email_subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT, email_text);
        startActivity(Intent.createChooser(mailIntent, email_dialog_header));
    }

    private void createAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogTitleAbout);
        builder.setMessage(R.string.dialogMsgAbout);
        AlertDialog ad = builder.create();
        ad.show();
    }
}
