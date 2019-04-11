package com.mvvm.dagger.coroutines.sample.webservice

import androidx.test.platform.app.InstrumentationRegistry
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.di.WebServiceModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class IApiTest: BaseTest() {

    private lateinit var api: IApi

    @Before
    fun setUp() {
        val okHttpClient = WebServiceModule().provideOkHttpClient(PreferenceManager(InstrumentationRegistry.getInstrumentation().targetContext))
        val retrofit = WebServiceModule().provideRetrofit(okHttpClient)
        api = WebServiceModule().provideApi(retrofit)
    }

    @Test
    fun loginAsyncTest() {
        runBlocking {
            val response = api.loginAsync(LoginRequest("","")).await()
            Assert.assertNotNull("Response should not be null",response)
        }
    }

    @Test
    fun registerAsyncTest() {
        runBlocking {
            val response = api.registerAsync(RegisterRequest("","","")).await()
            Assert.assertNotNull("Response should not be null",response)
        }
    }

    @Test
    fun getPlacesAsyncTest() {
        runBlocking {
            val response = api.getPlacesAsync().await()
            Assert.assertNotNull("Response should not be null",response)
            Assert.assertTrue("places should not be empty",response.isNotEmpty())
        }
    }

    @Test
    fun getPhotosAsyncTest() {
        runBlocking {
            val response = api.getPhotosAsync().await()
            Assert.assertNotNull("Response should not be null",response)
            Assert.assertTrue("photos should not be empty",response.isNotEmpty())
        }
    }
}