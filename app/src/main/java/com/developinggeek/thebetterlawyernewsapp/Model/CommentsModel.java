package com.developinggeek.thebetterlawyernewsapp.Model;

/**
 * Created by DELL-PC on 8/29/2017.
 */

public class CommentsModel
{

    private String comment;
    private String image;
    private String name;

    public CommentsModel() {}

    public CommentsModel(String comment, String image, String name) {
        this.comment = comment;
        this.image = image;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
