package com.example.codereviewtask_jc51.data.di

import com.example.codereviewtask_jc51.data.repository.CatFavouriteRepositoryImpl
import com.example.codereviewtask_jc51.data.repository.CatRepositoryImpl
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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