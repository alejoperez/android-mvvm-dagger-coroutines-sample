package com.mvvm.dagger.coroutines.sample.data.places

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.data.room.PlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesLocalDataSource @Inject constructor(private val placesDao: PlaceDao) : IPlacesDataSource {

    override suspend fun savePlaces(context: Context, places: List<Place>) = placesDao.savePlaces(places)

    override suspend fun getPlaces(context: Context): Deferred<List<Place>> = CoroutineScope(Dispatchers.IO).async {
        placesDao.getPlaces()
    }

}