package com.example.biodun.mdb.db

import androidx.room.Dao
import androidx.room.Query
import com.example.biodun.mdb.Constants.REVIEW_TABLE
import com.example.biodun.mdb.Data.model.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM $REVIEW_TABLE")
    fun getReviews(): List<Review>
}
