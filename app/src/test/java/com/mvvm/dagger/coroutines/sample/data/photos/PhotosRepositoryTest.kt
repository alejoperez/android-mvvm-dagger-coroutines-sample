package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class PhotosRepositoryTest: BaseTest() {

    private val photos = emptyList<Photo>()

    private val localDataSource = mock<PhotosLocalDataSource>()
    private val remoteDataSource = mock<PhotosRemoteDataSource>()


    private fun resetInteractions() {
        reset(localDataSource, remoteDataSource)
    }

    @Test
    fun getPhotosAsyncCacheTest() = runBlocking {
        val repository = PhotosRepository(localDataSource, remoteDataSource)
        repository.hasCache = true
        repository.getPhotosAsync()

        verifyZeroInteractions(remoteDataSource)
        verify(localDataSource, times(1)).getPhotosAsync()
        resetInteractions()
    }

    @Test
    fun getPhotosAsyncRemoteTest() = runBlocking {

        whenever(remoteDataSource.getPhotosAsync()).thenReturn(photos.toDeferred())

        val repository = PhotosRepository(localDataSource, remoteDataSource)
        repository.hasCache = false
        repository.getPhotosAsync()

        verify(remoteDataSource, times(1)).getPhotosAsync()
        verify(localDataSource, times(1)).savePhotosAsync(photos)
        resetInteractions()
    }

    @Test
    fun savePhotosAsyncTest() = runBlocking {
        val repository = PhotosRepository(localDataSource, remoteDataSource)
        repository.hasCache = true
        repository.savePhotosAsync(photos)

        verifyZeroInteractions(remoteDataSource)
        verify(localDataSource, times(1)).savePhotosAsync(photos)
        resetInteractions()
    }

}