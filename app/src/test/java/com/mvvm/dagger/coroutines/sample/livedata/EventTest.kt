package com.mvvm.dagger.coroutines.sample.livedata

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class EventTest: BaseTest() {

    companion object {
        const val MSG_INVALID_EVENT_DATA_NOT_NULL_VALUE = "Even data should be null"
        const val MSG_INVALID_EVENT_DATA_NULL_VALUE = "Even data shouldn't be null"
        const val MSG_INVALID_STATUS_SUCCESS = "Status should de SUCCESS"
        const val MSG_INVALID_STATUS_FAILURE = "Status should de FAILURE"
        const val MSG_INVALID_STATUS_NETWORK_ERROR = "Status should de NETWORK_ERROR"
    }

    @Test
    fun successTest(){
        val event = Event.success(Any())
        Assert.assertNotNull(MSG_INVALID_EVENT_DATA_NULL_VALUE,event.peekData())
        Assert.assertEquals(MSG_INVALID_STATUS_SUCCESS,Status.SUCCESS,event.status)
    }

    @Test
    fun failureTest(){
        val event = Event.failure<Any>()
        Assert.assertNull(MSG_INVALID_EVENT_DATA_NOT_NULL_VALUE,event.peekData())
        Assert.assertEquals(MSG_INVALID_STATUS_FAILURE,Status.FAILURE,event.status)
    }

    @Test
    fun networkError() {
        val event = Event.networkError<Any>()
        Assert.assertNull(MSG_INVALID_EVENT_DATA_NOT_NULL_VALUE,event.peekData())
        Assert.assertEquals(MSG_INVALID_STATUS_NETWORK_ERROR,Status.NETWORK_ERROR,event.status)
    }


}