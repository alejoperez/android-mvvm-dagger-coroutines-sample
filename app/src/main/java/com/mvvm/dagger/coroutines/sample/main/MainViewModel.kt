package com.mvvm.dagger.coroutines.sample.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    val user = MutableLiveData<Event<User>>()

    fun getUser() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) { userRepository.getUserAsync(getApplication()).await() }
                user.value = Event.success(response)
            } catch (e: Exception) {
                user.value = Event.failure()
            }
        }
    }

    fun logout() = userRepository.logout(getApplication())

}