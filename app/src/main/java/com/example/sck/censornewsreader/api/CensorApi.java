package com.example.sck.censornewsreader.api;

import retrofit.Callback;
import retrofit.http.GET;

import com.example.sck.censornewsreader.models.Example;

/**
 * HTTP request to the remote webserver (our API); Retrofit uses Gson by default to convert HTTP bodies to and from JSON.
 */
public interface CensorApi {

    @GET( Constants.API_PREFIX + Constants.API_KEY )
    void newsArchive(Callback<Example> callback);
}