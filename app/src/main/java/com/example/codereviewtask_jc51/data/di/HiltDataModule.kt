package com.example.codereviewtask_jc51.data.di

import android.content.Context
import com.example.codereviewtask_jc51.data.datasource.CatBreedLocalDataSource
import com.example.codereviewtask_jc51.data.datasource.CatBreedRemoteDataSource
import com.example.codereviewtask_jc51.data.datasource.CatFavouriteLocalDataSource
import com.example.codereviewtask_jc51.data.datasource.CatFavouriteRemoteDataSource
import com.example.codereviewtask_jc51.data.network.CatApi
import com.example.codereviewtask_jc51.domain.datasource.CatBreedDataSource
import com.example.codereviewtask_jc51.domain.datasource.CatFavouriteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltDataModule {

    @Singleton
    @LocalDataSource
    @Provides
    fun provideLocalCatDatasource(): CatBreedDataSource {
        return CatBreedLocalDataSource()
    }

    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteCatDatasource(catApi: CatApi): CatBreedDataSource {
        return CatBreedRemoteDataSource(catApi)
    }

    @Singleton
    @LocalDataSource
    @Provides
    fun provideLocalCatFavDatasource(
        @ApplicationContext context: Context
    ): CatFavouriteDataSource {
        return CatFavouriteLocalDataSource(context)
    }

    @Singleton
    @RemoteDataSource
    @Provides
    fun provideRemoteCatFavDatasource(): CatFavouriteDataSource {
        return CatFavouriteRemoteDataSource()
    }
}