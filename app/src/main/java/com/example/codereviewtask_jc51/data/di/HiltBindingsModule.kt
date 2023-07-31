package com.example.codereviewtask_jc51.data.di

import android.content.Context
import com.example.codereviewtask_jc51.data.datasource.CatBreedLocalDataSource
import com.example.codereviewtask_jc51.data.datasource.CatBreedRemoteDataSource
import com.example.codereviewtask_jc51.data.datasource.CatFavouriteLocalDataSource
import com.example.codereviewtask_jc51.data.datasource.CatFavouriteRemoteDataSource
import com.example.codereviewtask_jc51.data.network.CatApi
import com.example.codereviewtask_jc51.data.repository.CatFavouriteRepositoryImpl
import com.example.codereviewtask_jc51.data.repository.CatRepositoryImpl
import com.example.codereviewtask_jc51.domain.datasource.CatBreedDataSource
import com.example.codereviewtask_jc51.domain.datasource.CatFavouriteDataSource
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
abstract class HiltBindingsModule {

    @Binds
    abstract fun bindCatReporitory(
        catRepositoryImpl: CatRepositoryImpl
    ): CatRepository
    @Binds
    abstract fun bindCatFavouriteReporitory(
        catFavouriteRepositoryImpl: CatFavouriteRepositoryImpl
    ): CatFavouriteRepository

}