package com.example.biodun.mdb.Utils;

import android.net.Uri;

/**
 * Created by Biodun on 4/21/2017.
 */

public class ImageUtil       {

    private final static String SMALL_BASE_IMAGE_URL="http://image.tmdb.org/t/p/w185/";
    private final static String BIG_BASE_IMAGE_URL="http://image.tmdb.org/t/p/w342/";
    private static String fullUrl;

    public  static String getSmallImageUrl(String path) throws NullPointerException{

            try {
                Uri uri= Uri.parse(SMALL_BASE_IMAGE_URL).buildUpon()
                        .appendEncodedPath(path).build();
                fullUrl=uri.toString();

                return fullUrl;
            }catch(NullPointerException e){

                return null;

            }



        }
    public  static String getBigImageUrl(String path) throws NullPointerException{

        try {
            Uri uri= Uri.parse(BIG_BASE_IMAGE_URL).buildUpon()
                    .appendEncodedPath(path).build();
            fullUrl=uri.toString();

            return fullUrl;
        }catch(NullPointerException e){

            return null;

        }



    }


    }




