package com.mvvm.dagger.coroutines.sample.data

interface BaseRepositoryModule {
    companion object {
        const val LOCAL = "local"
        const val REMOTE = "remote"
    }
}