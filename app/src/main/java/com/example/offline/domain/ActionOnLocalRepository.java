package com.example.offline.domain;

import com.example.offline.model.Event;
import com.example.offline.model.HomePage;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;


public interface ActionOnLocalRepository {


    Flowable<Event> getEventsById(int eventId);

    Flowable<List<Event>> getEventsByCatId(int catId);

    Flowable<HomePage> getHomePage();

    Completable updateEventsByCat(List<Event> events, int catId);

    Completable updateHomePage(HomePage homePage);

}
