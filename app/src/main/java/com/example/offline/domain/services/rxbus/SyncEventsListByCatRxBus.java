package com.example.offline.domain.services.rxbus;


import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.responses.SyncEventsListByCatResponse;
import com.example.offline.model.Event;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class SyncEventsListByCatRxBus {

    private static SyncEventsListByCatRxBus instance;
    private final PublishRelay<SyncEventsListByCatResponse> relay;

    public static synchronized SyncEventsListByCatRxBus getInstance() {
        if (instance == null) {
            instance = new SyncEventsListByCatRxBus();
        }
        return instance;
    }

    private SyncEventsListByCatRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponseEventType eventType, List<Event> events, int catId) {
        Timber.d("Executing post(SyncResponseEventType eventType, List<Event> events)");
        relay.accept(new SyncEventsListByCatResponse(eventType, events, catId));
    }


    public Observable<SyncEventsListByCatResponse> toObservable() {
        return relay;
    }
}
