package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.UnsupportedOperationException

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class PlacesRemoteDataSourceTest : BaseTest() {

    private val places = listOf(Place(1, "", "", 0.0, 0.0))
    private val api: IApi = mock()

    private fun resetInteractions() = reset(api)

    @Test(expected = UnsupportedOperationException::class)
    fun savePlacesAsyncTest() {
        runBlocking {
            val remoteDataSource = PlacesRemoteDataSource(api)
            try {
                remoteDataSource.savePlacesAsync(places)
            } finally {
                verifyZeroInteractions(api)
                resetInteractions()
            }
        }
    }

    @Test
    fun getPlacesAsync() {
        runBlocking {
            val remoteDataSource = PlacesRemoteDataSource(api)
            remoteDataSource.getPlacesAsync()
            verify(api, times(1)).getPlacesAsync()
            resetInteractions()
        }
    }

}