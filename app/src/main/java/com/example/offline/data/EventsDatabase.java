package com.example.offline.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.offline.model.Event;
import com.example.offline.model.HomePage;
import com.example.offline.model.ModelConstants;

@Database(entities = {Event.class, HomePage.class}, version = 1, exportSchema = false)
public abstract class EventsDatabase extends RoomDatabase {
    private static EventsDatabase instance;

    public static synchronized EventsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), EventsDatabase.class, ModelConstants.DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract EventsDao commentDao();
}
