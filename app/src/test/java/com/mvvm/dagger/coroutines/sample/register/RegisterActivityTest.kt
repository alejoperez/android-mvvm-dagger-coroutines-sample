package com.mvvm.dagger.coroutines.sample.register

import androidx.test.core.app.ActivityScenario
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterActivityTes : BaseTest() {

    companion object {
        const val ERROR_ACTIVITY_SHOULD_NOT_ACTIVE = "Activity shouldn't be active"
        const val ERROR_ACTIVITY_SHOULD_BE_ACTIVE = "Activity should be active"
    }

    @Test
    fun onRegisterResponseTest() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity {
            val activity = spy(it)

            activity.onRegisterResponse(Event.success(mock()))
            verify(activity, times(1)).onRegisterSuccess()

            activity.onRegisterResponse(Event.failure())
            verify(activity, times(1)).onRegisterFailure()

            activity.onRegisterResponse(Event.networkError())
            verify(activity, times(1)).onNetworkError()
        }
    }

    @Test
    fun onRegisterSuccessTest() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity {
            it.onRegisterSuccess()
            assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE, it.isActive())
        }
    }

    @Test
    fun onRegisterFailureTest() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity {
            it.onRegisterFailure()
            assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE, it.isActive())
        }
    }

    @Test
    fun onNetworkErrorTest() {
        val scenario = ActivityScenario.launch(RegisterActivity::class.java)
        scenario.onActivity {
            it.onNetworkError()
            assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE, it.isActive())
        }
    }

}