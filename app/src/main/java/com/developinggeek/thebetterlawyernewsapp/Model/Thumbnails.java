package com.developinggeek.thebetterlawyernewsapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pradumn on 23-Aug-17.
 */

public class Thumbnails {
    @SerializedName("medium_large")
    private MediumLarge mediumLarge;

    public MediumLarge getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    public class MediumLarge {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
