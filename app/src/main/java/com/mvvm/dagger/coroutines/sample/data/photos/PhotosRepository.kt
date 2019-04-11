package com.mvvm.dagger.coroutines.sample.data.photos

import androidx.annotation.VisibleForTesting
import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PhotosRepository
@Inject
constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IPhotosDataSource,
            @Named(BaseRepositoryModule.REMOTE) private val  remoteDataSource: IPhotosDataSource) : IPhotosDataSource {


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var hasCache = false

    override suspend fun getPhotosAsync(): Deferred<List<Photo>> {
        return if (hasCache) {
            localDataSource.getPhotosAsync()
        } else {
            remoteDataSource.getPhotosAsync().also { savePhotosAsync(it.await()) }
        }
    }

    override suspend fun savePhotosAsync(photos: List<Photo>) {
        localDataSource.savePhotosAsync(photos).also { hasCache = true }
    }

    fun invalidateCache() {
        hasCache = false
    }
}