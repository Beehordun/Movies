package com.example.biodun.mdb.Data.DataSources

import com.example.biodun.mdb.Data.model.MovieResults
import com.example.biodun.mdb.Data.model.ReviewResults
import com.example.biodun.mdb.Data.model.TrailerResults

interface IRemoteDataSource {
    suspend fun fetchMovies(preference: String, apiKey: String): MovieResults

    suspend fun fetchTrailers(id: String, apiKey: String): TrailerResults

    suspend fun fetchReviews(id: String, apiKey: String): ReviewResults
}