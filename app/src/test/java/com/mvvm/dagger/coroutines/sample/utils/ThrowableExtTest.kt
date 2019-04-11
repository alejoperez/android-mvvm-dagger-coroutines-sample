package com.mvvm.dagger.coroutines.sample.utils

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.livedata.Status
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class ThrowableExtTest: BaseTest() {

    companion object {
        const val MSG_INVALID_STATUS_FAILURE = "Status should de FAILURE"
        const val MSG_INVALID_STATUS_NETWORK_ERROR = "Status should de NETWORK_ERROR"
    }

    @Test
    fun eventFailureTest() {
        val t = HttpException(mock())
        val event = t.getEventError<Any>()
        Assert.assertEquals(MSG_INVALID_STATUS_FAILURE, Status.FAILURE, event.status)
    }

    @Test
    fun eventNetworkErrorTest() {
        val t = Exception()
        val event = t.getEventError<Any>()
        Assert.assertEquals(MSG_INVALID_STATUS_NETWORK_ERROR, Status.NETWORK_ERROR, event.status)
    }

}