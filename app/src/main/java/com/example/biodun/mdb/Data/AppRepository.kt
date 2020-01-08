package com.example.biodun.mdb.Data

import com.example.biodun.mdb.Data.DataSources.ILocalDataSource
import com.example.biodun.mdb.Data.DataSources.IRemoteDataSource
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.Data.model.MovieResults
import com.example.biodun.mdb.Data.model.ReviewResults
import com.example.biodun.mdb.Data.model.TrailerResults
import javax.inject.Inject

class AppRepository @Inject constructor(private val localDataSource: ILocalDataSource,
                                        private val remoteDataSource: IRemoteDataSource) {
    suspend fun fetchMovies(preference: String, apiKey: String): MovieResults
            = remoteDataSource.fetchMovies(preference, apiKey)

    suspend fun fetchTrailers(id: String, apiKey: String): TrailerResults
            = remoteDataSource.fetchTrailers(id, apiKey)

    suspend fun fetchReviews(id: String, apiKey: String): ReviewResults
            = remoteDataSource.fetchReviews(id, apiKey)

    suspend fun getFavoriteMoviesFromDb(): List<Movie> = localDataSource.getFavoriteMoviesFromDb()

    suspend fun isMovieInDb(movieId: Int): Boolean = localDataSource.isMovieInDb(movieId)

    suspend fun insertFavoriteMovieIntoDb(movie: Movie) {
        localDataSource.insertFavoriteMovieIntoDb(movie)
    }

    suspend fun  deleteFavoriteMovie(movie: Movie) {
       localDataSource.deleteFavoriteMovie(movie)
    }
}
