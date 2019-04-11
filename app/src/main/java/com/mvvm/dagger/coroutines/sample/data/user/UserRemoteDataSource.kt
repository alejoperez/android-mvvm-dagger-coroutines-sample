package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.webservice.*
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val api: IApi): IUserDataSource {

    override suspend fun loginAsync(request: LoginRequest): Deferred<LoginResponse> = api.loginAsync(request)

    override suspend fun registerAsync(request: RegisterRequest): Deferred<RegisterResponse> = api.registerAsync(request)

    override suspend fun getUserAsync(): Deferred<User> = throw UnsupportedOperationException()

    override suspend fun saveUserAsync(user: User) = throw UnsupportedOperationException()

    override fun isLoggedIn(): Boolean = throw UnsupportedOperationException()

    override fun logout() = throw UnsupportedOperationException()

}