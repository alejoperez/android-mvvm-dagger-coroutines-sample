package com.mvvm.dagger.coroutines.sample.login

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseViewModel
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.databinding.BindingAdapters
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.utils.*
import com.mvvm.dagger.coroutines.sample.webservice.LoginRequest
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application,private val userRepository: UserRepository): BaseViewModel(application) {

    val email = ObservableField("")
    val password = ObservableField("")

    val emailError = ObservableInt(BindingAdapters.EMPTY)
    val passwordError = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableBoolean(false)

    val loginEvent = MutableLiveData<Event<LoginResponse>>()

    private fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    private fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    private fun isValidForm(): Boolean = isValidEmail() && isValidPassword()

    fun login() {
        if (isValidForm()) {

            viewModelScope.launch {

                try {
                    showProgress()

                    val response = withContext(Dispatchers.IO) {
                        val loginRequest = LoginRequest(email.getValueOrDefault(), password.getValueOrDefault())
                        userRepository.loginAsync(getApplication(), loginRequest).await()
                    }

                    loginEvent.value = Event.success(response)

                } catch (t: Throwable) {
                    loginEvent.value = t.getEventError()
                } finally {
                    hideProgress()
                }
            }

        } else {
            emailError.checkField(R.string.error_invalid_email,isValidEmail())
            passwordError.checkField(R.string.error_empty_password,isValidPassword())
        }
    }

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)
}