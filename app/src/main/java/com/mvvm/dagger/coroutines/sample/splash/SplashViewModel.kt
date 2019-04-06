package com.mvvm.dagger.coroutines.sample.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.livedata.Event
import javax.inject.Inject

class SplashViewModel @Inject constructor(application: Application, private val userRepository: UserRepository): BaseViewModel(application) {

    val isUserLoggedEvent = MutableLiveData<Event<Boolean>>()

    fun isUserLoggedIn() = userRepository.isLoggedIn(getApplication())

}