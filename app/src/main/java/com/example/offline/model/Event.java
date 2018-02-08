package com.example.offline.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity
public class Event implements Serializable {

    @Expose
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @Expose
    @SerializedName("titolo")
    @ColumnInfo(name = "titolo")
    private String titolo;

    @Expose
    @SerializedName("data_fiera")
    @ColumnInfo(name = "data_fiera")
    private String dataFiera;

    @Expose
    @SerializedName("location")
    @ColumnInfo(name = "location")
    private String location;

    @Expose
    @SerializedName("foto")
    @ColumnInfo(name = "foto")
    private String foto;

    @Expose
    @SerializedName("descrizione")
    @ColumnInfo(name = "descrizione")
    private String descrizione;

    @Expose
    @SerializedName("timestamp")
    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @Expose
    @SerializedName("cat")
    @ColumnInfo(name = "cat")
    private int cat;


    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDataFiera() {
        return dataFiera;
    }

    public void setDataFiera(String dataFiera) {
        this.dataFiera = dataFiera;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    public Event(int id, String titolo, String dataFiera, String location, String foto, String descrizione, long timestamp, int cat, boolean syncPending) {
        this.id = id;
        this.titolo = titolo;
        this.dataFiera = dataFiera;
        this.location = location;
        this.foto = foto;
        this.descrizione = descrizione;
        this.timestamp = timestamp;
        this.cat = cat;
        this.syncPending = syncPending;
    }




    @Override
    public String toString() {
        return String.format("Event id: %s, titolo: %s, syncPending: %s", id, titolo, syncPending);
    }
}
