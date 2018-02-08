package com.example.offline.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.model.Event;
import com.example.offline.preferences.PersistentStorageProxy;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ExpoDetailViewModel extends ViewModel {

    private final GetFromLocalUseCase getFromLocalUseCase;
    private final PersistentStorageProxy persistentStorageProxy;
    private final CompositeDisposable disposables = new CompositeDisposable();


    private SingleLiveEvent<Event> eventLiveData = new SingleLiveEvent<>();

    public ExpoDetailViewModel(GetFromLocalUseCase getFromLocalUseCase,
                               PersistentStorageProxy persistentStorageProxy) {
        this.getFromLocalUseCase = getFromLocalUseCase;
        this.persistentStorageProxy = persistentStorageProxy;

    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }


    public void loadLocalEventById(int eventId) {
        disposables.add(getFromLocalUseCase.getEventById(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventLiveData::setValue,
                        t -> Timber.e(t, "get comments error")));
    }


    public LiveData<Event> eventDetailPage() {
        return eventLiveData;
    }


}
