package com.example.offline.domain;

import com.example.offline.model.Event;
import com.example.offline.model.HomePage;

import java.util.List;

import io.reactivex.Completable;

public class UpdateToLocalUseCase {
    private final ActionOnLocalRepository actionOnLocalRepository;

    public UpdateToLocalUseCase(ActionOnLocalRepository actionOnLocalRepository) {
        this.actionOnLocalRepository = actionOnLocalRepository;
    }


    public Completable addEventsByCat(List<Event> events, int catId) {
        return actionOnLocalRepository.updateEventsByCat(events, catId);
    }

    public Completable addHomePage(HomePage homePage) {
        return actionOnLocalRepository.updateHomePage(homePage);
    }
}
