package com.example.biodun.mdb.di

import androidx.lifecycle.ViewModel
import com.example.biodun.mdb.features.Movie.MovieViewModel
import com.example.biodun.mdb.features.movieDetail.MovieDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    internal abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindMovieDetailViewModel(movieViewModel: MovieDetailViewModel): ViewModel
}
