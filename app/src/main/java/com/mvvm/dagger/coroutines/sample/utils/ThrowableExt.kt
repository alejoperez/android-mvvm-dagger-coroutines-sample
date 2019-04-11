package com.mvvm.dagger.coroutines.sample.utils

import com.mvvm.dagger.coroutines.sample.livedata.Event
import retrofit2.HttpException


fun <T> Throwable.getEventError(): Event<T> {
    return when (this) {
        is HttpException -> Event.failure()
        else -> Event.networkError()
    }
}