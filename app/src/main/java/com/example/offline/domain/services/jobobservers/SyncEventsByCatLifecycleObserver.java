package com.example.offline.domain.services.jobobservers;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.example.offline.domain.UpdateToLocalUseCase;

import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.responses.SyncEventsListByCatResponse;
import com.example.offline.domain.services.rxbus.SyncEventsListByCatRxBus;
import com.example.offline.model.Event;
import com.example.offline.preferences.PersistentStorageProxy;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SyncEventsByCatLifecycleObserver implements LifecycleObserver {
    private final UpdateToLocalUseCase updateToLocalUseCase;
    private final PersistentStorageProxy persistentStorageProxy;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SyncEventsByCatLifecycleObserver(UpdateToLocalUseCase updateToLocalUseCase,
                                            PersistentStorageProxy persistentStorageProxy) {
        this.updateToLocalUseCase = updateToLocalUseCase;
        this.persistentStorageProxy = persistentStorageProxy;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume lifecycle event.");
        disposables.add(SyncEventsListByCatRxBus.getInstance().toObservable()
                .subscribe(this::handleSyncResponse, t -> Timber.e(t, "error handling sync response")));
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.d("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleSyncResponse(SyncEventsListByCatResponse response) {
        if (response.eventType == SyncResponseEventType.SUCCESS) {
            onSyncCommentSuccess(response.events, response.catId);
        }
    }

    private void onSyncCommentSuccess(List<Event> events, int catId) {
        HashMap hm = persistentStorageProxy.getIndexCatMap();
        int maxPk = 0;
        if (events.size() == 1) {
            maxPk = (int) ((Event) events.get(0)).getId();
        } else if (events.size() > 1) {
            Event event = Collections.max(events, POWER_COMPARATOR);
            maxPk = event.getId();
        }
        hm.put(catId, maxPk);

        persistentStorageProxy.saveIndexCatMap(hm);

        if (maxPk > 0) {
            disposables.add(updateToLocalUseCase.addEventsByCat(events, catId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> Timber.d("update comment success"),
                            t -> Timber.e(t, "update comment error")));
        }
    }


    public static final Comparator<Event> POWER_COMPARATOR =
            new Comparator<Event>() {
                public int compare(Event dev1, Event dev2) {
                    return Integer.compare(dev1.getId(), dev2.getId());
                }
            };


}


