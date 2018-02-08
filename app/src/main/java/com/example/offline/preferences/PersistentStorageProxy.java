package com.example.offline.preferences;

import java.util.HashMap;

public interface PersistentStorageProxy {

    HashMap<Integer, Integer> getIndexCatMap();

    void saveIndexCatMap(Object obj);

    void saveSyncedTimeStamp(int timestamp);

    int getLastSyncedTimeStamp();

    Boolean getNeedToSync();

    void setNeedToSync(Boolean needToSync);
}
