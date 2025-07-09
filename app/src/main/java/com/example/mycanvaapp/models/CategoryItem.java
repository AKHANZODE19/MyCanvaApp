package com.example.mycanvaapp.models;

public class CategoryItem {
    private String name;
    private int icon;

    public CategoryItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
