package com.example.biodun.mdb.Data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biodun.mdb.Constants.EMPTY_STRING
import com.example.biodun.mdb.Constants.MOVIE_TABLE
import com.google.gson.annotations.SerializedName
/**
 * Created by Biodun on 5/14/2017.
 */

@Entity(tableName = MOVIE_TABLE)
data class Movie(@SerializedName("original_title")
                     var title: String = EMPTY_STRING,
                 @SerializedName("poster_path")
                     var imageUrl: String = EMPTY_STRING,
                 @SerializedName("overview")
                     var overview: String = EMPTY_STRING,
                 @SerializedName("vote_average")
                     var ratings: Double = 0.0,
                 @SerializedName("release_date")
                     var releaseDate: String = EMPTY_STRING,
                 @PrimaryKey
                 @SerializedName("id")
                     var id: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readDouble(),
            parcel.readString()!!,
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(overview)
        parcel.writeDouble(ratings)
        parcel.writeString(releaseDate)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}
