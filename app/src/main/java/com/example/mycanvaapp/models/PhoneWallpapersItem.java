package com.example.mycanvaapp.models;

public class PhoneWallpapersItem {
    private String name;
    private int image;

    public PhoneWallpapersItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
