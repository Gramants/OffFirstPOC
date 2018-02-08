package com.example.offline.model;


public class Category {

    public Category(int catId, String catName, int imageId) {
        this.catId = catId;
        this.catName = catName;
        this.imageId = imageId;
    }

    public void setCatId(int catId) {

        this.catId = catId;
    }


    public int getCatId() {
        return catId;
    }

    public String getCatName() {
        return catName;
    }

    public int getImageId() {
        return imageId;
    }

    private int catId;
    private String catName;
    private int imageId;

}
