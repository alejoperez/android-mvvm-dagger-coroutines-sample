package com.mvvm.dagger.coroutines.sample.data.preference

import androidx.test.platform.app.InstrumentationRegistry
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class PreferenceManagerTest: BaseTest() {

    companion object {
        const val LONG_KEY = "long"
        const val STRING_KEY = "string"
        const val INT_KEY = "int"
        const val BOOLEAN_KEY = "boolean"
        const val FLOAT_KEY = "float"
    }

    private val preferenceManager = PreferenceManager(InstrumentationRegistry.getInstrumentation().targetContext)

    @Test
    fun longPreference(){
        var value = preferenceManager.findPreference(LONG_KEY,0)
        Assert.assertEquals("value should be 0",0,value)

        preferenceManager.putPreference(LONG_KEY,89)
        value = preferenceManager.findPreference(LONG_KEY,0)

        Assert.assertEquals("value should be 89",89,value)
    }

    @Test
    fun stringPreference(){
        var value = preferenceManager.findPreference(STRING_KEY,"")
        Assert.assertEquals("value should be empty","",value)

        preferenceManager.putPreference(STRING_KEY,"alejo")
        value = preferenceManager.findPreference(STRING_KEY,"")

        Assert.assertEquals("value should be alejo","alejo",value)
    }

    @Test
    fun intPreference(){
        var value = preferenceManager.findPreference(INT_KEY,1)
        Assert.assertEquals("value should be 1",1,value)

        preferenceManager.putPreference(INT_KEY,45)
        value = preferenceManager.findPreference(INT_KEY,1)

        Assert.assertEquals("value should be 45",45,value)
    }

    @Test
    fun booleanPreference(){
        var value = preferenceManager.findPreference(BOOLEAN_KEY,false)
        Assert.assertFalse("value should be false",value)

        preferenceManager.putPreference(BOOLEAN_KEY,true)
        value = preferenceManager.findPreference(BOOLEAN_KEY,false)

        Assert.assertTrue("value should be true",value)
    }

    @Test
    fun floatPreference(){
        var value:Float = preferenceManager.findPreference(FLOAT_KEY,1.5.toFloat())
        Assert.assertEquals("value should be 1.5",1.5,value.toDouble(),0.001)

        preferenceManager.putPreference(FLOAT_KEY,56.98.toFloat())
        value = preferenceManager.findPreference(FLOAT_KEY,1.5.toFloat())

        Assert.assertEquals("value should be 56.98",56.98,value.toDouble(), 0.001)
    }

}