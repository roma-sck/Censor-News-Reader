package com.example.sck.censornewsreader.api;

import retrofit.RestAdapter;

public class ApiManager {

    /**
     * The RestAdapter class generates an implementation of the CensorAPI interface.
     */
    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder().setEndpoint(Constants.API_URL).build();

    private static final CensorApi CENSOR_NET_SERVICE = REST_ADAPTER.create(CensorApi.class);

    public static CensorApi getService() {
        return CENSOR_NET_SERVICE;
    }
}
