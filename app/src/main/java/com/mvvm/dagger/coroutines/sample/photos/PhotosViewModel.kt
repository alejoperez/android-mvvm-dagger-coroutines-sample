package com.mvvm.dagger.coroutines.sample.photos

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.photos.PhotosRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosViewModel @Inject constructor(application: Application, private val photosRepository: PhotosRepository): BaseViewModel(application) {

    val isLoading = ObservableBoolean(false)

    val photos = MutableLiveData<Event<List<Photo>>>()

    fun getPhotos() {

        viewModelScope.launch {

            try {
                showProgress()

                val response = withContext(Dispatchers.IO) { photosRepository.getPhotosAsync(getApplication()).await() }

                photos.value = Event.success(response)

            } catch (t: Throwable) {
                photos.value = t.getEventError()
            } finally {
                hideProgress()
            }

        }
    }

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)

}