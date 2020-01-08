package com.example.biodun.mdb.di

import android.app.Application
import androidx.room.Room
import com.example.biodun.mdb.Constants.DATABASE_NAME
import com.example.biodun.mdb.db.AppDatabase
import com.example.biodun.mdb.Network.MdbApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT = 2
private const val BASE_URL = "http://api.themoviedb.org/3/"

@Module
class AppModule {

    @Provides
    @Reusable
    internal fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT.toLong(), TimeUnit.MINUTES)
                .readTimeout(TIMEOUT.toLong(), TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT.toLong(), TimeUnit.MINUTES)
                .build()
    }

    @Provides
    @Singleton
    fun provideMdbApi(retrofit: Retrofit): MdbApi = retrofit.create(MdbApi::class.java)

    @Provides
    @Reusable
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }

    @Singleton
    @Provides
    internal fun provideRoomDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME).build()
    }
}
