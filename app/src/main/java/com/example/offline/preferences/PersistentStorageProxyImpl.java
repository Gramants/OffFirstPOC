package com.example.offline.preferences;


import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;


public class PersistentStorageProxyImpl implements PersistentStorageProxy {
    private static final String INDEX_PK_CAT = "INDEX_PK_CAT";
    private static final String LAST_UPDATE = "LAST_UPDATE";
    private static final String NEED_TO_SYNC = "NEED_TO_SYNC";


    private SharedPreferences mPreferences;

    public PersistentStorageProxyImpl(SharedPreferences sharedPreferences) {
        this.mPreferences = sharedPreferences;
    }


    @Override
    public HashMap<Integer, Integer> getIndexCatMap() {
        Gson gson = new Gson();
        String json = mPreferences.getString(INDEX_PK_CAT, "{1:0}");
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer, Integer>>() {
        }.getType();
        HashMap<Integer, Integer> obj = gson.fromJson(json, type);
        return obj;
    }

    @Override
    public void saveIndexCatMap(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(INDEX_PK_CAT, json);
        editor.apply();
    }

    @Override
    public void saveSyncedTimeStamp(int timestamp) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(LAST_UPDATE, timestamp);
        editor.apply();
    }

    @Override
    public int getLastSyncedTimeStamp() {
        return mPreferences.getInt(LAST_UPDATE, 0);
    }

    @Override
    public Boolean getNeedToSync() {
        return mPreferences.getBoolean(NEED_TO_SYNC, true);
    }

    @Override
    public void setNeedToSync(Boolean needToSync) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(NEED_TO_SYNC, needToSync);
        editor.apply();
    }

}
