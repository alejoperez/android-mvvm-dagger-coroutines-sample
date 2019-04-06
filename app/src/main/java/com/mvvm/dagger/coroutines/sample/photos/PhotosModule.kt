package com.mvvm.dagger.coroutines.sample.photos

import androidx.lifecycle.ViewModel
import com.mvvm.dagger.coroutines.sample.di.FragmentScope
import com.mvvm.dagger.coroutines.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PhotosModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFragment(): PhotosFragment

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindViewModel(viewModel: PhotosViewModel): ViewModel

}