package com.example.biodun.mdb.features.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biodun.mdb.Data.AppRepository
import com.example.biodun.mdb.Data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieViewModel @Inject constructor (private val appRepository: AppRepository): ViewModel() {

    private var _movies = MutableLiveData<List<Movie>>()
    var moviesLiveData: LiveData<List<Movie>> = _movies

    fun getMovies(moviePreference: String, apiKey: String) {
        viewModelScope.launch {
           withContext(Dispatchers.IO) {
               val movies = appRepository.fetchMovies(moviePreference, apiKey)
               _movies.postValue(movies.results)
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val movies = appRepository.getFavoriteMoviesFromDb()
                _movies.postValue(movies)
            }
        }
    }
}
