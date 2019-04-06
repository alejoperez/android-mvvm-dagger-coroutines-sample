package com.mvvm.dagger.coroutines.sample.di

import android.app.Application
import com.mvvm.dagger.coroutines.sample.application.SampleApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            ViewsModule::class,
            ViewModelModule::class,
            RepositoriesModule::class,
            RoomModule::class,
            WebServiceModule::class,
            AndroidSupportInjectionModule::class
        ]
)
interface AppComponent: AndroidInjector<SampleApp> {

    fun inject(app: Application)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent

    }
}