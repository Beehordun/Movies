package com.example.biodun.mdb.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.biodun.mdb.Constants.MOVIE_TABLE
import com.example.biodun.mdb.Data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM $MOVIE_TABLE")
    suspend fun getFavoriteMovies(): List<Movie>

    @Query("SELECT title FROM $MOVIE_TABLE WHERE id = :movieId")
    suspend fun getMovieCount(movieId: Int): String?

    @Insert
    suspend fun insertFavoriteMovie(movie: Movie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: Movie)
}
