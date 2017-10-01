package com.rijo.weatherbugdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rijogeorge on 9/30/17.
 */

public class Images implements Parcelable {
    public static final String KEY="com.rijo.weatherbugdemo.model.Images";
    private List<Image> Images=new ArrayList<>();

    public Images(List<Image> images) {
        Images = images;
    }

    protected Images(Parcel in) {
        Images = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public List<Image> getImages() {
        return Images;
    }

    public void setImages(List<Image> images) {
        Images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(Images);
    }
}
