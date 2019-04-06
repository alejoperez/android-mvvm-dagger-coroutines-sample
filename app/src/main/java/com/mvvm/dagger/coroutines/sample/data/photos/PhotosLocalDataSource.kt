package com.mvvm.dagger.coroutines.sample.data.photos

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.room.PhotoDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosLocalDataSource @Inject constructor(private val photoDao: PhotoDao) : IPhotosDataSource {

    override suspend fun savePhotos(context: Context, photos: List<Photo>) = photoDao.savePhotos(photos)

    override suspend fun getPhotos(context: Context): Deferred<List<Photo>> = CoroutineScope(Dispatchers.IO).async {
        photoDao.getPhotos()
    }

}