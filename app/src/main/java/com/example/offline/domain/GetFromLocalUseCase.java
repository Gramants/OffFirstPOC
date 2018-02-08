package com.example.offline.domain;

import com.example.offline.model.Event;
import com.example.offline.model.HomePage;

import java.util.List;

import io.reactivex.Flowable;


public class GetFromLocalUseCase {
    private final ActionOnLocalRepository actionOnLocalRepository;


    public GetFromLocalUseCase(ActionOnLocalRepository actionOnLocalRepository) {
        this.actionOnLocalRepository = actionOnLocalRepository;
    }

    public Flowable<List<Event>> getEventsByCatId(int catId) {
        return actionOnLocalRepository.getEventsByCatId(catId);
    }

    public Flowable<HomePage> getHomePage() {
        return actionOnLocalRepository.getHomePage();
    }

    public Flowable<Event> getEventById(int eventId) {
        return actionOnLocalRepository.getEventsById(eventId);
    }
}

