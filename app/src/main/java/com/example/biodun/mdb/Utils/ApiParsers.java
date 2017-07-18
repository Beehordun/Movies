package com.example.biodun.mdb.Utils;

import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.Data.ReviewData;
import com.example.biodun.mdb.Data.TrailerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Biodun on 5/14/2017.
 */

public class ApiParsers {

    public static ArrayList<MovieData> getMovieDataFromJson(String result)throws JSONException {
        ArrayList<MovieData> data =new ArrayList<>();

        if ((result!=null)){

            JSONObject rootObject = new JSONObject(result);
            if (rootObject.has("results")){

                JSONArray movieArray = rootObject.getJSONArray("results");

                for(int i=0; i<movieArray.length();i++){
                    JSONObject eachMovie=movieArray.getJSONObject(i);
                    data.add(new MovieData(eachMovie.getString("original_title")
                            ,eachMovie.getString("poster_path")
                            ,eachMovie.getString("overview")
                            ,eachMovie.getDouble("vote_average"),
                            eachMovie.getString("release_date"),
                            eachMovie.getInt("id")));

                }
            }
        }

        return data;
    }

    public static ArrayList<ReviewData> getReviewDataFromJson(String result)throws JSONException{
        ArrayList<ReviewData> data=new ArrayList<>();
        if (result!=null){
            JSONObject rootObject = new JSONObject(result);
            if (rootObject.has("results")){

                JSONArray reviewArray=rootObject.getJSONArray("results");
                for(int i=0;i<reviewArray.length();i++){
                    JSONObject eachReview=reviewArray.getJSONObject(i);
                    data.add(new ReviewData(eachReview.getString("author"),
                                            eachReview.getString("content")));

                }

            }



        }




        return data;
    }



    public static ArrayList<TrailerData> getTrailerDataFromJson(String result)throws JSONException{
        ArrayList<TrailerData> data=new ArrayList<>();
        if (result!=null){
            JSONObject rootObject = new JSONObject(result);
            if (rootObject.has("results")){

                JSONArray trailerArray=rootObject.getJSONArray("results");
                for(int i=0;i<trailerArray.length();i++){
                    JSONObject eachTrailer=trailerArray.getJSONObject(i);
                    data.add(new TrailerData(eachTrailer.getString("id"),
                            eachTrailer.getString("key"),
                            eachTrailer.getString("name")));

                }

            }



        }




        return data;
    }


}
