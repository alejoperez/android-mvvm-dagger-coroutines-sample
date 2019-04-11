package com.mvvm.dagger.coroutines.sample.data.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mvvm.dagger.coroutines.sample.base.BaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDaoTest: BaseTest() {

    private lateinit var dataBase: SampleDataBase

    @Before
    fun initDb() {
        dataBase = Room
                .inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, SampleDataBase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        dataBase.close()
    }

    @Test
    fun userNotFoundTest() {
        val user = dataBase.userDao().getUser()
        Assert.assertNull("user should be null",user)
    }

    @Test
    fun userFoundTest() {
        dataBase.userDao().saveUser(getSampleData())

        val dataBaseUser = dataBase.userDao().getUser()

        Assert.assertEquals("userId should be ${getSampleData().id}",
                getSampleData().id,
                dataBaseUser.id)
    }

    private fun getSampleData() = User(1, "1", "1")

}