package com.example.offline.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.offline.domain.GetFromLocalUseCase;
import com.example.offline.domain.GetFromRemoteUseCase;
import com.example.offline.preferences.PersistentStorageProxy;

public class ExpoViewModelFactory implements ViewModelProvider.Factory {

    private final GetFromLocalUseCase getFromLocalUseCase;
    private final GetFromRemoteUseCase getFromRemoteUseCase;
    private final PersistentStorageProxy persistentStorageProxy;


    public ExpoViewModelFactory(GetFromLocalUseCase getFromLocalUseCase,
                         GetFromRemoteUseCase getFromRemoteUseCase,
                         PersistentStorageProxy persistentStorageProxy) {
        this.getFromLocalUseCase = getFromLocalUseCase;
        this.getFromRemoteUseCase = getFromRemoteUseCase;
        this.persistentStorageProxy=persistentStorageProxy;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ExpoViewModel.class)) {
            return (T) new ExpoViewModel(getFromLocalUseCase, getFromRemoteUseCase,persistentStorageProxy);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
