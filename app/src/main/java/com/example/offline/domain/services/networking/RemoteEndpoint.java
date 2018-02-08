package com.example.offline.domain.services.networking;

import com.example.offline.model.Event;
import com.example.offline.model.HomePageRaw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RemoteEndpoint {

    @GET("/cgi-bin/_app/getbycat.cgi")
    Call<List<Event>> getEventsByCat(@Query("cat") int cat, @Query("from") int pk);

    @GET("/cgi-bin/_app/getindex.cgi")
    Call<HomePageRaw> getHomePage(@Query("sync") int savedtimestamp);
}
