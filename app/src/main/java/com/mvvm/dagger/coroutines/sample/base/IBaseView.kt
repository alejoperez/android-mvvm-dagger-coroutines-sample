package com.mvvm.dagger.coroutines.sample.base

import android.content.Context

interface IBaseView {

    fun isActive(): Boolean

    fun showAlert(textResource: Int)

    fun getViewContext(): Context

    fun <T: BaseViewModel> obtainViewModel(clazz: Class<T>): T

    fun onNetworkError()
}