package com.example.mycanvaapp.models;

public class InstagramPostsItem {
    private int image; // This holds the resource ID of the image
    private String caption; // This holds the caption for the Instagram post

    // Constructor that accepts both image resource ID and caption
    public InstagramPostsItem(int image, String caption) {
        this.image = image; // Initialize the image resource ID
        this.caption = caption; // Initialize the caption
    }

    // Getter for the image resource ID
    public int getImage() {
        return image;
    }

    // Getter for the caption
    public String getCaption() {
        return caption;
    }
}
