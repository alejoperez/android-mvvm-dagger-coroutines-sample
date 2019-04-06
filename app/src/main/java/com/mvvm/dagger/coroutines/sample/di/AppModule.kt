package com.mvvm.dagger.coroutines.sample.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context = application.applicationContext

}