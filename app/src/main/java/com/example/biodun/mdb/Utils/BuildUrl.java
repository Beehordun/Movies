package com.example.biodun.mdb.Utils;

import android.net.Uri;

import com.example.biodun.mdb.BuildConfig;



/**
 * Created by Biodun on 5/13/2017.
 */

public class BuildUrl {


    private static final String BASE_URL="http://api.themoviedb.org/3/movie/";
    private final static String DEFAULT_PREFERENCE="popular";
    private static  String preference=DEFAULT_PREFERENCE;
    private static  final String API_KEY= BuildConfig.API_KEY;
    private static final String KEY="?api_key=";
    private static final String VIDEO_IMAGE_URL="http://img.youtube.com/vi/";

    public static String getMovieUrl(String moviePreference){
        preference=moviePreference;
        try {

            StringBuilder url = new StringBuilder(BASE_URL);
            url.append(preference)
                    .append(KEY)
                    .append(API_KEY);
            return url.toString();
        }catch(NullPointerException e){

            return null;

        }



        }


    public static String getReviewUrl(int id){
        String newId=Integer.toString(id);

        try {

            StringBuilder url = new StringBuilder(BASE_URL);
            url.append(newId)
                    .append("/reviews")
                    .append(KEY)
                    .append(API_KEY);
            return url.toString();
        }catch(NullPointerException e){

            return null;

        }
    }



    public static String getTrailerUrl(int id){
        String newId=Integer.toString(id);
        try {

            StringBuilder url = new StringBuilder(BASE_URL);
            url.append(newId)
                    .append("/videos")
                    .append(KEY)
                    .append(API_KEY);
            return url.toString();
        }catch(NullPointerException e){

            return null;

        }
    }


    public static String getTrailerImage(String key){

        try{
            StringBuilder url=new StringBuilder(VIDEO_IMAGE_URL);
            url.append(key)
            .append("/1.jpg");
            return url.toString();
        }catch(NullPointerException e){
            return null;
        }




    }


    }



