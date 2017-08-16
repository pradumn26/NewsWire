package com.developinggeek.thebetterlawyernewsapp.Model;

import android.support.v4.os.ParcelableCompat;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pradumn on 14-Aug-17.
 */

public class Categories implements Serializable{
    @SerializedName("title")
    private String title;
    @SerializedName("id")
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
