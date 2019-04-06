package com.mvvm.dagger.coroutines.sample.data.photos

import android.content.Context
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


    private var hasCache = false

    override suspend fun getPhotos(context: Context): Deferred<List<Photo>> {
        return if (hasCache) {
            localDataSource.getPhotos(context)
        } else {
            remoteDataSource.getPhotos(context).also {
                savePhotos(context,it.await())
            }
        }
    }

    override suspend fun savePhotos(context: Context,photos: List<Photo>) {
        localDataSource.savePhotos(context, photos)
        hasCache = true
    }

    fun invalidateCache() {
        hasCache = false
    }
}