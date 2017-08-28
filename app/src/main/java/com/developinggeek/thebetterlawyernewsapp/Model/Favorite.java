package com.developinggeek.thebetterlawyernewsapp.Model;



public class Favorite {

    private String Headline;

    public Favorite() {
    }

    public Favorite(String headline, String content, String liked, String bitmap) {
        Headline = headline;
        Content = content;
        Liked = liked;
        Bitmap = bitmap;
    }

    private String Content;
    private String Liked;

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLiked() {
        return Liked;
    }

    public void setLiked(String liked) {
        Liked = liked;
    }

    public String getBitmap() {
        return Bitmap;
    }

    public void setBitmap(String bitmap) {
        Bitmap = bitmap;
    }

    private String Bitmap;
}
