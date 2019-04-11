package com.mvvm.dagger.coroutines.sample.places

import androidx.fragment.app.testing.launchFragmentInContainer
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class PlacesFragmentTest: BaseTest() {

    companion object {
        const val MSG_FRAGMENT_SHOULD_ACTIVE = "fragment should be Active"
        const val MSG_PLACES_SHOULD_NOT_EMPTY = "Places shouldn't be empty"
    }

    @Test
    fun onPlacesResponseTest() {

        val scenario = launchFragmentInContainer<PlacesFragment>(themeResId=R.style.AppTheme)
        scenario.onFragment {
            val fragment = spy(it)
            fragment.googleMap = mock()

            val places = listOf(Place(1,"","",0.0,0.0))

            try {
                fragment.onPlacesResponse(Event.success(places))
            } catch (t: Throwable) {
            }

            verify(fragment, times(1)).onPlacesSuccess(places)

            fragment.onPlacesResponse(Event.failure())
            verify(fragment, times(1)).onPlacesFailure()

            fragment.onPlacesResponse(Event.networkError())
            verify(fragment, times(1)).onNetworkError()
        }
    }


    @Test
    fun onPlacesSuccessTest() {
        val scenario = launchFragmentInContainer<PlacesFragment>(themeResId=R.style.AppTheme)
        scenario.onFragment {
            val fragment = spy(it)
            fragment.googleMap = mock()

            val places = listOf(Place(1,"","",0.0,0.0))

            try {
                fragment.onPlacesSuccess(places)
            } catch (t: Throwable) {

            }
            Assert.assertEquals(MSG_PLACES_SHOULD_NOT_EMPTY,1,fragment.currentPlaces.size)
            verify(fragment, times(1)).loadPlacesInMap()
            verify(fragment, times(1)).randomPlace()
        }
    }

    @Test
    fun onPlacesFailureTest() {
        val scenario = launchFragmentInContainer<PlacesFragment>(themeResId=R.style.AppTheme)
        scenario.onFragment {
            it.onPlacesFailure()
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_ACTIVE,it.isActive())
        }
    }

    @Test
    fun onNetworkErrorTest() {
        val scenario = launchFragmentInContainer<PlacesFragment>(themeResId=R.style.AppTheme)
        scenario.onFragment {
            it.onNetworkError()
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_ACTIVE,it.isActive())
        }
    }

}
