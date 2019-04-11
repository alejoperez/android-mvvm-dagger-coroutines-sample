package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class PlacesRepositoryTest: BaseTest() {

    private val places = emptyList<Place>()

    private val localDataSource = mock<PlacesLocalDataSource>()
    private val remoteDataSource = mock<PlacesRemoteDataSource>()


    private fun resetInteractions() {
        reset(localDataSource, remoteDataSource)
    }

    @Test
    fun getPlacesAsyncCacheTest() = runBlocking {
        val repository = PlacesRepository(localDataSource, remoteDataSource)
        repository.hasCache = true
        repository.getPlacesAsync()

        verifyZeroInteractions(remoteDataSource)
        verify(localDataSource, times(1)).getPlacesAsync()
        resetInteractions()
    }

    @Test
    fun getPlacesAsyncRemoteTest() = runBlocking {

        whenever(remoteDataSource.getPlacesAsync()).thenReturn(places.toDeferred())

        val repository = PlacesRepository(localDataSource, remoteDataSource)
        repository.hasCache = false
        repository.getPlacesAsync()

        verify(remoteDataSource, times(1)).getPlacesAsync()
        verify(localDataSource, times(1)).savePlacesAsync(places)
        resetInteractions()
    }

    @Test
    fun savePlacesAsyncTest() = runBlocking {
        val repository = PlacesRepository(localDataSource, remoteDataSource)
        repository.hasCache = true
        repository.savePlacesAsync(places)

        verifyZeroInteractions(remoteDataSource)
        verify(localDataSource, times(1)).savePlacesAsync(places)
        resetInteractions()
    }
}