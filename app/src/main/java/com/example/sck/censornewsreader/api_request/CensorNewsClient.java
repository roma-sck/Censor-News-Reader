package com.example.sck.censornewsreader.api_request;

import com.example.sck.censornewsreader.models.Example;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CensorNewsClient {
    private static final String API_URL = "https://www.kimonolabs.com";
    private static RestAdapter restAdapter;
    private static CensorAPI censorServise;
    private static CensorNewsClient client = null;

    private CensorNewsClient() {}

    public static CensorNewsClient getClient() {
        if(client == null) {
            client = new CensorNewsClient();
        }
        return client;
    }

    // The RestAdapter class generates an implementation of the CensorAPI interface.
    private static CensorAPI getService() {
        if(restAdapter == null && censorServise == null) {
            restAdapter = new RestAdapter.Builder().setEndpoint(API_URL).build();
            censorServise = restAdapter.create(CensorAPI.class);
        }
        return censorServise;
    }

    public static void updateNews() {
        CensorNewsClient.getService().newsArhive(new Callback<Example>() {
            // Callback returns Example object (model) from json
            @Override
            public void success(Example allNewses, Response response) {
                // Successful HTTP response.
                EventBus.getDefault().post(allNewses);
            }

            @Override
            public void failure(RetrofitError error) {
                // Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected exception.
                String err = error.getMessage();
                EventBus.getDefault().post(err);
            }
        });
    }
}
