package com.example.offline.domain;


import io.reactivex.Completable;


public class GetFromRemoteUseCase {
    private final ActionOnRemoteRepository actionOnRemoteRepository;

    public GetFromRemoteUseCase(ActionOnRemoteRepository actionOnRemoteRepository) {
        this.actionOnRemoteRepository = actionOnRemoteRepository;
    }

    public Completable syncEventsByCat(int catId, int fromPk) {
        return actionOnRemoteRepository.getEventsFromRemotebyId(catId, fromPk);
    }

    public Completable syncHomePage(int savedTimestamp) {
        return actionOnRemoteRepository.getHomePageFromRemote(savedTimestamp);
    }
}
