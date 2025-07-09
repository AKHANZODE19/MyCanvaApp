package com.example.mycanvaapp.models;

import java.util.List;

public class ParentItem {
    private final String title;
    private final int imageResource;  // New field for image resource ID
    private final List<CategoryItem> categoryItems;
    private final List<PostersItem> postersItems;
    private final List<ResumesItem> resumesItems;
    private final List<PhoneWallpapersItem> phoneWallpapersItems;
    private final List<DocsItem> docsItems;
    private final List<LogosItem> logosItems;
    private final List<InstagramPostsItem> instagramPostsItems;

    public ParentItem(String title, int imageResource, List<CategoryItem> categoryItems,
                      List<PostersItem> postersItems, List<ResumesItem> resumesItems,
                      List<PhoneWallpapersItem> phoneWallpapersItems,
                      List<DocsItem> docsItems, List<LogosItem> logosItems,
                      List<InstagramPostsItem> instagramPostsItems) {
        this.title = title;
        this.imageResource = imageResource;  // Assign imageResource
        this.categoryItems = categoryItems;
        this.postersItems = postersItems;
        this.resumesItems = resumesItems;
        this.phoneWallpapersItems = phoneWallpapersItems;
        this.docsItems = docsItems;
        this.logosItems = logosItems;
        this.instagramPostsItems = instagramPostsItems;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {  // New getter method for imageResource
        return imageResource;
    }

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public List<PostersItem> getPostersItems() {
        return postersItems;
    }

    public List<ResumesItem> getResumesItems() {
        return resumesItems;
    }

    public List<PhoneWallpapersItem> getPhoneWallpapersItems() {
        return phoneWallpapersItems;
    }

    public List<DocsItem> getDocsItems() {
        return docsItems;
    }

    public List<LogosItem> getLogosItems() {
        return logosItems;
    }

    public List<InstagramPostsItem> getInstagramPostsItems() {
        return instagramPostsItems;
    }
}
