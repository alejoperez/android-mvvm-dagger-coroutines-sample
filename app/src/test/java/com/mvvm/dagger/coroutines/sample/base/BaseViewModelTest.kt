package com.mvvm.dagger.coroutines.sample.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class BaseViewModelTest {

    @Rule
    @JvmField
    var mvvmRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var rxRule = RxImmediateSchedulerRule()
}

