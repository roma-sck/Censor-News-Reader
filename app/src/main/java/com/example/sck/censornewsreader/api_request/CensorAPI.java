package com.example.sck.censornewsreader.api_request;

import retrofit.Callback;
import retrofit.http.GET;

import com.example.sck.censornewsreader.models.Example;

public interface CensorAPI {

    // HTTP request to the remote webserver (our API); Retrofit uses Gson by default to convert HTTP bodies to and from JSON.
    @GET("/api/2ydfg6ng?apikey=EiEsK7B8Gp7J9e39N9IyPSgpr57k3Nk2")
    public void newsArhive(Callback<Example> callback);
}