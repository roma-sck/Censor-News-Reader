package com.example.sck.censornewsreader.api;

import com.example.sck.censornewsreader.models.Example;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiRequest {

    public static void updateNews() {
        // Callback returns Example object (model) from json
        ApiManager.getService().newsArhive(new Callback<Example>() {

            // Successful HTTP response.
            @Override
            public void success(Example allNewses, Response response) {
                EventBus.getDefault().post(allNewses);
            }

            // Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected exception.
            @Override
            public void failure(RetrofitError error) {
                String err = error.getMessage();
                EventBus.getDefault().post(err);
            }
        });
    }
}
