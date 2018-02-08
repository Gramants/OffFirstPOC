package com.example.offline.domain.services.rxbus;

import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.responses.SyncHomePageResponse;
import com.example.offline.model.HomePage;
import com.example.offline.model.HomePageRaw;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class SyncHomePageRxBus {

    private static SyncHomePageRxBus instance;
    private final PublishRelay<SyncHomePageResponse> relay;

    public static synchronized SyncHomePageRxBus getInstance() {
        if (instance == null) {
            instance = new SyncHomePageRxBus();
        }
        return instance;
    }

    private SyncHomePageRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponseEventType eventType, HomePageRaw homePage) {
        relay.accept(new SyncHomePageResponse(eventType, homePage));
    }


    public Observable<SyncHomePageResponse> toObservable() {
        return relay;
    }
}
