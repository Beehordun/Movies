package com.example.biodun.mdb.Data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Biodun on 5/13/2017.
 */

public class TrailerData implements Parcelable {

    private String id;

    private String key;

    private String name;

    public TrailerData(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
    }

    protected TrailerData(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<TrailerData> CREATOR = new Parcelable.Creator<TrailerData>() {
        @Override
        public TrailerData createFromParcel(Parcel source) {
            return new TrailerData(source);
        }

        @Override
        public TrailerData[] newArray(int size) {
            return new TrailerData[size];
        }
    };
}
