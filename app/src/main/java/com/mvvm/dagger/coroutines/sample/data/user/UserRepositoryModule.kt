package com.mvvm.dagger.coroutines.sample.data.user

import com.mvvm.dagger.coroutines.sample.data.BaseRepositoryModule
import dagger.Binds
import dagger.Module
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class UserRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: UserRepository): IUserDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.LOCAL)
    abstract fun bindUserLocalDataSource(localDataSource: UserLocalDataSource): IUserDataSource

    @Singleton
    @Binds
    @Named(BaseRepositoryModule.REMOTE)
    abstract fun bindUserRemoteDataSource(remoteDataSource: UserRemoteDataSource): IUserDataSource

}