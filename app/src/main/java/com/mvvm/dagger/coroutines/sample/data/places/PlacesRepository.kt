package com.mvvm.dagger.coroutines.sample.data.places

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import com.mvvm.dagger.coroutines.sample.data.room.Place
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PlacesRepository
@Inject
constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IPlacesDataSource,
            @Named(BaseRepositoryModule.REMOTE) private val remoteDataSource: IPlacesDataSource) : IPlacesDataSource {


    private var hasCache = false

    override suspend fun getPlaces(context: Context): Deferred<List<Place>> {
        return if (hasCache) {
            localDataSource.getPlaces(context)
        } else {
            remoteDataSource.getPlaces(context)
                    .also {
                        savePlaces(context,it.await())
                    }
        }
    }

    override suspend fun savePlaces(context: Context,places: List<Place>) {
        localDataSource.savePlaces(context, places)
        hasCache = true
    }

    fun invalidateCache() {
        hasCache = false
    }
}