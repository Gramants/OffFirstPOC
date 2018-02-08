package com.example.offline.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class HomePage {


    @PrimaryKey
    public int id;

    public String catToShow;

    public HomePage(int id, String catToShow) {
        this.id = id;
        this.catToShow = catToShow;
    }
}