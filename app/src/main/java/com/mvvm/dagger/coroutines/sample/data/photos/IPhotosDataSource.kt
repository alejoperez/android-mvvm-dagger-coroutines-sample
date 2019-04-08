package com.mvvm.dagger.coroutines.sample.data.photos

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import kotlinx.coroutines.Deferred

interface IPhotosDataSource {

    suspend fun getPhotosAsync(context: Context): Deferred<List<Photo>>

    suspend fun savePhotosAsync(context: Context, photos: List<Photo>)

}