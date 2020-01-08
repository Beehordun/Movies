package com.example.biodun.mdb.di

import com.example.biodun.mdb.Data.DataSources.ILocalDataSource
import com.example.biodun.mdb.Data.DataSources.IRemoteDataSource
import com.example.biodun.mdb.Data.DataSources.LocalDataSource
import com.example.biodun.mdb.Data.DataSources.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceModule {

    @Binds
    internal abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    internal abstract fun bindLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource
}