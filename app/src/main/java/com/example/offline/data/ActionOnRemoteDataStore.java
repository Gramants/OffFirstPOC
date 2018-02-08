package com.example.offline.data;

import com.example.offline.domain.ActionOnRemoteRepository;
import com.example.offline.domain.services.jobs.JobManagerFactory;
import com.example.offline.domain.services.jobs.SyncEventsByCatJob;
import com.example.offline.domain.services.jobs.SyncHomePageJob;


import io.reactivex.Completable;

public class ActionOnRemoteDataStore implements ActionOnRemoteRepository {


    @Override
    public Completable getEventsFromRemotebyId(int catId, int fromPk) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new SyncEventsByCatJob(catId, fromPk)));
    }

    @Override
    public Completable getHomePageFromRemote(int savedTimestamp) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new SyncHomePageJob(savedTimestamp)));
    }


}
