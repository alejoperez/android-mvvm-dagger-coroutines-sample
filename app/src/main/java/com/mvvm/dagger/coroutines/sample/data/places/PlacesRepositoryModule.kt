package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class PlacesRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPlacesRepository(repository: PlacesRepository): IPlacesDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.LOCAL)
    abstract fun bindPlacesLocalDataSource(localDataSource: PlacesLocalDataSource): IPlacesDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.REMOTE)
    abstract fun bindPlacesRemoteDataSource(remoteDataSource: PlacesRemoteDataSource): IPlacesDataSource
}