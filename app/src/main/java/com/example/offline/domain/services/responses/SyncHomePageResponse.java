package com.example.offline.domain.services.responses;

import com.example.offline.domain.services.SyncResponseEventType;
import com.example.offline.model.HomePageRaw;


public class SyncHomePageResponse {

    public final SyncResponseEventType eventType;
    public final HomePageRaw homePage;


    public SyncHomePageResponse(SyncResponseEventType eventType, HomePageRaw homepage) {
        this.eventType = eventType;
        this.homePage = homepage;

    }
}
