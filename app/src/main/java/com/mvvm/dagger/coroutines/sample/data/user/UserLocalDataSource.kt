package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.data.room.UserDao
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(private val userDao: UserDao, private val preferenceManager: PreferenceManager): IUserDataSource {

    override suspend fun getUserAsync(): Deferred<User> = CoroutineScope(Dispatchers.IO).async { userDao.getUser() }

    override suspend fun saveUserAsync(user: User) = withContext(Dispatchers.IO) { userDao.saveUser(user) }

    override suspend fun loginAsync(request: LoginRequest): Deferred<LoginResponse> = throw UnsupportedOperationException()

    override suspend fun registerAsync(request: RegisterRequest): Deferred<RegisterResponse> = throw UnsupportedOperationException()

    override fun isLoggedIn(): Boolean = preferenceManager.findPreference(PreferenceManager.ACCESS_TOKEN,"").isNotEmpty()

    override fun logout() = preferenceManager.putPreference(PreferenceManager.ACCESS_TOKEN,"")

}