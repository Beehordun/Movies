package com.example.biodun.mdb.Data.FakeDataSources

import com.example.biodun.mdb.Data.DataSources.ILocalDataSource
import com.example.biodun.mdb.Data.model.Movie

class FakeLocalDataSource (private var movies: MutableList<Movie>): ILocalDataSource {

    override suspend fun getFavoriteMoviesFromDb(): List<Movie> = movies

    override suspend fun isMovieInDb(movieId: Int): Boolean {
        for (movie in movies) {
            if (movie.id == movieId) return true
        }
        return false
    }

    override suspend fun insertFavoriteMovieIntoDb(movie: Movie) {
        movies.add(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        movies.remove(movie)
    }
}
