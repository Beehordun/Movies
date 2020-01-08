package com.example.biodun.mdb.Data.DataSources

import com.example.biodun.mdb.Data.model.Movie

interface ILocalDataSource {
    suspend fun getFavoriteMoviesFromDb(): List<Movie>

    suspend fun isMovieInDb(movieId: Int): Boolean

    suspend fun insertFavoriteMovieIntoDb(movie: Movie)

    suspend fun  deleteFavoriteMovie(movie: Movie)
}