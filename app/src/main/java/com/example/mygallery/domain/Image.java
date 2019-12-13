package com.example.mygallery.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class Image implements Parcelable {

    public final File file;
    public final String title;
    public final String date;
    public final String comment;

    public Image(File file, String title, String date, String comment) {
        this.file = file;
        this.title = title;
        this.date = date;
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.file);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.comment);
    }

    protected Image(Parcel in) {
        this.file = (File) in.readSerializable();
        this.title = in.readString();
        this.date = in.readString();
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (file != null ? !file.equals(image.file) : image.file != null) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (date != null ? !date.equals(image.date) : image.date != null) return false;
        return comment != null ? comment.equals(image.comment) : image.comment == null;
    }

    @Override
    public int hashCode() {
        int result = file != null ? file.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
