package com.developinggeek.thebetterlawyernewsapp.Model;



public class Category {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Category(String name, String imageURL) {
        Name = name;
        ImageURL = imageURL;
    }

    private String ImageURL;
}
