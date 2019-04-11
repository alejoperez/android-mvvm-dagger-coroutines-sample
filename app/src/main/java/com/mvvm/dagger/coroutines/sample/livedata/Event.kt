package com.mvvm.dagger.coroutines.sample.livedata

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T> private constructor(val status: Status,private val data: T?) {

    companion object {
        fun <T> success(data: T?): Event<T> = Event(Status.SUCCESS, data)

        fun <T> failure(): Event<T> = Event(Status.FAILURE, null)

        fun <T> networkError(): Event<T> = Event(Status.NETWORK_ERROR, null)
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekData(): T? = data
}

enum class Status { SUCCESS, FAILURE, NETWORK_ERROR }