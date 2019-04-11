package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.room.UserDao
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class UserLocalDataSourceTest : BaseTest() {

    private val user: User = mock()
    private val loginRequest: LoginRequest = mock()
    private val registerRequest: RegisterRequest = mock()
    private val userDao: UserDao = mock()
    private val preferenceManager: PreferenceManager = mock {
        whenever(it.findPreference(PreferenceManager.ACCESS_TOKEN, "")).thenReturn("")
        doNothing().whenever(it).putPreference(ArgumentMatchers.anyString(), eq(Any()))
    }

    private fun resetInteractions() = reset(userDao, preferenceManager)

    @Test
    fun getUserAsyncTest() = runBlocking {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.getUserAsync().await()
        verify(userDao, times(1)).getUser()
        resetInteractions()
    }

    @Test
    fun saveUserAsyncTest() = runBlocking {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.saveUserAsync(user)
        verify(userDao, times(1)).saveUser(user)
        resetInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun loginAsyncTest() = runBlocking {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.loginAsync(loginRequest)
        resetInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun registerAsyncTest() = runBlocking {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.registerAsync(registerRequest)
        resetInteractions()
    }

    @Test
    fun isLoggedInTest() {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.isLoggedIn()
        verify(preferenceManager, times(1)).findPreference(PreferenceManager.ACCESS_TOKEN, "")
        resetInteractions()
    }

    @Test
    fun logoutTest() {
        val localDataSource = UserLocalDataSource(userDao, preferenceManager)
        localDataSource.logout()
        verify(preferenceManager, times(1)).putPreference(PreferenceManager.ACCESS_TOKEN, "")
        resetInteractions()
    }

}