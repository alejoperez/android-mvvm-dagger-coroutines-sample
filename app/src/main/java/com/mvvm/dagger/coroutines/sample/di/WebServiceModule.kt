package com.mvvm.dagger.coroutines.sample.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mvvm.dagger.coroutines.sample.BuildConfig
import com.mvvm.dagger.coroutines.sample.data.preference.PreferenceManager
import com.mvvm.dagger.coroutines.sample.livedata.Event
import com.mvvm.dagger.coroutines.sample.webservice.IApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class WebServiceModule {

    enum class AuthenticationType {
        BASIC, OAUTH, NONE
    }

    companion object {
        private const val CONNECT_TIMEOUT = 60L
        private const val READ_TIMEOUT = 60L
        private const val WRITE_TIMEOUT = 60L
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient =
            OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                        for (entry in getHeaders(context, AuthenticationType.NONE).entries) {
                            requestBuilder.addHeader(entry.key, entry.value)
                        }
                        requestBuilder.method(original.method(), original.body())
                        chain.proceed(requestBuilder.build())
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(okHttpClient).build()

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): IApi = retrofit.create(IApi::class.java)

    private fun getHeaders(context: Context, authType: AuthenticationType): Map<String, String> {
        val requestHeader = hashMapOf(
                "version" to BuildConfig.VERSION_NAME
        )
        val accessToken = PreferenceManager<String>(context).findPreference(PreferenceManager.ACCESS_TOKEN, "")
        when (authType) {
            AuthenticationType.BASIC -> {
                requestHeader["Authorization"] = "Basic $accessToken"
            }
            AuthenticationType.OAUTH -> {
                requestHeader["Authorization"] = "Bearer $accessToken"
            }
            AuthenticationType.NONE -> Unit
        }
        return requestHeader
    }

}