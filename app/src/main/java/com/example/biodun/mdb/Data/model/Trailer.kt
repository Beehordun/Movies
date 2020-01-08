package com.example.biodun.mdb.Data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.biodun.mdb.Constants.EMPTY_STRING
import com.example.biodun.mdb.Constants.TRAILER_TABLE

/**
 * Created by Biodun on 5/13/2017.
 */

@Entity(tableName = TRAILER_TABLE)
data class Trailer(var id: String? = null,
                   var key: String = EMPTY_STRING,
                   var name: String? = null,
                   @PrimaryKey(autoGenerate = true)
                   var index: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Trailer> {
        override fun createFromParcel(parcel: Parcel): Trailer {
            return Trailer(parcel)
        }

        override fun newArray(size: Int): Array<Trailer?> {
            return arrayOfNulls(size)
        }
    }
}
