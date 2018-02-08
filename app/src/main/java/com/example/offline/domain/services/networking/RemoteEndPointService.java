package com.example.offline.domain.services.networking;

import com.example.offline.BuildConfig;
import com.example.offline.model.Event;
import com.example.offline.model.HomePageRaw;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public final class RemoteEndPointService {

    private static RemoteEndPointService instance;

    private final Retrofit retrofit;

    public RemoteEndPointService() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RemoteEndPointService getInstance() {
        if (instance == null) {
            instance = new RemoteEndPointService();
        }
        return instance;
    }


    public Response<List<Event>> getEventsByCatId(int catId, int fromPk) throws IOException, RemoteException {
        RemoteEndpoint service = retrofit.create(RemoteEndpoint.class);
        Timber.d("Executing getEventsByCatId for catId: " + catId);
        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response<List<Event>> response = service.getEventsByCat(catId, fromPk).execute();


        if (response == null || !response.isSuccessful() || response.errorBody() != null) {
            throw new RemoteException(response);
        } else {
            Timber.d("successful ADDING stuff: " + response.body());
            return response;
        }
    }

    public Response<HomePageRaw> getHomePageFromRemote(int savedTimeStamp) {
        RemoteEndpoint service = retrofit.create(RemoteEndpoint.class);
        Timber.d("Executing getHomePage");
        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response<HomePageRaw> response = null;
        try {
            response = service.getHomePage(savedTimeStamp).execute();
            if (response.isSuccessful() && response.errorBody() != null) {
                Timber.d("successful Loaded homepage: " + response.body());
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return response;
    }
}