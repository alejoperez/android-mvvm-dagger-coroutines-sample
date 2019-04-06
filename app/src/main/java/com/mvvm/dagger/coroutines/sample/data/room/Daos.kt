package com.mvvm.dagger.coroutines.sample.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * from user LIMIT 1")
    suspend fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

}

@Dao
interface PlaceDao {

    @Query("SELECT * from place")
    suspend fun getPlaces(): List<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlaces(places: List<Place>)

}

@Dao
interface PhotoDao {

    @Query("SELECT * from photo")
    suspend fun getPhotos(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhotos(photos: List<Photo>)
}