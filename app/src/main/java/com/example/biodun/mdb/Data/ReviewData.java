package com.example.biodun.mdb.Data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Biodun on 5/12/2017.
 */

public class ReviewData implements Parcelable {

    private String author;

    private String content;

    public ReviewData(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);
    }

    protected ReviewData(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<ReviewData> CREATOR = new Parcelable.Creator<ReviewData>() {
        @Override
        public ReviewData createFromParcel(Parcel source) {
            return new ReviewData(source);
        }

        @Override
        public ReviewData[] newArray(int size) {
            return new ReviewData[size];
        }
    };
}
