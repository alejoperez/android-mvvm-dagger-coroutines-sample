package com.mvvm.dagger.coroutines.sample.places

import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.data.places.PlacesRepository
import com.mvvm.dagger.coroutines.sample.data.room.Place
import com.mvvm.dagger.coroutines.sample.livedata.Status
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
class PlacesViewModelTest : BaseTest() {

    companion object {
        const val MSG_EVENT_NULL_VALUE = "Even shouldn't be null"
        const val MSG_INVALID_STATUS_SUCCESS = "Status should de SUCCESS"
        const val MSG_INVALID_STATUS_FAILURE = "Status should de FAILURE"
        const val MSG_INVALID_STATUS_NETWORK_ERROR = "Status should de NETWORK_ERROR"
    }

    private val placesRepository: PlacesRepository = mock()
    private val viewModel = PlacesViewModel(placesRepository).apply { contextProvider = TestContextProvider() }

    @Test
    fun getPlacesSuccessTest() {
        val response = listOf(Place(1,"","",0.0,0.0))
        whenever(runBlocking { placesRepository.getPlacesAsync() }).thenReturn(response.toDeferred())

        viewModel.getPlaces()

        val event = viewModel.places.value
        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_SUCCESS, Status.SUCCESS, event?.status)

    }

    @Test
    fun getPlacesErrorTest() = runBlocking {
        doThrow(HttpException(mock())).whenever(placesRepository).getPlacesAsync()

        viewModel.getPlaces()

        val event = viewModel.places.value

        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_FAILURE, Status.FAILURE, event?.status)
    }

    @Test
    fun getPlacesNetworkErrorTest() = runBlocking {
        doThrow(RuntimeException()).whenever(placesRepository).getPlacesAsync()

        viewModel.getPlaces()

        val event = viewModel.places.value

        Assert.assertNotNull(MSG_EVENT_NULL_VALUE, event)
        Assert.assertEquals(MSG_INVALID_STATUS_NETWORK_ERROR, Status.NETWORK_ERROR, event?.status)
    }

}