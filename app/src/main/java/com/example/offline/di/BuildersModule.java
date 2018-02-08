package com.example.offline.di;

import com.example.offline.view.ExpoActivity;
import com.example.offline.view.ExpoDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Binds all sub-components within the app.
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = ExpoActivityModule.class)
    abstract ExpoActivity bindCommentsActivity();

    @ContributesAndroidInjector(modules = ExpoDetailModule.class)
    abstract ExpoDetailActivity bindExpoDetailActivity();
}
