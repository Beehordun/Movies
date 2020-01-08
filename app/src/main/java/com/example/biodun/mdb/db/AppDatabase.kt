package com.example.biodun.mdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.biodun.mdb.Constants.DATABASE_VERSION
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.Data.model.Review
import com.example.biodun.mdb.Data.model.Trailer

@Database(entities = [Movie::class, Review::class, Trailer::class], version = DATABASE_VERSION)
abstract class AppDatabase: RoomDatabase() {

    abstract fun reviewDao(): ReviewDao

    abstract fun trailerDao(): TrailerDao

    abstract fun movieDao(): MovieDao
}
