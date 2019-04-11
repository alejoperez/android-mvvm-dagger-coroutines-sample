package com.mvvm.dagger.coroutines.sample.base

import androidx.lifecycle.ViewModel
import com.mvvm.dagger.coroutines.sample.coroutines.CoroutineContextProvider

abstract class BaseViewModel: ViewModel() {
    open var contextProvider: CoroutineContextProvider = CoroutineContextProvider()
}