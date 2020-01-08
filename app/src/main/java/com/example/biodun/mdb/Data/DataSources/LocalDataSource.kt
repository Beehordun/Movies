package com.example.biodun.mdb.Data.DataSources

import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.db.AppDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appDatabase: AppDatabase) : ILocalDataSource {

    override suspend fun getFavoriteMoviesFromDb(): List<Movie> = appDatabase.movieDao().getFavoriteMovies()

    override suspend fun isMovieInDb(movieId: Int): Boolean = appDatabase.movieDao().getMovieCount(movieId) != null

    override suspend fun insertFavoriteMovieIntoDb(movie: Movie) {
        appDatabase.movieDao().insertFavoriteMovie(movie)
    }

    override suspend fun  deleteFavoriteMovie(movie: Movie) {
        appDatabase.movieDao().deleteFavoriteMovie(movie)
    }
}
