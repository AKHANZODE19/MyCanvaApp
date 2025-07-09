package com.example.mycanvaapp.models;

public class LogosItem {
    private int image; // Resource ID for the logo image
    private String name; // Name or title for the logo

    // Constructor accepting both image and name
    public LogosItem(int image, String name) {
        this.image = image; // Store the image resource ID
        this.name = name;   // Store the name
    }

    public int getImage() {
        return image; // Return the image resource ID as int
    }

    public String getName() {
        return name; // Return the name
    }
}
