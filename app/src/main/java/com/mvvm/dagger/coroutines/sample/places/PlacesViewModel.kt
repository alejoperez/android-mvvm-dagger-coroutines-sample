package com.mvvm.dagger.coroutines.sample.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.data.places.PlacesRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesViewModel @Inject constructor(private val placesRepository: PlacesRepository): BaseViewModel() {

    val places = MutableLiveData<Event<List<Place>>>()

    fun getPlaces() {
        viewModelScope.launch(contextProvider.Main) {
            try {
                val response = withContext(contextProvider.IO) { placesRepository.getPlacesAsync().await() }
                places.value = Event.success(response)
            } catch (t: Throwable) {
                places.value = t.getEventError()
            }
        }
    }

}