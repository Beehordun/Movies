package com.example.biodun.mdb.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Biodun on 5/16/2017.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.example.biodun.mdb";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String PATH_MOVIES = "movies";





    public static final class MoviesEntry implements BaseColumns{

        public static final Uri MOVIE_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_DATE = "date";
        public static final String COLUMN_MOVIE_RATING = "rating";
        public static final String COLUMN_MOVIE_POSTER = "poster";

    }


}
