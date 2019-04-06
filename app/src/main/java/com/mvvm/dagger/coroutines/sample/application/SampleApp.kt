package com.mvvm.dagger.coroutines.sample.application

import com.mvvm.dagger.coroutines.sample.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SampleApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val graph = DaggerAppComponent.builder().application(this).build()
        graph.inject(this)
        return graph
    }

}