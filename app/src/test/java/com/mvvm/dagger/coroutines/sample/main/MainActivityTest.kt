package com.mvvm.dagger.coroutines.sample.main

import androidx.test.core.app.ActivityScenario
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.photos.PhotosFragment
import com.mvvm.dagger.coroutines.sample.places.PlacesFragment
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityTest : BaseTest() {

    companion object {
        const val MSG_FRAGMENT_SHOULD_BE_PLACES = "Fragment should be PlacesFragment"
        const val MSG_FRAGMENT_SHOULD_BE_PHOTOS = "Fragment should be PhotosFragment"
        const val MSG_ACTIVITY_SHOULD_NOT_BE_ACTIVE = "Activity should not be active"
    }

    @Test
    fun onUserResponseTest() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            val spyActivity = spy(it)
            val user = User(1, "alejo", "alejo@gmail.com")

            spyActivity.onUserResponse(Event.success(user))
            verify(spyActivity, times(1)).setUser(user)

            spyActivity.onUserResponse(Event.failure())
            verify(spyActivity, times(1)).showAlert(R.string.error_user)
        }
    }

    @Test
    fun onMenuItemClickedTest() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            val activity = spy(it)
            activity.onMenuItemClicked(R.id.nav_places)
            var fragment = activity.getCurrentFragment(R.id.main_content_view)
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_BE_PLACES, fragment is PlacesFragment)

            activity.onMenuItemClicked(R.id.nav_photos)
            fragment = activity.getCurrentFragment(R.id.main_content_view)
            Assert.assertTrue(MSG_FRAGMENT_SHOULD_BE_PHOTOS, fragment is PhotosFragment)

            activity.onMenuItemClicked(R.id.nav_logout)
            verify(activity, times(1)).logout()
        }
    }

    @Test
    fun logoutTest() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.onActivity {
            it.logout()
            Assert.assertFalse(MSG_ACTIVITY_SHOULD_NOT_BE_ACTIVE, it.isActive())
        }
    }
}