package com.mvvm.dagger.coroutines.sample.photos

import androidx.fragment.app.testing.launchFragmentInContainer
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Photo
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotosFragmentTest: BaseTest() {

    companion object {
        const val MSG_FRAGMENT_SHOULD_ACTIVE = "fragment should be Active"
        const val MSG_PHOTOS_SHOULD_NOT_EMPTY = "Photos shouldn't be empty"
    }

    @Test
    fun onPhotosResponseTest() {

        val scenario = launchFragmentInContainer<PhotosFragment>(themeResId= R.style.AppTheme)
        scenario.onFragment {
            val fragment = spy(it)

            val photos = listOf(Photo(1,"","","",""))

            fragment.onPhotosResponse(Event.success(photos))

            verify(fragment, times(1)).onPhotosSuccess(photos)

            fragment.onPhotosResponse(Event.failure())
            verify(fragment, times(1)).onPhotosFailure()

            fragment.onPhotosResponse(Event.networkError())
            verify(fragment, times(1)).onNetworkError()
        }
    }


    @Test
    fun onPlacesSuccessTest() {
        val scenario = launchFragmentInContainer<PhotosFragment>(themeResId= R.style.AppTheme)
        scenario.onFragment {
            val fragment = spy(it)

            val photos = listOf(Photo(1,"","","",""))

            fragment.onPhotosSuccess(photos)
            val adapter = fragment.dataBinding.rvPhotos.adapter as PhotosAdapter
            Assert.assertEquals(MSG_PHOTOS_SHOULD_NOT_EMPTY,1, adapter.itemCount)
        }
    }

    @Test
    fun onPlacesFailureTest() {
        val scenario = launchFragmentInContainer<PhotosFragment>(themeResId= R.style.AppTheme)
        scenario.onFragment {
            it.onPhotosFailure()
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_ACTIVE,it.isActive())
        }
    }

    @Test
    fun onNetworkErrorTest() {
        val scenario = launchFragmentInContainer<PhotosFragment>(themeResId= R.style.AppTheme)
        scenario.onFragment {
            it.onNetworkError()
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_ACTIVE,it.isActive())
        }
    }
}