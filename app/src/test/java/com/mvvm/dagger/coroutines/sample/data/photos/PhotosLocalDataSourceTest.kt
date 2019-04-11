package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.room.PhotoDao
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import org.junit.Test

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class PhotosLocalDataSourceTest : BaseTest() {

    private val photos = listOf(Photo(1, "", "", "", ""))
    private val photosDao: PhotoDao = mock()

    private fun resetInteractions() = reset(photosDao)

    @Test
    fun savePhotosAsyncTest() = runBlocking {
        val localDataSource = PhotosLocalDataSource(photosDao)
        localDataSource.savePhotosAsync(photos)
        verify(photosDao, times(1)).savePhotos(photos)
        resetInteractions()
    }

    @Test
    fun getPhotosAsync() = runBlocking {
        val localDataSource = PhotosLocalDataSource(photosDao)
        localDataSource.getPhotosAsync().await()
        verify(photosDao, times(1)).getPhotos()
        resetInteractions()
    }

}