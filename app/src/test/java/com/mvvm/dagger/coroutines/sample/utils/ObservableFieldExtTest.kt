package com.mvvm.dagger.coroutines.sample.utils

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.mvvm.dagger.coroutines.sample.R
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import com.mvvm.dagger.coroutines.sample.databinding.BindingAdapters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class ObservableFieldExtTest: BaseTest() {

    @Test
    fun checkFieldValid() {
        val observableInt = ObservableInt()
        observableInt.checkField(R.string.error_network,true)
        Assert.assertEquals("value should be empty",BindingAdapters.EMPTY,observableInt.get())
    }

    @Test
    fun checkFieldInvalid() {
        val observableInt = ObservableInt()
        observableInt.checkField(R.string.error_network,false)
        Assert.assertEquals("value should be R.string.error_network",R.string.error_network,observableInt.get())
    }

    @Test
    fun getValueOrDefaultStringTest(){
        val observableString = ObservableField<String>()
        Assert.assertEquals("value should be empty string","",observableString.getValueOrDefault())
        Assert.assertEquals("value should be empty string","default",observableString.getValueOrDefault("default"))

        observableString.set("90")
        Assert.assertEquals("value should be 90","90",observableString.getValueOrDefault())
    }

    @Test
    fun getValueOrDefaultBooleanTest() {
        val observableBoolean = ObservableField<Boolean>()
        Assert.assertFalse("value should be false",observableBoolean.getValueOrDefault(false))
        Assert.assertFalse("value should be false",observableBoolean.getValueOrDefault(false))
        Assert.assertTrue("value should be true",observableBoolean.getValueOrDefault(true))

        observableBoolean.set(true)

        Assert.assertTrue("value should be true",observableBoolean.getValueOrDefault())


    }
}
