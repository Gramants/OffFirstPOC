package com.example.offline.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.offline.App;
import com.example.offline.data.EventsDao;
import com.example.offline.data.EventsDatabase;
import com.example.offline.data.ActionOnLocalDataStore;
import com.example.offline.data.ActionOnRemoteDataStore;
import com.example.offline.domain.ActionOnLocalRepository;
import com.example.offline.domain.ActionOnRemoteRepository;
import com.example.offline.domain.services.jobs.GcmJobService;
import com.example.offline.domain.services.jobs.SchedulerJobService;
import com.example.offline.model.HomePageMapper;
import com.example.offline.preferences.PersistentStorageProxy;
import com.example.offline.preferences.PersistentStorageProxyImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SchedulerJobService provideSchedulerJobService() {
        return new SchedulerJobService();
    }

    @Singleton
    @Provides
    GcmJobService provideGcmJobService() {
        return new GcmJobService();
    }

    @Singleton
    @Provides
    EventsDao provideCommentDao(Context context) {
        return EventsDatabase.getInstance(context).commentDao();
    }

    @Singleton
    @Provides
    ActionOnLocalRepository provideLocalCommentRepository(EventsDao eventsDao) {
        return new ActionOnLocalDataStore(eventsDao);
    }

    @Singleton
    @Provides
    ActionOnRemoteRepository provideRemoteCommentRepository() {
        return new ActionOnRemoteDataStore();
    }

    @Singleton
    @Provides
    HomePageMapper provideHomePageMapper() {
        return new HomePageMapper();
    }

    @Singleton
    @Provides
    SharedPreferences provideDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    PersistentStorageProxy providePersistentStorageProxy(SharedPreferences preferences) {
        return new PersistentStorageProxyImpl(preferences);
    }
}
