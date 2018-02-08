package com.example.offline.domain;


import io.reactivex.Completable;

public interface ActionOnRemoteRepository {
    Completable getEventsFromRemotebyId(int catId, int fromPk);

    Completable getHomePageFromRemote(int savedTimestamp);
}
