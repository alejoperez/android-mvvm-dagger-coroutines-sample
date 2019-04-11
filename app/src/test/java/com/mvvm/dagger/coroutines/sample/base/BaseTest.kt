package com.mvvm.dagger.coroutines.sample.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mvvm.dagger.coroutines.sample.coroutines.CoroutineContextProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    fun <T> T.toDeferred() = CompletableDeferred(this)

    class TestContextProvider : CoroutineContextProvider() {
        override val Main: CoroutineContext = Dispatchers.Unconfined
        override val IO: CoroutineContext = Dispatchers.Unconfined
    }
}