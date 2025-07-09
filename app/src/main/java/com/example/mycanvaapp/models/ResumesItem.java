package com.example.mycanvaapp.models;

public class ResumesItem {
    private int image; // Change this to int if it's a resource ID
    private String name; // Optional: If you want to store a name

    // Constructor
    public ResumesItem(int image, String name) {
        this.image = image; // Store the image resource ID
        this.name = name;   // Store the name if needed
    }

    // Method to return the image resource ID
    public int getImage() {
        return image; // Correctly returns an int
    }

    // Optional: Getter for name
    public String getName() {
        return name; // Returns the name
    }
}
