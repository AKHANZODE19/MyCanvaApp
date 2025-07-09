package com.example.mycanvaapp.models;

public class DocsItem {
    private int image; // Assuming this is an image resource ID
    private String name; // Add a name field

    // Updated constructor to accept both image and name
    public DocsItem(int image, String name) {
        this.image = image; // Initialize image
        this.name = name;   // Initialize name
    }

    public int getImage() {
        return image; // Getter for image
    }

    public String getName() {
        return name; // Getter for name
    }
}
