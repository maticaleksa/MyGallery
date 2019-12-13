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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkImage that = (NetworkImage) o;

        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = comment != null ? comment.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
