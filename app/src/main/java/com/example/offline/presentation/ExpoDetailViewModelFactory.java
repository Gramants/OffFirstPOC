package com.example.offline.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.preferences.PersistentStorageProxy;

public class ExpoDetailViewModelFactory implements ViewModelProvider.Factory {

    private final GetFromLocalUseCase getFromLocalUseCase;
    private final PersistentStorageProxy persistentStorageProxy;


    public ExpoDetailViewModelFactory(GetFromLocalUseCase getFromLocalUseCase,
                                      PersistentStorageProxy persistentStorageProxy) {
        this.getFromLocalUseCase = getFromLocalUseCase;
        this.persistentStorageProxy = persistentStorageProxy;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ExpoDetailViewModel.class)) {
            return (T) new ExpoDetailViewModel(getFromLocalUseCase, persistentStorageProxy);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
