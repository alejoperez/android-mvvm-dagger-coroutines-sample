package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class PhotosRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPhotosRepository(repository: PhotosRepository): IPhotosDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.LOCAL)
    abstract fun bindPhotosLocalDataSource(localDataSource: PhotosLocalDataSource): IPhotosDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.REMOTE)
    abstract fun bindPhotosRemoteDataSource(remoteDataSource: PhotosRemoteDataSource): IPhotosDataSource
}