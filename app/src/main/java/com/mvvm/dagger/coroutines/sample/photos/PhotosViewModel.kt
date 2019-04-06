package com.mvvm.dagger.coroutines.sample.photos

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.photos.PhotosRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class PhotosViewModel @Inject constructor(application: Application, private val photosRepository: PhotosRepository): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    val photos = MutableLiveData<Event<List<Photo>>>()

    fun getPhotos() {
        showProgress()
        CoroutineScope(Dispatchers.IO).launch {
            val request = photosRepository.getPhotos(getApplication())
            withContext(Dispatchers.Main) {
                hideProgress()
                try {
                    val response = request.await()
                    photos.value = Event.success(response)
                } catch (e: Exception) {
                    photos.value = e.getEventError()
                }
            }
        }
    }

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)

}