package com.example.biodun.mdb.Data;

/**
 * Created by Biodun on 5/14/2017.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class MovieData implements Parcelable {
    @SerializedName("original_title")
    private String title;
    @SerializedName("poster_path")
    private String imageUrl;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private double ratings;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private int id;

  public MovieData(){

  }

    public MovieData(String title, String imageUrl, String overview, double ratings, String releaseDate,int ids) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.overview = overview;
        this.ratings = ratings;
        this.releaseDate = releaseDate;
        this.id=ids;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int ids) {
        this.id = ids;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.imageUrl);
        dest.writeString(this.overview);
        dest.writeDouble(this.ratings);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.id);
    }

    protected MovieData(Parcel in) {
        this.title = in.readString();
        this.imageUrl = in.readString();
        this.overview = in.readString();
        this.ratings = in.readDouble();
        this.releaseDate = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
