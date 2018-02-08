package com.example.offline.di;

import com.example.offline.domain.ActionOnLocalRepository;
import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.preferences.PersistentStorageProxy;
import com.example.offline.presentation.ExpoDetailViewModelFactory;


import dagger.Module;
import dagger.Provides;

@Module
public class ExpoDetailModule {


    @Provides
    ExpoDetailViewModelFactory provideExpoDetailViewModelFactory(GetFromLocalUseCase getFromLocalUseCase,
                                                                 PersistentStorageProxy persistentStorageProxy) {
        return new ExpoDetailViewModelFactory(getFromLocalUseCase, persistentStorageProxy);
    }


    @Provides
    GetFromLocalUseCase provideGetFromLocalUseCase(ActionOnLocalRepository actionOnLocalRepository) {
        return new GetFromLocalUseCase(actionOnLocalRepository);
    }
}
