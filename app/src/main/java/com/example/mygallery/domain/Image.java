package com.example.mygallery.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class Image implements Parcelable {

    public final File file;
    public final String title;
    public final String date;

    public Image(File file, String title, String date) {
        this.file = file;
        this.title = title;
        this.date = date;
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
    }

    protected Image(Parcel in) {
        this.file = (File) in.readSerializable();
        this.title = in.readString();
        this.date = in.readString();
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
}
