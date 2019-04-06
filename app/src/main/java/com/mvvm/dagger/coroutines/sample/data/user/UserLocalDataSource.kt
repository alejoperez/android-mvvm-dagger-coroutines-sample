package com.mvvm.dagger.coroutines.sample.data.user

import android.content.Context
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.data.room.UserDao
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(private val userDao: UserDao): IUserDataSource {

    override fun isLoggedIn(context: Context): Boolean = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override suspend fun getUser(context: Context): Deferred<User> = CoroutineScope(Dispatchers.IO).async {
        userDao.getUser()
    }

    override suspend fun saveUser(context: Context, user: User) = userDao.saveUser(user)

    override fun logout(context: Context) = PreferenceManager<String>(context).putPreference(PreferenceManager.ACCESS_TOKEN,"")

    override suspend fun login(context: Context, request: LoginRequest): Deferred<LoginResponse> = throw UnsupportedOperationException()

    override suspend fun register(context: Context, request: RegisterRequest): Deferred<RegisterResponse> = throw UnsupportedOperationException()

}