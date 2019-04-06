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

    override suspend fun saveUser(context: Context, user: User) = localDataSource.saveUser(context, user)

    override suspend fun getUser(context: Context): Deferred<User> = localDataSource.getUser(context)

    override suspend fun login(context: Context, request: LoginRequest): Deferred<LoginResponse> =
            remoteDataSource.login(context, request)
                    .also {
                        val user = it.await()
                        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, user.accessToken)
                        localDataSource.saveUser(context, user.toUser())
                    }

    override suspend fun register(context: Context, request: RegisterRequest): Deferred<RegisterResponse> =
            remoteDataSource.register(context, request)
                    .also {
                        val user = it.await()
                        PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN, user.accessToken)
                        localDataSource.saveUser(context, user.toUser())
                    }


    override fun isLoggedIn(context: Context): Boolean = localDataSource.isLoggedIn(context)

    override fun logout(context: Context) = localDataSource.logout(context)

}
