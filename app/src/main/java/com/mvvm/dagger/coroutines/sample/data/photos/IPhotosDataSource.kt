package com.mvvm.dagger.coroutines.sample.data.photos

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import kotlinx.coroutines.Deferred

interface IPhotosDataSource {

    suspend fun getPhotos(context: Context): Deferred<List<Photo>>

    suspend fun savePhotos(context: Context, photos: List<Photo>)

}