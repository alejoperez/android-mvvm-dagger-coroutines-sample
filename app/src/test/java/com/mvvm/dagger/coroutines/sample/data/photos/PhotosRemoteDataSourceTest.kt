package com.mvvm.dagger.coroutines.sample.data.photos

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.UnsupportedOperationException

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class PhotosRemoteDataSourceTest: BaseTest() {

    private val photos = listOf(Photo(1, "", "", "", ""))
    private val api: IApi = mock()

    private fun resetInteractions() = reset(api)

    @Test(expected = UnsupportedOperationException::class)
    fun savePhotosAsyncTest() {
        runBlocking {
            val remoteDataSource = PhotosRemoteDataSource(api)
            try {
                remoteDataSource.savePhotosAsync(photos)
            } finally {
                verifyZeroInteractions(api)
                resetInteractions()
            }
        }
    }

    @Test
    fun getPhotosAsync() {
        runBlocking {
            val remoteDataSource = PhotosRemoteDataSource(api)
            remoteDataSource.getPhotosAsync()
            verify(api, times(1)).getPhotosAsync()
            resetInteractions()
        }
    }
}