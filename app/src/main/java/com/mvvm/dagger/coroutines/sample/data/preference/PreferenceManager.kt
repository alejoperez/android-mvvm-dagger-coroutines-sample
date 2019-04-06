package com.mvvm.dagger.coroutines.sample.data.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager<T>(val context: Context) {

    companion object {
        private const val FILE_NAME = "Sample"
        const val ACCESS_TOKEN = "access_token"
    }

    private val prefs: SharedPreferences by lazy { context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE) }

    @Suppress("UNCHECKED_CAST")
    fun findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> Unit
        }
        res as T
    }

    fun putPreference(name: String, value: T) = with(prefs) {
        when (value) {
            is Long -> edit().putLong(name, value).apply()
            is String -> edit().putString(name, value).apply()
            is Int -> edit().putInt(name, value).apply()
            is Boolean -> edit().putBoolean(name, value).apply()
            is Float -> edit().putFloat(name, value).apply()
            else -> Unit
        }
    }
}