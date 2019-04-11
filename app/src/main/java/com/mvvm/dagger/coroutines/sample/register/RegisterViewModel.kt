package com.mvvm.dagger.coroutines.sample.register

import androidx.annotation.VisibleForTesting
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
import com.mvvm.dagger.coroutines.sample.webservice.RegisterRequest
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import kotlinx.coroutines.*
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) : BaseViewModel() {

    val name = ObservableField("")
    val email = ObservableField("")
    val password = ObservableField("")

    val errorName = ObservableInt(BindingAdapters.EMPTY)
    val errorEmail = ObservableInt(BindingAdapters.EMPTY)
    val errorPassword = ObservableInt(BindingAdapters.EMPTY)

    val isLoading = ObservableBoolean(false)

    val registerEvent = MutableLiveData<Event<RegisterResponse>>()

    fun register() {
        if (isValidForm()) {

            viewModelScope.launch(contextProvider.Main) {
                try {
                    showProgress()

                    val response = withContext(contextProvider.IO) {
                        val request = RegisterRequest(name.getValueOrDefault(), email.getValueOrDefault(), password.getValueOrDefault())
                        userRepository.registerAsync(request).await()
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isValidName(): Boolean = name.getValueOrDefault().isNotEmpty()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isValidEmail(): Boolean = email.getValueOrDefault().isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.getValueOrDefault()).matches()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isValidPassword(): Boolean = password.getValueOrDefault().isNotEmpty()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isValidForm(): Boolean = isValidName() && isValidEmail() && isValidPassword()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun showProgress() = isLoading.set(true)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun hideProgress() = isLoading.set(false)

}