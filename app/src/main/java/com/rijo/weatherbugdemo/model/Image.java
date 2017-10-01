package com.rijo.weatherbugdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable{

    public static final String KEY="com.rijo.weatherbugdemo.model.Image";
    private String title;
    private String description;
    private String filename;

    protected Image(Parcel in) {
        title = in.readString();
        description = in.readString();
        filename = in.readString();
    }
    //for testing activity as isolated in espresso
    public Image (String title,String description,String filename) {
        this.title = title;
        this.description = description;
        this.filename = filename;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(filename);
    }
}