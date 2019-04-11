package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.data.room.Photo
import kotlinx.coroutines.Deferred

interface IPhotosDataSource {

    suspend fun getPhotosAsync(): Deferred<List<Photo>>

    suspend fun savePhotosAsync(photos: List<Photo>)

}