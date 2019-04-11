package com.mvvm.dagger.coroutines.sample.login

import androidx.test.core.app.ActivityScenario
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginActivityTest : BaseTest() {

    companion object {
        const val ERROR_ACTIVITY_SHOULD_NOT_ACTIVE = "Activity shouldn't be active"
        const val ERROR_ACTIVITY_SHOULD_BE_ACTIVE = "Activity should be active"
    }

    @Test
    fun onLoginResponseTest() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity {
            val activity = spy(it)

            activity.onLoginResponse(Event.success(mock()))
            verify(activity, times(1)).onLoginSuccess()

            activity.onLoginResponse(Event.failure())
            verify(activity, times(1)).onLoginFailure()

            activity.onLoginResponse(Event.networkError())
            verify(activity, times(1)).onNetworkError()
        }
    }

    @Test
    fun onLoginSuccessTest() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity {
            it.onLoginSuccess()
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun onLoginFailureTest() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity {
            it.onLoginFailure()
            Assert.assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE,it.isActive())

        }
    }

    @Test
    fun onNetworkErrorTest() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity {
            it.onNetworkError()
            Assert.assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE,it.isActive())
        }
    }
}