package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.room.PhotoDao
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosLocalDataSource @Inject constructor(private val photoDao: PhotoDao) : IPhotosDataSource {

    override suspend fun savePhotosAsync(photos: List<Photo>) = withContext(Dispatchers.IO) { photoDao.savePhotos(photos) }

    override suspend fun getPhotosAsync(): Deferred<List<Photo>> = CoroutineScope(Dispatchers.IO).async { photoDao.getPhotos() }

}