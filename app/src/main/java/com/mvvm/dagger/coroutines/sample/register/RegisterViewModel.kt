package com.mvvm.dagger.coroutines.sample.register

import android.app.Application
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
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class RegisterViewModel @Inject constructor(application: Application, private val userRepository: UserRepository) : BaseViewModel(application) {

    val name = ObservableField("")
    val email = ObservableField("")
    val password = ObservableField("")

    val errorName = ObservableInt(BindingAdapters.EMPTY)
    val errorEmail = ObservableInt(BindingAdapters.EMPTY)
    val errorPassword = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableField(false)

    val registerEvent = MutableLiveData<Event<RegisterResponse>>()

    fun register() {
        if (isValidForm()) {

            viewModelScope.launch {
                try {
                    showProgress()

                    val response = withContext(Dispatchers.IO) {
                        val request = RegisterRequest(name.getValueOrDefault(), email.getValueOrDefault(), password.getValueOrDefault())
                        userRepository.registerAsync(getApplication(),request).await()
                    }



                    registerEvent.value = Event.success(response)

                } catch (t: Throwable) {
                    registerEvent.value = t.getEventError()
                } finally {
                    hideProgress()
                }
            }

        } else {
            errorName.checkField(R.string.error_name_empty, isValidName())
            errorEmail.checkField(R.string.error_invalid_email, isValidEmail())
            errorPassword.checkField(R.string.error_empty_password, isValidPassword())
        }
    }

    private fun isValidName(): Boolean = name.getValueOrDefault().isNotEmpty()

    private fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    private fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    private fun isValidForm(): Boolean = isValidName() && isValidEmail() && isValidPassword()

    private fun showProgress() = isLoading.set(true)

    private fun hideProgress() = isLoading.set(false)
}