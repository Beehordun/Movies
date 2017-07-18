package com.example.biodun.mdb.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Biodun on 5/16/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";


    private static final int VERSION = 1;


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE = "CREATE TABLE "  + MovieContract.MoviesEntry.TABLE_NAME + " (" +
                MovieContract.MoviesEntry._ID                + " INTEGER PRIMARY KEY, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_RATING + " REAL NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE + " STRING NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_DATE+ " STRING NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_POSTER+ " STRING NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
