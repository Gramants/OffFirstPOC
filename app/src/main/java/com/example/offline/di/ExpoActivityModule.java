package com.example.offline.di;

import com.example.offline.domain.ActionOnLocalRepository;
import com.example.offline.domain.ActionOnRemoteRepository;
import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.domain.GetFromRemoteUseCase;
import com.example.offline.domain.UpdateToLocalUseCase;
import com.example.offline.domain.services.jobobservers.SyncEventsByCatLifecycleObserver;
import com.example.offline.domain.services.jobobservers.SyncHomePageLifecycleObserver;
import com.example.offline.model.HomePageMapper;
import com.example.offline.preferences.PersistentStorageProxy;
import com.example.offline.presentation.ExpoViewModelFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Define ExpoActivity-specific dependencies here.
 */
@Module
public class ExpoActivityModule {


    @Provides
    ExpoViewModelFactory provideExpoViewModelFactory(GetFromLocalUseCase getFromLocalUseCase,
                                                     GetFromRemoteUseCase getFromRemoteUseCase,
                                                     PersistentStorageProxy persistentStorageProxy) {
        return new ExpoViewModelFactory(getFromLocalUseCase, getFromRemoteUseCase, persistentStorageProxy);
    }


    @Provides
    SyncEventsByCatLifecycleObserver provideSyncEventsByCatLifecycleObserver(UpdateToLocalUseCase updateToLocalUseCase,
                                                                             PersistentStorageProxy persistentStorageProxy) {
        return new SyncEventsByCatLifecycleObserver(updateToLocalUseCase, persistentStorageProxy);
    }

    @Provides
    SyncHomePageLifecycleObserver provideSyncHomePageLifecycleObserver(UpdateToLocalUseCase updateToLocalUseCase,
                                                                       HomePageMapper homepageMapper,
                                                                       PersistentStorageProxy persistentStorageProxy) {
        return new SyncHomePageLifecycleObserver(updateToLocalUseCase, homepageMapper, persistentStorageProxy);
    }






    // DEPENDENCY FOR ENDPOINTS REMOTE GET

    @Provides
    GetFromRemoteUseCase provideRemoteSyncUseCase(ActionOnRemoteRepository actionOnRemoteRepository) {
        return new GetFromRemoteUseCase(actionOnRemoteRepository);
    }


    // DEPENDENCY FOR LOCAL DB UPDATE

    @Provides
    UpdateToLocalUseCase provideUpdateToLocalUseCase(ActionOnLocalRepository actionOnLocalRepository) {
        return new UpdateToLocalUseCase(actionOnLocalRepository);
    }

    @Provides
    GetFromLocalUseCase provideGetFromLocalUseCase(ActionOnLocalRepository actionOnLocalRepository) {
        return new GetFromLocalUseCase(actionOnLocalRepository);
    }
}
