package com.mvvm.dagger.coroutines.sample.places

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.data.places.PlacesRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PlacesViewModel @Inject constructor(application: Application,private val placesRepository: PlacesRepository): BaseViewModel(application) {

    val places = MutableLiveData<Event<List<Place>>>()

    fun getPlaces() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = placesRepository.getPlaces(getApplication())

            withContext(Dispatchers.Main) {

                try {
                    val response = request.await()
                    places.value = Event.success(response)
                } catch (e: Exception) {
                    places.value = e.getEventError()
                }

            }
        }
    }

}