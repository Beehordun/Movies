package com.example.biodun.mdb.Network

import com.example.biodun.mdb.Data.model.MovieResults
import com.example.biodun.mdb.Data.model.ReviewResults
import com.example.biodun.mdb.Data.model.TrailerResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MdbApi {

    @GET("movie/{preference}")
    suspend fun getMovies(@Path("preference") preference: String,
                          @Query("api_key") apiKey: String): MovieResults

    @GET("movie/{id}/reviews")
    suspend fun getMovieReview(@Path("id") id: String,
                               @Query("api_key") apiKey: String): ReviewResults

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailer(@Path("id") id: String,
                                @Query("api_key") apiKey: String): TrailerResults
}
