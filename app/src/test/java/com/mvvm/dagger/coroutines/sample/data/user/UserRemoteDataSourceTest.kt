package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest : BaseTest() {

    private val user: User = mock()
    private val loginRequest: LoginRequest = mock()
    private val registerRequest: RegisterRequest = mock()
    private val api: IApi = mock()

    private fun resetInteractions() = reset(api)

    @Test
    fun loginAsyncTest() = runBlocking {
        val remoteDataSource = UserRemoteDataSource(api)
        remoteDataSource.loginAsync(loginRequest)
        verify(api, times(1)).loginAsync(loginRequest)
        resetInteractions()
    }

    @Test
    fun registerAsyncTest() = runBlocking {
        val remoteDataSource = UserRemoteDataSource(api)
        remoteDataSource.registerAsync(registerRequest)
        verify(api, times(1)).registerAsync(registerRequest)
        resetInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getUserAsyncTest() = runBlocking {
        val remoteDataSource = UserRemoteDataSource(api)
        remoteDataSource.getUserAsync()
        resetInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveUserAsyncTest() {
        runBlocking {
            try {
                val remoteDataSource = UserRemoteDataSource(api)
                remoteDataSource.saveUserAsync(user)
            } finally {
                verifyZeroInteractions(api)
                resetInteractions()
            }
        }
    }

    @Test(expected = UnsupportedOperationException::class)
    fun isLoggedInTest() {
        val remoteDataSource = UserRemoteDataSource(api)
        remoteDataSource.isLoggedIn()
        resetInteractions()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun logoutTest() {
        val remoteDataSource = UserRemoteDataSource(api)
        remoteDataSource.isLoggedIn()
        resetInteractions()
    }

}