package com.example.biodun.mdb.Data.FakeDataSources

import com.example.biodun.mdb.Data.DataSources.IRemoteDataSource
import com.example.biodun.mdb.Data.model.MovieResults
import com.example.biodun.mdb.Data.model.ReviewResults
import com.example.biodun.mdb.Data.model.TrailerResults

class FakeRemoteDataSource(private val movieResults: MovieResults,
                           private val trailerResults: TrailerResults,
                           private val reviewResults: ReviewResults): IRemoteDataSource {
    override suspend fun fetchMovies(preference: String, apiKey: String): MovieResults = movieResults

    override suspend fun fetchTrailers(id: String, apiKey: String): TrailerResults = trailerResults

    override suspend fun fetchReviews(id: String, apiKey: String): ReviewResults = reviewResults
}
