package com.example.offline.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.domain.GetFromRemoteUseCase;
import com.example.offline.model.Event;
import com.example.offline.model.HomePage;
import com.example.offline.preferences.PersistentStorageProxy;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ExpoViewModel extends ViewModel {

    private final GetFromLocalUseCase getFromLocalUseCase;
    private final GetFromRemoteUseCase getFromRemoteUseCase;
    private final PersistentStorageProxy persistentStorageProxy;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private SingleLiveEvent<List<Event>> eventsLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<HomePage> homepageLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<Event> eventLiveData = new SingleLiveEvent<>();

    public ExpoViewModel(GetFromLocalUseCase getFromLocalUseCase,
                         GetFromRemoteUseCase getFromRemoteUseCase,
                         PersistentStorageProxy persistentStorageProxy) {
        this.getFromLocalUseCase = getFromLocalUseCase;
        this.getFromRemoteUseCase = getFromRemoteUseCase;
        this.persistentStorageProxy = persistentStorageProxy;

    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }


    public void getRemoteEventsByCat(int catId, Boolean force) {
        int maxpercat = 0;
        if ((((HashMap) persistentStorageProxy.getIndexCatMap() != null)) && (!force)) {
            if (((HashMap) persistentStorageProxy.getIndexCatMap()).get(catId) != null) {
                maxpercat = (int) ((HashMap) persistentStorageProxy.getIndexCatMap()).get(catId);
            }
        }


        disposables.add(getFromRemoteUseCase.syncEventsByCat(catId, maxpercat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("add comment success"),
                        t -> Timber.e(t, "add comment error")));
    }

    public void getRemoteHomePage() {
        disposables.add(getFromRemoteUseCase.syncHomePage(persistentStorageProxy.getLastSyncedTimeStamp())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("add comment success"),
                        t -> Timber.e(t, "add comment error")));
    }


    public void loadEventsByCatFromDb(int catId) {
        disposables.add(getFromLocalUseCase.getEventsByCatId(catId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventsLiveData::setValue,
                        t -> Timber.e(t, "get comments error")));
    }

    public void loadHomePageFromDb() {
        disposables.add(getFromLocalUseCase.getHomePage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(homepageLiveData::setValue,
                        t -> Timber.e(t, "get comments error")));
    }


    public void loadLocalEventById(int eventId) {
        disposables.add(getFromLocalUseCase.getEventById(eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventLiveData::setValue,
                        t -> Timber.e(t, "get comments error")));
    }


    public LiveData<List<Event>> filteredEventsByCat() {
        return eventsLiveData;
    }

    public LiveData<HomePage> homePage() {
        return homepageLiveData;
    }

    public LiveData<Event> eventDetailPage() {
        return eventLiveData;
    }


    public boolean needToSync() {
        return persistentStorageProxy.getNeedToSync();
    }

    public void setNeedToSync(Boolean need) {
        persistentStorageProxy.setNeedToSync(need);
    }

    public void setAllBackGroundJobs(int[] catToUpdateInBackGroundInt) {
        for (int i = 0; i < catToUpdateInBackGroundInt.length; i++) {
            getRemoteEventsByCat(catToUpdateInBackGroundInt[i], false);
        }
    }
}
