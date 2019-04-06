package com.mvvm.dagger.coroutines.sample.livedata

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T> private constructor(val status: Status,private val data: T?) {

    companion object {
        fun <T> loading(): Event<T> = Event(Status.LOADING, null)

        fun <T> success(data: T?): Event<T> = Event(Status.SUCCESS, data)

        fun <T> failure(): Event<T> = Event(Status.FAILURE, null)

        fun <T> networkError(): Event<T> = Event(Status.NETWORK_ERROR, null)
    }

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getDataIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekData(): T? = data
}

enum class Status { SUCCESS, LOADING, FAILURE, NETWORK_ERROR }