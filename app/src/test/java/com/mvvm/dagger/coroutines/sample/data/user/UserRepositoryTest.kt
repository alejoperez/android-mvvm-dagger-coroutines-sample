package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class UserRepositoryTest : BaseTest() {

    private val user: User = mock()
    private val requestLogin: LoginRequest = mock()
    private val responseLogin: LoginResponse = mock()
    private val requestRegister: RegisterRequest = mock()
    private val responseRegister: RegisterResponse = mock()
    private val localDataSource = mock<UserLocalDataSource>()
    private val remoteDataSource = mock<UserRemoteDataSource>()
    private val preferenceManager: PreferenceManager = mock {
        doNothing().whenever(it).putPreference(ArgumentMatchers.anyString(), eq(Any()))
    }


    private fun resetInteractions() {
        reset(localDataSource, remoteDataSource)
    }

    @Test
    fun saveUserAsyncTest() = runBlocking {
        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)
        repository.saveUserAsync(user)

        verifyZeroInteractions(remoteDataSource)
        verify(localDataSource, times(1)).saveUserAsync(user)
        resetInteractions()
    }

    @Test
    fun getUserAsyncTest() = runBlocking {
        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)

        repository.getUserAsync()

        verifyZeroInteractions(remoteDataSource)

        verify(localDataSource, times(1)).getUserAsync()

        resetInteractions()

    }

    @Test
    fun loginAsyncTest() = runBlocking {
        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)

        val result = CompletableDeferred(responseLogin)
        whenever(remoteDataSource.loginAsync(requestLogin)).thenReturn(result)

        repository.loginAsync(requestLogin)

        verify(remoteDataSource, times(1)).loginAsync(requestLogin)

        verify(localDataSource, times(1)).saveUserAsync(result.await().toUser())


        resetInteractions()

    }

    @Test
    fun registerAsyncTest() = runBlocking {

        val result = CompletableDeferred(responseRegister)
        whenever(remoteDataSource.registerAsync(requestRegister)).thenReturn(result)

        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)

        repository.registerAsync(requestRegister)

        verify(remoteDataSource, times(1)).registerAsync(requestRegister)

        verify(localDataSource, times(1)).saveUserAsync(result.await().toUser())

        resetInteractions()
    }

    @Test
    fun isLoggedInTest() {
        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)

        repository.isLoggedIn()

        verifyZeroInteractions(remoteDataSource)

        verify(localDataSource, times(1)).isLoggedIn()

        resetInteractions()
    }

    @Test
    fun logoutTest() {
        val repository = UserRepository(localDataSource, remoteDataSource, preferenceManager)

        repository.logout()

        verifyZeroInteractions(remoteDataSource)

        verify(localDataSource, times(1)).logout()

        resetInteractions()
    }

}