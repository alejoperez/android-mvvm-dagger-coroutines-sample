package com.mvvm.dagger.coroutines.sample.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(@PrimaryKey val id: Long, val name: String, val email: String) : Parcelable

@Parcelize
@Entity
data class Place(@PrimaryKey val id: Long, val companyName: String, val address: String, val lat: Double, val lon: Double) : Parcelable

@Parcelize
@Entity
data class Photo(@PrimaryKey val id: Long, val albumId: String, val title: String, val url: String, val thumbnailUrl: String) : Parcelable
