package com.mvvm.dagger.coroutines.sample.splash

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest: BaseTest() {

    @Test
    fun userLoggedEventTrueTest() {
        val userRepository = mock<UserRepository> {
            on { isLoggedIn() } doReturn true
        }

        val viewModel = SplashViewModel(userRepository)

        Assert.assertTrue("user should be logged in",viewModel.isUserLoggedIn())
    }

    @Test
    fun userLoggedEventFalseTest() {
        val userRepository = mock<UserRepository> {
            on { isLoggedIn() } doReturn false
        }

        val viewModel = SplashViewModel(userRepository)

        Assert.assertFalse("user shouldn't be logged in",viewModel.isUserLoggedIn())

    }

}