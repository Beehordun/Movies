package com.example.biodun.mdb.Data.DataSources

import com.example.biodun.mdb.Data.model.MovieResults
import com.example.biodun.mdb.Data.model.ReviewResults
import com.example.biodun.mdb.Data.model.TrailerResults
import com.example.biodun.mdb.Network.MdbApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val mdbApi: MdbApi) : IRemoteDataSource {

    override suspend fun fetchMovies(preference: String, apiKey: String): MovieResults
            = mdbApi.getMovies(preference, apiKey)

    override suspend fun fetchTrailers(id: String, apiKey: String): TrailerResults
            = mdbApi.getMovieTrailer(id, apiKey)

    override suspend fun fetchReviews(id: String, apiKey: String): ReviewResults
            = mdbApi.getMovieReview(id, apiKey)

}