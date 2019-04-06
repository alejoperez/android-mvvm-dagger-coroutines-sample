package com.mvvm.dagger.coroutines.sample.di

import com.mvvm.dagger.coroutines.sample.data.photos.PhotosRepositoryModule
import com.mvvm.dagger.coroutines.sample.data.places.PlacesRepositoryModule
import com.mvvm.dagger.coroutines.sample.data.user.UserRepositoryModule
import dagger.Module

@Module(
        includes = [
            UserRepositoryModule::class,
            PlacesRepositoryModule::class,
            PhotosRepositoryModule::class
        ]
)
abstract class RepositoriesModule