package com.example.offline.model;

import com.example.offline.R;

import java.util.HashMap;

public class ModelConstants {
    public static final String DB_NAME = "offlinedb";
    public static final int KEY_TRANSITION_TIME = 150;


    public static HashMap<Integer, Integer> getLogo() {
        HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
        hm.put(1, R.drawable.sagre);
        hm.put(2, R.drawable.mercatini);
        hm.put(3, R.drawable.militaria);
        hm.put(4, R.drawable.giocattolo);
        hm.put(5, R.drawable.minerali);
        hm.put(6, R.drawable.auto);
        hm.put(7, R.drawable.gioco);
        hm.put(8, R.drawable.fumetto);
        hm.put(9, R.drawable.antiquariato);
        hm.put(10, R.drawable.disco);
        hm.put(11, R.drawable.estivi);
        hm.put(12, R.drawable.modellismo);
        hm.put(13, R.drawable.elettronica);
        hm.put(14, R.drawable.campionaria);
        hm.put(15, R.drawable.artigianato);
        hm.put(16, R.drawable.streetfood);
        hm.put(18, R.drawable.verde);
        hm.put(19, R.drawable.autoexpo);
        return hm;
    }

    public static HashMap<Integer, String> getLabel() {
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "cat 1");
        hm.put(2, "cat 2");
        hm.put(3, "cat 3");
        hm.put(4, "cat 4");
        hm.put(5, "cat 5");
        hm.put(6, "cat 6");
        hm.put(7, "cat 7");
        hm.put(8, "cat 8");
        hm.put(9, "cat 9");
        hm.put(10, "cat 10");
        hm.put(11, "cat 11");
        hm.put(12, "cat 12");
        hm.put(13, "cat 13");
        hm.put(14, "cat 14");
        hm.put(15, "cat 15");
        hm.put(16, "cat 16");
        hm.put(18, "cat 17");
        hm.put(19, "cat 18");
        return hm;
    }


}
