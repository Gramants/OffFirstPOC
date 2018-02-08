package com.example.offline.domain.services.responses;

import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.model.Event;

import java.util.List;


public class SyncEventsListByCatResponse {

    public final SyncResponseEventType eventType;
    public final List<Event> events;
    public final int catId;

    public SyncEventsListByCatResponse(SyncResponseEventType eventType, List<Event> events, int catId) {
        this.eventType = eventType;
        this.events = events;
        this.catId = catId;
    }
}
