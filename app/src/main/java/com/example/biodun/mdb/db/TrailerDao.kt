package com.example.biodun.mdb.db

import androidx.room.Dao
import androidx.room.Query
import com.example.biodun.mdb.Constants.TRAILER_TABLE
import com.example.biodun.mdb.Data.model.Review

@Dao
interface TrailerDao {
    @Query("SELECT * FROM $TRAILER_TABLE")
    fun getReviews(): List<Review>
}
