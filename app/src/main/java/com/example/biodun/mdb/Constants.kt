package com.example.biodun.mdb

object Constants {
    const val MOVIE_TABLE = "MovieTable"
    const val REVIEW_TABLE = "ReviewTable"
    const val TRAILER_TABLE = "TrailerTable"
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "MdbDatabase"
    const val EMPTY_STRING = ""
}

enum class MoviePreference(val preference: String) {
    TOP_RATED("top_rated"), POPULAR("popular")
}
