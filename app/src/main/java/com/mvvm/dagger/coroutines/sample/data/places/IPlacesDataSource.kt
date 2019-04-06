package com.mvvm.dagger.coroutines.sample.data.places

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Place
import kotlinx.coroutines.Deferred

interface IPlacesDataSource {

    suspend fun getPlaces(context: Context): Deferred<List<Place>>

    suspend fun savePlaces(context: Context, places: List<Place>)
}