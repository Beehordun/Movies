package com.example.biodun.mdb.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.biodun.mdb.MainActivity;

import static com.example.biodun.mdb.Data.MovieContract.MoviesEntry.TABLE_NAME;
/**
 * Created by Biodun on 5/16/2017.
 */

public class MovieContentProvider extends ContentProvider {


    private MovieDbHelper movieDbHelper;


    public static final int MOVIES = 100;

    public static final int MOVIES_WITH_ID = 101;



    public static UriMatcher sUriMatcher=buildUriMatcher();


    public static final UriMatcher buildUriMatcher() {


        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES,MOVIES);


        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES +"/#",MOVIES_WITH_ID);


        return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        Context context=getContext();
        movieDbHelper=new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {




        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch(match){

            case MOVIES:
                SQLiteDatabase movieDb=movieDbHelper.getReadableDatabase();
                retCursor=movieDb.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);


        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }





    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {



        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch(match){

            case MOVIES:
                SQLiteDatabase movieDb=movieDbHelper.getWritableDatabase();
                long id =movieDb.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MoviesEntry.MOVIE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {



        int match = sUriMatcher.match(uri);
        int tasksDeleted;
        switch(match){

            case MOVIES_WITH_ID:
                SQLiteDatabase movieDb=movieDbHelper.getWritableDatabase();
                String id = uri.getPathSegments().get(1);

                tasksDeleted = movieDb.delete(TABLE_NAME, "movie_id=?", new String[]{id});
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (tasksDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
            MainActivity.mAdapter.notifyDataSetChanged();
        }


        return tasksDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



}
