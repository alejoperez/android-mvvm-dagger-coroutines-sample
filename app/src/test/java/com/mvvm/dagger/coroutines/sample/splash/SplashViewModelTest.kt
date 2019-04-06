package com.mvvm.dagger.coroutines.sample.splash

import com.mvvm.dagger.coroutines.sample.application.SampleApp
import com.mvvm.dagger.coroutines.sample.base.BaseViewModelTest
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.livedata.Status
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.junit.Assert
import org.junit.Test

class SplashViewModelTest: BaseViewModelTest() {

    private val application = mock<SampleApp>()


    @Test
    fun userLoggedEventTrueTest() {
        val userRepository = mock<UserRepository> {
            on { isLoggedIn(application) } doReturn Single.just(true)
        }

        val viewModel = SplashViewModel(application,userRepository)

        viewModel.isUserLoggedIn()

        val liveDataValue = viewModel.isUserLoggedEvent.value

        Assert.assertNotNull("Live Data value shouldn't be null",liveDataValue)
        Assert.assertEquals("Status should be SUCCESS",Status.SUCCESS, liveDataValue?.status)
        Assert.assertTrue("User should be logged in", liveDataValue?.peekData() ?: false)
    }

    @Test
    fun userLoggedEventFalseTest() {
        val userRepository = mock<UserRepository> {
            on { isLoggedIn(application) } doReturn Single.just(false)
        }

        val viewModel = SplashViewModel(application,userRepository)

        viewModel.isUserLoggedIn()

        val liveDataValue = viewModel.isUserLoggedEvent.value

        Assert.assertNotNull("Live Data value shouldn't be null",liveDataValue)
        Assert.assertEquals("Status should be SUCCESS", Status.SUCCESS, liveDataValue?.status)
        Assert.assertFalse("User shouldn't be logged in", liveDataValue?.peekData() ?: true)
    }

    @Test
    fun userLoggedEventFailureTest() {
        val userRepository = mock<UserRepository> {
            on { isLoggedIn(application) } doReturn Single.error(Exception())
        }

        val viewModel = SplashViewModel(application,userRepository)

        viewModel.isUserLoggedIn()

        val liveDataValue = viewModel.isUserLoggedEvent.value

        Assert.assertNotNull("Live Data value shouldn't be null",liveDataValue)
        Assert.assertEquals("Status should be FAILURE",Status.FAILURE, liveDataValue?.status)
        Assert.assertNull("Value should be null",liveDataValue?.peekData())
    }

}