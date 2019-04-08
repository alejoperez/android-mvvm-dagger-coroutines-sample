package com.mvvm.dagger.coroutines.sample.data.places

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.Place
import kotlinx.coroutines.Deferred

interface IPlacesDataSource {

    suspend fun getPlacesAsync(context: Context): Deferred<List<Place>>

    suspend fun savePlacesAsync(context: Context, places: List<Place>)
}