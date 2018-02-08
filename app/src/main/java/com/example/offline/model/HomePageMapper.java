package com.example.offline.model;

import io.reactivex.functions.Function;


public class HomePageMapper implements Function<HomePageRaw, HomePage> {
    @Override
    public HomePage apply(HomePageRaw homePageRaw) throws Exception {
        HomePage homePageSanitized = new HomePage(1, homePageRaw.getCattosync());
        return homePageSanitized;
    }
}
