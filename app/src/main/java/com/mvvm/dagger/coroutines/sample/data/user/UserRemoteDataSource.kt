package com.mvvm.dagger.coroutines.sample.data.user

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.webservice.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val api: IApi): IUserDataSource {

    override suspend fun login(context: Context, request: LoginRequest): Deferred<LoginResponse> = api.login(request)

    override suspend fun register(context: Context, request: RegisterRequest): Deferred<RegisterResponse> = api.register(request)

    override fun logout(context: Context) = throw UnsupportedOperationException()

    override suspend fun getUser(context: Context): Deferred<User> = throw UnsupportedOperationException()

    override suspend fun saveUser(context: Context, user: User) = throw UnsupportedOperationException()

    override fun isLoggedIn(context: Context): Boolean = throw UnsupportedOperationException()
}