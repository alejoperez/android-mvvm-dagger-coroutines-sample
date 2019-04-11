package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import kotlinx.coroutines.Deferred
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRemoteDataSource @Inject constructor(private val api: IApi) : IPhotosDataSource {

    override suspend fun savePhotosAsync(photos: List<Photo>) = throw UnsupportedOperationException()

    override suspend fun getPhotosAsync(): Deferred<List<Photo>> = api.getPhotosAsync()

}