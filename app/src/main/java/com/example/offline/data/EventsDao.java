package com.example.offline.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.offline.model.Event;
import com.example.offline.model.HomePage;
import java.util.List;
import io.reactivex.Flowable;


@Dao
public interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEventsByCat(List<Event> events);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addHomePage(HomePage homepage);

    @Query("SELECT * FROM Event where cat=:catId order by id")
    Flowable<List<Event>> getEventsByCat(int catId);

    @Query("SELECT * FROM homepage")
    Flowable<HomePage> getHomePage();

    @Query("SELECT * FROM Event where id=:eventId")
    Flowable<Event> getEventsById(int eventId);

}
