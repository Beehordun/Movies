package com.example.biodun.mdb.features.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biodun.mdb.Data.*
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.Data.model.Review
import com.example.biodun.mdb.Data.model.Trailer
import kotlinx.coroutines.*
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private lateinit var movie: Movie

    private var _reviews = MutableLiveData<List<Review>>()
    var reviewsLiveData: LiveData<List<Review>> = _reviews

    private var _trailers = MutableLiveData<List<Trailer>>()
    var trailersLiveData: LiveData<List<Trailer>> = _trailers

    private var _toastMessage = MutableLiveData<String>()
    var toastMessage: LiveData<String> = _toastMessage

    fun setSelectedMovie(selectedMovie: Movie) {
        movie = selectedMovie
    }

    fun getSelectedMovie() = movie

    fun getMovieReviews(id: String, apiKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val reviews = appRepository.fetchReviews(id, apiKey)
                _reviews.postValue(reviews.results)
            }
        }
        //TODO handle request errors
    }

    fun getMovieTrailers(id: String, apiKey: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val trailers = appRepository.fetchTrailers(id, apiKey)
                _trailers.postValue(trailers.results)
            }
        }
    }

    fun isMoviePresentInDb(movieId: Int): Boolean {
        var isMoviePresent = false
        runBlocking {
            withContext(Dispatchers.IO) {
                isMoviePresent = appRepository.isMovieInDb(movieId)
            }
        }
        return isMoviePresent
    }

    fun deleteFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.deleteFavoriteMovie(movie)
                _toastMessage.postValue("Movie has been removed")
            }
        }
    }

    fun insertFavorite(movie: Movie) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appRepository.insertFavoriteMovieIntoDb(movie)
                _toastMessage.postValue("Movie has been added")
            }
        }
    }
}
