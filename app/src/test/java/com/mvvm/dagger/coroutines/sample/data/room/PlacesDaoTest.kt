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
class PlacesDaoTest : BaseTest() {

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
    fun placesTest() {
        dataBase.placeDao().savePlaces(getSampleData())

        val dataBasePlaces = dataBase.placeDao().getPlaces()

        assert(dataBasePlaces.isNotEmpty()) {
            "It should be places saved"
        }

        Assert.assertEquals("size should be ${getSampleData().size}",
                getSampleData().size,
                dataBasePlaces.size)
    }

    private fun getSampleData() = listOf(
            Place(1, "1", "1", 0.0, 0.0),
            Place(2, "2", "2", 0.0, 0.0),
            Place(3, "3", "3", 0.0, 0.0)
    )

}