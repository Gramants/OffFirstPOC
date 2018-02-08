package com.example.offline.data;


import com.example.offline.domain.ActionOnLocalRepository;
import com.example.offline.model.Event;
import com.example.offline.model.HomePage;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;


public class ActionOnLocalDataStore implements ActionOnLocalRepository {

    private final EventsDao eventsDao;

    public ActionOnLocalDataStore(EventsDao eventsDao) {
        this.eventsDao = eventsDao;
    }



    @Override
    public Flowable<Event> getEventsById(int eventId) {
        return eventsDao.getEventsById(eventId);
    }

    @Override
    public Flowable<List<Event>> getEventsByCatId(int catId) {
        return eventsDao.getEventsByCat(catId);
    }

    @Override
    public Flowable<HomePage> getHomePage() {
        return eventsDao.getHomePage().distinctUntilChanged();
    }

    @Override
    public Completable updateEventsByCat(List<Event> events, int catId) {
        return Completable.fromAction(() -> eventsDao.addEventsByCat(events));
    }

    @Override
    public Completable updateHomePage(HomePage homePage) {
        return Completable.fromAction(() -> eventsDao.addHomePage(homePage));
    }

}