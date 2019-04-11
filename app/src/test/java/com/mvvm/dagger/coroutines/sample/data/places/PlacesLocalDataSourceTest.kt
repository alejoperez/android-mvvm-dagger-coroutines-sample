package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.data.room.PlaceDao
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class PlacesLocalDataSourceTest: BaseTest() {

    private val places = listOf(Place(1, "", "", 0.0, 0.0))
    private val placesDao: PlaceDao = mock()

    private fun resetInteractions() = reset(placesDao)

    @Test
    fun savePlacesAsyncTest() = runBlocking {
        val localDataSource = PlacesLocalDataSource(placesDao)
        localDataSource.savePlacesAsync(places)
        verify(placesDao, times(1)).savePlaces(places)
        resetInteractions()
    }

    @Test
    fun getPlacesAsync() = runBlocking {
        val localDataSource = PlacesLocalDataSource(placesDao)
        localDataSource.getPlacesAsync().await()
        verify(placesDao, times(1)).getPlaces()
        resetInteractions()
    }


}