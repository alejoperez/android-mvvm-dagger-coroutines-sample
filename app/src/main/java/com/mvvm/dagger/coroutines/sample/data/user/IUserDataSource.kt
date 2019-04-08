package com.mvvm.dagger.coroutines.sample.data.user

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.Deferred

interface IUserDataSource {

    suspend fun getUserAsync(context: Context): Deferred<User>

    suspend fun saveUserAsync(context: Context,user: User)

    suspend fun loginAsync(context: Context, request: LoginRequest): Deferred<LoginResponse>

    suspend fun registerAsync(context: Context, request: RegisterRequest): Deferred<RegisterResponse>

    fun isLoggedIn(context: Context): Boolean

    fun logout(context: Context)
}