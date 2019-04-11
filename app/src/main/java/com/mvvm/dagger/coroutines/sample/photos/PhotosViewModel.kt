package com.mvvm.dagger.coroutines.sample.photos

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.data.photos.PhotosRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotosViewModel @Inject constructor(private val photosRepository: PhotosRepository): BaseViewModel() {

    val isLoading = ObservableBoolean(false)

    val photos = MutableLiveData<Event<List<Photo>>>()

    fun getPhotos() {

        viewModelScope.launch(contextProvider.Main) {

            try {
                showProgress()

                val response = withContext(contextProvider.IO) { photosRepository.getPhotosAsync().await() }

                photos.value = Event.success(response)

            } catch (t: Throwable) {
                photos.value = t.getEventError()
            } finally {
                hideProgress()
            }

        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun showProgress() = isLoading.set(true)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun hideProgress() = isLoading.set(false)

}