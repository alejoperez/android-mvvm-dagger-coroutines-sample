package com.mvvm.dagger.coroutines.sample.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    val user = MutableLiveData<Event<User>>()

    val onLogoutSuccess = MutableLiveData<Event<Unit>>()

    fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = userRepository.getUser(getApplication())
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    user.value = Event.success(response)
                } catch (e: Exception) {
                    user.value = Event.failure()
                }
            }
        }
    }

    fun logout() = userRepository.logout(getApplication())

}