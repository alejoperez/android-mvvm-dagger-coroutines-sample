package com.mvvm.dagger.coroutines.sample.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.room.User
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.getEventError
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    val user = MutableLiveData<Event<User>>()

    fun getUser() {
        viewModelScope.launch(contextProvider.Main) {
            try {
                val response = withContext(contextProvider.IO) { userRepository.getUserAsync().await() }
                user.value = Event.success(response)
            } catch (e: Exception) {
                user.value = e.getEventError()
            }
        }
    }

    fun logout() = userRepository.logout()

}