package com.mvvm.dagger.coroutines.sample.places

import androidx.lifecycle.ViewModel
import com.mvvm.dagger.coroutines.sample.di.FragmentScope
import com.mvvm.dagger.coroutines.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PlacesModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeFragment(): PlacesFragment

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindViewModel(viewModel: PlacesViewModel): ViewModel

}