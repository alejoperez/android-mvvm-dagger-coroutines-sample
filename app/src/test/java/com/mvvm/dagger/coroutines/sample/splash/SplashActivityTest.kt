package com.mvvm.dagger.coroutines.sample.splash

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import com.mvvm.dagger.coroutines.sample.base.BaseViewTest
import com.mvvm.dagger.coroutines.sample.livedata.Event
import org.junit.Assert
import org.junit.Test

class SplashActivityTest: BaseViewTest() {

    companion object {
        const val ERROR_ACTIVITY_SHOULD_NOT_ACTIVE = "Activity shouldn't be active"
        const val ERROR_ACTIVITY_SHOULD_BE_ACTIVE = "Activity should be active"
    }

    @Test
    fun userLoggedTrueTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.onUserLoggedResponse(Event.success(true))
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun userLoggedFalseTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.onUserLoggedResponse(Event.success(false))
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun userLoggedResponseFailureTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.onUserLoggedResponse(Event.failure())
            Assert.assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE,it.isActive())
        }
    }

    @Test
    fun goToNextScreenTrueTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.goToNextScreen(true)
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun goToNextScreenFalseTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.goToNextScreen(false)
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun goToNextScreenNullTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity {
            it.goToNextScreen(null)
            Assert.assertTrue(ERROR_ACTIVITY_SHOULD_BE_ACTIVE,it.isActive())
        }
    }
}