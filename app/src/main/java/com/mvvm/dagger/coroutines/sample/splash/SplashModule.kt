package com.mvvm.dagger.coroutines.sample.splash

import androidx.lifecycle.ViewModel
import com.mvvm.dagger.coroutines.sample.di.ActivityScope
import com.mvvm.dagger.coroutines.sample.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SplashModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeActivity(): SplashActivity

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindViewModel(viewModel: SplashViewModel): ViewModel

}