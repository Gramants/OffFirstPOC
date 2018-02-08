package com.example.offline.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HomePageRaw {


    @SerializedName("catToShow")
    @Expose
    public String cattosync;

    @SerializedName("lastTimeStamp")
    @Expose
    public int timestamp;

    @SerializedName("lastDate")
    @Expose
    public String lastdate;

    @SerializedName("needToSync")
    @Expose
    public Boolean needsync;


    public String getCattosync() {
        return cattosync;
    }

    public void setCattosync(String cattosync) {
        this.cattosync = cattosync;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public Boolean getNeedsync() {
        return needsync;
    }

    public void setNeedsync(Boolean needsync) {
        this.needsync = needsync;
    }

    public HomePageRaw(String cattosync, int timestamp, String lastdate, Boolean needsync) {
        this.cattosync = cattosync;
        this.timestamp = timestamp;
        this.lastdate = lastdate;
        this.needsync = needsync;
    }


}