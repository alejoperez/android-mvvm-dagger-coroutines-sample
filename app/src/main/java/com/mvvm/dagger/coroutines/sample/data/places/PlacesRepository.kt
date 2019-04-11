package com.mvvm.dagger.coroutines.sample.data.places

import androidx.annotation.VisibleForTesting
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var hasCache = false

    override suspend fun getPlacesAsync(): Deferred<List<Place>> {
        return if (hasCache) {
            localDataSource.getPlacesAsync()
        } else {
            remoteDataSource.getPlacesAsync().also { savePlacesAsync(it.await()) }
        }
    }

    override suspend fun savePlacesAsync(places: List<Place>) {
        localDataSource.savePlacesAsync(places).also { hasCache = true }
    }

    fun invalidateCache() {
        hasCache = false
    }
}