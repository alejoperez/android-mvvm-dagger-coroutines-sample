package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject constructor(@Named(BaseRepositoryModule.LOCAL) private val localDataSource: IUserDataSource,
                    @Named(BaseRepositoryModule.REMOTE) private val remoteDataSource: IUserDataSource,
                    private val preferenceManager: PreferenceManager) : IUserDataSource {

    override suspend fun saveUserAsync(user: User) = localDataSource.saveUserAsync(user)

    override suspend fun getUserAsync(): Deferred<User> = localDataSource.getUserAsync()

    override suspend fun loginAsync(request: LoginRequest): Deferred<LoginResponse> =
            remoteDataSource.loginAsync(request).also {
                val response = it.await()
                updateUserAndAccessToken(response.toUser(), response.accessToken)
            }

    override suspend fun registerAsync(request: RegisterRequest): Deferred<RegisterResponse> =
            remoteDataSource.registerAsync(request).also {
                val response = it.await()
                updateUserAndAccessToken(response.toUser(), response.accessToken)
            }


    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()

    override fun logout() = localDataSource.logout()

    private suspend fun updateUserAndAccessToken(user: User, accessToken: String) {
        preferenceManager.putPreference(PreferenceManager.ACCESS_TOKEN, accessToken)
        localDataSource.saveUserAsync(user)
    }

}
