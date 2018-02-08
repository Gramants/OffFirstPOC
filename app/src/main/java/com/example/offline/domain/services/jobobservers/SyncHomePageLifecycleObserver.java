package com.example.offline.domain.services.jobobservers;


import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.example.offline.domain.UpdateToLocalUseCase;
import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.domain.services.responses.SyncHomePageResponse;
import com.example.offline.domain.services.rxbus.SyncHomePageRxBus;
import com.example.offline.model.HomePageMapper;
import com.example.offline.model.HomePageRaw;
import com.example.offline.preferences.PersistentStorageProxy;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SyncHomePageLifecycleObserver implements LifecycleObserver {
    private final UpdateToLocalUseCase updateToLocalUseCase;
    private final PersistentStorageProxy persistentStorageProxy;
    private final HomePageMapper homepageMapper;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SyncHomePageLifecycleObserver(UpdateToLocalUseCase updateToLocalUseCase,
                                         HomePageMapper homepageMapper,
                                         PersistentStorageProxy persistentStorageProxy) {
        this.updateToLocalUseCase = updateToLocalUseCase;
        this.homepageMapper = homepageMapper;
        this.persistentStorageProxy = persistentStorageProxy;
    }

    @OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume lifecycle event.");
        disposables.add(SyncHomePageRxBus.getInstance().toObservable()
                .subscribe(this::handleSyncResponse, t -> Timber.e(t, "error handling sync response")));
    }


    @OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.d("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleSyncResponse(SyncHomePageResponse response) throws Exception {
        if (response.eventType == SyncResponseEventType.SUCCESS) {
            onSyncHomePageSuccess(response.homePage);
        }
    }

    private void onSyncHomePageSuccess(HomePageRaw homePage) throws Exception {
        persistentStorageProxy.saveSyncedTimeStamp(homePage.getTimestamp());
        persistentStorageProxy.setNeedToSync(homePage.getNeedsync());
        disposables.add(updateToLocalUseCase.addHomePage(homepageMapper.apply(homePage))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("update homepage"),
                        t -> Timber.e(t, "update homepage")));

    }


}


