package com.mvvm.dagger.coroutines.sample.splash

import androidx.test.core.app.ActivityScenario
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashActivityTest: BaseTest() {

    companion object {
        const val ERROR_ACTIVITY_SHOULD_NOT_ACTIVE = "Activity shouldn't be active"
    }

    @Test
    fun goToNextScreenLoggedTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.onActivity {
            it.goToNextScreen(true)
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }

    @Test
    fun goToNextScreenNotLoggedTest() {
        val scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.onActivity {
            it.goToNextScreen(false)
            Assert.assertFalse(ERROR_ACTIVITY_SHOULD_NOT_ACTIVE,it.isActive())
        }
    }
}