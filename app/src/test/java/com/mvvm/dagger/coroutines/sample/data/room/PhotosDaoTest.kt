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
class PhotosDaoTest : BaseTest() {

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
    fun photosTest() {
        dataBase.photoDao().savePhotos(getPhotosSampleData())

        val dataBasePhotos = dataBase.photoDao().getPhotos()

        assert(dataBasePhotos.isNotEmpty()) {
            "It should be photos saved"
        }

        Assert.assertEquals("size should be ${getPhotosSampleData().size}",
                getPhotosSampleData().size,
                dataBasePhotos.size)
    }

    private fun getPhotosSampleData(): List<Photo> = listOf(
            Photo(1, "1", "1", "1", "1"),
            Photo(2, "2", "2", "2", "2"),
            Photo(3, "3", "3", "3", "3")
    )

}