package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.data.room.PlaceDao
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesLocalDataSource @Inject constructor(private val placesDao: PlaceDao) : IPlacesDataSource {

    override suspend fun savePlacesAsync(places: List<Place>) = withContext(Dispatchers.IO) { placesDao.savePlaces(places) }

    override suspend fun getPlacesAsync(): Deferred<List<Place>> = CoroutineScope(Dispatchers.IO).async { placesDao.getPlaces() }

}