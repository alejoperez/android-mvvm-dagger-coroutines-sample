package com.mvvm.dagger.coroutines.sample.register

import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.user.UserRepository
import com.mvvm.dagger.coroutines.sample.databinding.BindingAdapters
import com.mvvm.dagger.coroutines.sample.livedata.Status
import com.mvvm.dagger.coroutines.sample.webservice.RegisterResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException

@Suppress("DeferredResultUnused")
@ExperimentalCoroutinesApi
class RegisterViewModelTest: BaseTest() {

    companion object {
        const val MSG_VALID_NAME = "Name should be valid"
        const val MSG_INVALID_NAME = "Name should be invalid"
        
        const val MSG_VALID_EMAIL = "Email should be valid"
        const val MSG_INVALID_EMAIL = "Email should be invalid"

        const val MSG_VALID_PASSWORD = "Password should be valid"
        const val MSG_INVALID_PASSWORD = "Password should be invalid"

        const val MSG_VALID_FORM = "Form should be valid"
        const val MSG_INVALID_FORM = "Form should be invalid"

        const val MSG_PROGRESS_TRUE = "Progress should be true"
        const val MSG_PROGRESS_FALSE = "Progress should be false"

        const val MSG_NAME_ERROR = "Invalid name error message"
        const val MSG_EMAIL_ERROR = "Invalid email error message"
        const val MSG_PASSWORD_ERROR = "Invalid password error message"

        const val MSG_EVENT_NULL_VALUE = "Even shouldn't be null"
        const val MSG_INVALID_STATUS_SUCCESS = "Status should de SUCCESS"
        const val MSG_INVALID_STATUS_FAILURE = "Status should de FAILURE"
        const val MSG_INVALID_STATUS_NETWORK_ERROR = "Status should de NETWORK_ERROR"
    }

    private val userRepository: UserRepository = mock()
    private val viewModel = RegisterViewModel(userRepository).apply { contextProvider = TestContextProvider() }

    @Test
    fun isValidNameTest() {
        viewModel.name.set("Alejandro")
        Assert.assertTrue(MSG_VALID_NAME, viewModel.isValidName())
    }

    @Test
    fun isInvalidNameTest() {
        viewModel.name.set(null)
        Assert.assertFalse(MSG_INVALID_NAME, viewModel.isValidName())

        viewModel.name.set("")
        Assert.assertFalse(MSG_INVALID_NAME, viewModel.isValidName())
    }

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
        viewModel.name.set("Alejandro")
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        Assert.assertTrue(MSG_VALID_FORM, viewModel.isValidForm())

        viewModel.name.set("Alejandro")
        viewModel.email.set("alejo@gmail.com.co")
        viewModel.password.set("123456")
        Assert.assertTrue(MSG_VALID_FORM, viewModel.isValidForm())
    }

    @Test
    fun isInvalidFormTest() {
        viewModel.name.set(null)
        viewModel.email.set(null)
        viewModel.password.set(null)
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("")
        viewModel.email.set("")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("")
        viewModel.email.set(null)
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set(null)
        viewModel.email.set("")
        viewModel.password.set(null)
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("alejo")
        viewModel.email.set("")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("")
        viewModel.email.set("alejo")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("")
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("")
        Assert.assertFalse(MSG_INVALID_FORM, viewModel.isValidForm())

        viewModel.name.set("")
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
    fun registerInvalidFormTest() {
        viewModel.register()
        Assert.assertEquals(MSG_NAME_ERROR, viewModel.errorName.get(), R.string.error_name_empty)
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.errorEmail.get(), R.string.error_invalid_email)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.errorPassword.get(), R.string.error_empty_password)
    }

    @Test
    fun registerSuccessTest() = runBlocking {

        val response = RegisterResponse( 1, "Alejo", "alejo@gmail.com", "token")
        whenever(runBlocking { userRepository.registerAsync(any()) }).thenReturn(response.toDeferred())

        viewModel.name.set("alejo")
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")

        viewModel.register()

        Assert.assertEquals(MSG_NAME_ERROR, viewModel.errorName.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.errorEmail.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.errorPassword.get(), BindingAdapters.EMPTY)

        val event = viewModel.registerEvent.value
        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_SUCCESS, Status.SUCCESS, event?.status)

    }

    @Test
    fun registerFailureTest() = runBlocking {
        doThrow(HttpException(mock())).whenever(userRepository).registerAsync(any())


        viewModel.name.set("alejo")
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        viewModel.register()

        Assert.assertEquals(MSG_NAME_ERROR, viewModel.errorName.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.errorEmail.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.errorPassword.get(), BindingAdapters.EMPTY)

        val event = viewModel.registerEvent.value

        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_FAILURE, Status.FAILURE, event?.status)
    }

    @Test
    fun registerNetworkErrorTest() = runBlocking {
        doThrow(RuntimeException()).whenever(userRepository).registerAsync(any())

        viewModel.name.set("alejo")
        viewModel.email.set("alejo@gmail.com")
        viewModel.password.set("123456")
        viewModel.register()

        Assert.assertEquals(MSG_NAME_ERROR, viewModel.errorName.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_EMAIL_ERROR, viewModel.errorEmail.get(), BindingAdapters.EMPTY)
        Assert.assertEquals(MSG_PASSWORD_ERROR, viewModel.errorPassword.get(), BindingAdapters.EMPTY)

        val event = viewModel.registerEvent.value

        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_NETWORK_ERROR, Status.NETWORK_ERROR, event?.status)
    }
    
}