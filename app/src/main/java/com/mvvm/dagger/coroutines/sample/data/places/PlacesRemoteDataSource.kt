package com.mvvm.dagger.coroutines.sample.data.places

import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import kotlinx.coroutines.Deferred
import java.lang.UnsupportedOperationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesRemoteDataSource @Inject constructor(private val api: IApi)  : IPlacesDataSource {

    override suspend fun savePlacesAsync(places: List<Place>) = throw UnsupportedOperationException()

    override suspend fun getPlacesAsync(): Deferred<List<Place>> = api.getPlacesAsync()

}