package com.example.biodun.mdb.di

import android.app.Application
import com.example.biodun.mdb.Data.DataSources.ILocalDataSource
import com.example.biodun.mdb.Data.DataSources.IRemoteDataSource
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.features.Movie.MovieActivity
import com.example.biodun.mdb.features.movieDetail.MovieDetailActivity
import com.example.biodun.mdb.features.movieDetail.OverviewFragment
import com.example.biodun.mdb.features.movieDetail.ReviewFragment
import com.example.biodun.mdb.features.movieDetail.TrailerFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, DataSourceModule::class])
interface AppComponent {
    fun inject(activity: MovieActivity)
    fun inject(application: MdbApplication)
    fun inject(activity: MovieDetailActivity)
    fun inject(fragment: OverviewFragment)
    fun inject(fragment: ReviewFragment)
    fun inject(fragment: TrailerFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
    }
}
