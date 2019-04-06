package com.mvvm.dagger.coroutines.sample.di

import com.mvvm.dagger.coroutines.sample.login.LoginModule
import com.mvvm.dagger.coroutines.sample.main.MainModule
import com.mvvm.dagger.coroutines.sample.photos.PhotosModule
import com.mvvm.dagger.coroutines.sample.photos.detail.PhotoDetailModule
import com.mvvm.dagger.coroutines.sample.places.PlacesModule
import com.mvvm.dagger.coroutines.sample.register.RegisterModule
import com.mvvm.dagger.coroutines.sample.splash.SplashModule
import dagger.Module

@Module(
        includes = [
            SplashModule::class,
            RegisterModule::class,
            LoginModule::class,
            MainModule::class,
            PlacesModule::class,
            PhotosModule::class,
            PhotoDetailModule::class
        ]
)
abstract class ViewsModule