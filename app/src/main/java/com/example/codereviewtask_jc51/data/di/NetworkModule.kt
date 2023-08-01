package com.example.codereviewtask_jc51.data.di

import com.example.codereviewtask_jc51.data.network.CatApi
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://catbreeds.proxy.beeceptor.com/"

    @Provides
    fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                JacksonConverterFactory.create(
                    jacksonObjectMapper().registerKotlinModule()
                )
            )
            .addCallAdapterFactory(
                RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())
            )
            .client(
                OkHttpClient
                    .Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                    .build()
            )
            .build()
            .create(CatApi::class.java)

    }
}