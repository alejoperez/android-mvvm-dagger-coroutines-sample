package com.mvvm.dagger.coroutines.sample.data.user

import android.content.Context
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
                    @Named(BaseRepositoryModule.REMOTE) private val remoteDataSource: IUserDataSource) : IUserDataSource {

    override suspend fun saveUserAsync(context: Context, user: User) = localDataSource.saveUserAsync(context, user)

    override suspend fun getUserAsync(context: Context): Deferred<User> = localDataSource.getUserAsync(context)

    override suspend fun loginAsync(context: Context, request: LoginRequest): Deferred<LoginResponse> =
            remoteDataSource.loginAsync(context, request).also {
                val response = it.await()
                updateUserAndAccessToken(context, response.toUser(), response.accessToken)
            }

    override suspend fun registerAsync(context: Context, request: RegisterRequest): Deferred<RegisterResponse> =
            remoteDataSource.registerAsync(context, request).also {
                val response = it.await()
                updateUserAndAccessToken(context, response.toUser(), response.accessToken)
            }


    override fun isLoggedIn(context: Context): Boolean = localDataSource.isLoggedIn(context)

    override fun logout(context: Context) = localDataSource.logout(context)

    private suspend fun updateUserAndAccessToken(context: Context, user: User, accessToken: String) {
        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, accessToken)
        localDataSource.saveUserAsync(context, user)
    }

}
