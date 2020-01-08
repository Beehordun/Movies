package com.example.biodun.mdb.Data

import com.example.biodun.mdb.Data.DataSources.ILocalDataSource
import com.example.biodun.mdb.Data.DataSources.IRemoteDataSource
import com.example.biodun.mdb.Data.FakeDataSources.FakeLocalDataSource
import com.example.biodun.mdb.Data.FakeDataSources.FakeRemoteDataSource
import com.example.biodun.mdb.Data.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AppRepositoryTest {

    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var localDataSource: ILocalDataSource
    private lateinit var appRepository: AppRepository


    private var movies = mutableListOf(Movie(title = "BodyGuard", id = 1),
                                Movie(title = "TideLander", id = 2),
                                Movie(title = "American Sniper", id = 3),
                                Movie(title = "London has fallen", id = 4))

    private var movieResults = MovieResults(movies)

    private var trailerResults = TrailerResults(listOf(Trailer(name = "BodyGuard", index = 1),
                                                       Trailer(name = "TideLander", index = 2),
                                                       Trailer(name = "American Sniper", index = 3),
                                                       Trailer(name = "London has fallen", index = 4)))

    private var reviewResults = ReviewResults(listOf(Review(author = "Morgan Freeman", id = "1"),
                                                     Review(author = "Tyler Perry", id = "2"),
                                                     Review(author = "Beyonce", id = "3"),
                                                     Review(author = "Kim Kardashia", id = "4")))


    @Before
    fun createRepository() {
        remoteDataSource = FakeRemoteDataSource(movieResults, trailerResults, reviewResults)
        localDataSource = FakeLocalDataSource(movies.toMutableList())
        appRepository = AppRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun fetchMovies_returnsMoviesFromRemoteDataSource() = runBlockingTest{
        val movies = appRepository.fetchMovies("", "")
        assertThat(movies, IsEqual(movieResults))
    }

    @Test
    fun fetchTrailers_returnsTrailersFromRemoteDataSource() = runBlockingTest{
        val trailers = appRepository.fetchTrailers("", "")
        assertThat(trailers, IsEqual(trailerResults))
    }

    @Test
    fun fetchReviews_returnsReviewsFromRemoteDataSource() = runBlockingTest{
        val reviews = appRepository.fetchReviews("", "")
        assertThat(reviews, IsEqual(reviewResults))
    }

    @Test
    fun getFavoriteMoviesFromDb_returnsLocalMovieList() = runBlockingTest{
        val movies = appRepository.getFavoriteMoviesFromDb()
        assertThat(movies, IsEqual(movies))
    }

    @Test
    fun isMovieInDb_whenNotInDb_returnsFalse() = runBlockingTest{
        val isMoviePresent = appRepository.isMovieInDb(movieId = 0)
        assertFalse(isMoviePresent)
    }

    @Test
    fun isMovieInDb_whenInDb_returnsTrue() = runBlockingTest{
        val isMoviePresent = appRepository.isMovieInDb(movieId = 1)
        assertTrue(isMoviePresent)
    }

    @Test
    fun insertFavoriteMovieInDb_shouldAddMovie() = runBlockingTest {
        val movie = Movie(title = "Test Movie", id = 1234)
        movies.add(movie)

        appRepository.insertFavoriteMovieIntoDb(movie)

        assertEquals(movies, appRepository.getFavoriteMoviesFromDb())
    }

    @Test
    fun deleteFavoriteMovie_shouldRemoveMovie() = runBlockingTest {
        val movie = Movie(title = "London has fallen", id = 4)
        movies.remove(movie)

        appRepository.deleteFavoriteMovie(movie)

        assertEquals(movies, appRepository.getFavoriteMoviesFromDb())
    }
}
