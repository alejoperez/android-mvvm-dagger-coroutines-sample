package com.mvvm.dagger.coroutines.sample.login

import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.databinding.BindingAdapters
import com.mvvm.dagger.coroutines.sample.livedata.Status
import com.mvvm.dagger.coroutines.sample.webservice.LoginResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class LoginViewModelTest : BaseTest() {

    companion object {
        const val MSG_VALID_EMAIL = "Email should be valid"
        const val MSG_INVALID_EMAIL = "Email should be invalid"

        const val MSG_VALID_PASSWORD = "Password should be valid"
        const val MSG_INVALID_PASSWORD = "Password should be invalid"

        const val MSG_VALID_FORM = "Form should be valid"
        const val MSG_INVALID_FORM = "Form should be invalid"

        const val MSG_PROGRESS_TRUE = "Progress should be true"
        const val MSG_PROGRESS_FALSE = "Progress should be false"

        const val MSG_EMAIL_ERROR = "Invalid email error message"
        const val MSG_PASSWORD_ERROR = "Invalid password error message"

        const val MSG_EVENT_NULL_VALUE = "Even shouldn't be null"
        const val MSG_INVALID_STATUS_SUCCESS = "Status should de SUCCESS"
        const val MSG_INVALID_STATUS_FAILURE = "Status should de FAILURE"
        const val MSG_INVALID_STATUS_NETWORK_ERROR = "Status should de NETWORK_ERROR"
    }

    private val userRepository: UserRepository = mock()
    private val viewModel = LoginViewModel(userRepository).apply { contextProvider = TestContextProvider() }

    @Test
    fun isValidEmailTest() {
        viewModel.email.set("alejo@gmail.com")
        Assert.assertTrue(MSG_VALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo@gmail.com.co")
        Assert.assertTrue(MSG_VALID_EMAIL, viewModel.isValidEmail())
    }

    @Test
    fun isInvalidEmailTest() {
        viewModel.email.set(null)
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo@")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo@gmail")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo@gmail.")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())

        viewModel.email.set("alejo@gmail.com.")
        Assert.assertFalse(MSG_INVALID_EMAIL, viewModel.isValidEmail())
    }

    @Test
    fun isValidPasswordTest() {
        viewModel.password.set("123456")
        Assert.assertTrue(MSG_VALID_PASSWORD, viewModel.isValidPassword())
    }

    @Test
    fun isInvalidPasswordTest() {
        viewModel.password.set(null)
        Assert.assertFalse(MSG_INVALID_PASSWORD, viewModel.isValidPassword())

        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_PASSWORD, viewModel.isValidPassword())
    }

    @Test
    fun isValidFormTest() {
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        Assert.assertTrue(MSG_VALID_FORM, viewModel.isValidForm())

        viewModel.email.set("alejo@gmail.com.co")
        viewModel.password.set("123456")
        Assert.assertTrue(MSG_VALID_FORM, viewModel.isValidForm())
    }

    @Test
    fun isInvalidFormTest() {
        viewModel.email.set(null)
        viewModel.password.set(null)
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set("")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set(null)
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set("")
        viewModel.password.set(null)
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set("alejo")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.email.set("")
        viewModel.password.set("123456")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())
    }

    @Test
    fun showProgressTest() {
        viewModel.showProgress()
        Assert.assertTrue(MSG_PROGRESS_TRUE, viewModel.isLoading.get())
    }

    @Test
    fun hideProgressTest() {
        viewModel.hideProgress()
        Assert.assertFalse(MSG_PROGRESS_FALSE, viewModel.isLoading.get())
    }

    @Test
    fun loginInvalidFormTest() {
        viewModel.login()
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.emailError.get(), R.string.error_invalid_email)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.passwordError.get(), R.string.error_empty_password)
    }

    @Test
    fun loginSuccessTest() = runBlocking {

        val loginResponse = LoginResponse(true, 1, "Alejo", "alejo@gmail.com", "token")
        whenever(runBlocking { userRepository.loginAsync(any()) }).thenReturn(loginResponse.toDeferred())

        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")

        viewModel.login()

        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.emailError.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.passwordError.get(), BindingAdapters.EMPTY)

        val event = viewModel.loginEvent.value
        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_SUCCESS, Status.SUCCESS, event?.status)

    }

    @Test
    fun loginFailureTest() = runBlocking {
        doThrow(HttpException(mock())).whenever(userRepository).loginAsync(any())


        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        viewModel.login()
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.emailError.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.passwordError.get(), BindingAdapters.EMPTY)

        val event = viewModel.loginEvent.value
        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_FAILURE, Status.FAILURE, event?.status)
    }

    @Test
    fun loginNetworkErrorTest() = runBlocking {
        doThrow(RuntimeException()).whenever(userRepository).loginAsync(any())

        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        viewModel.login()
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.emailError.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.passwordError.get(), BindingAdapters.EMPTY)

        val event = viewModel.loginEvent.value
        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_NETWORK_ERROR, Status.NETWORK_ERROR, event?.status)
    }
}