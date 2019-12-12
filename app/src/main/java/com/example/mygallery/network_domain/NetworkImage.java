package com.example.mygallery.network_domain;

import com.google.gson.annotations.SerializedName;

public class NetworkImage {

    @SerializedName("comment")
    public final String comment;
    @SerializedName("picture")
    public final String imageUrl;
    @SerializedName("publishedAt")
    public final String date;
    @SerializedName("title")
    public final String title;

    public NetworkImage(String comment, String imageUrl, String date, String title) {
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.date = date;
        this.title = title;
    }
}
