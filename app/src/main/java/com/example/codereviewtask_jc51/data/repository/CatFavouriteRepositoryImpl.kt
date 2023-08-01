package com.example.codereviewtask_jc51.data.repository

import com.example.codereviewtask_jc51.data.di.LocalDataSource
import com.example.codereviewtask_jc51.domain.datasource.CatFavouriteDataSource
import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatFavouriteRepositoryImpl  @Inject constructor(
    @LocalDataSource private val localDataSource: CatFavouriteDataSource
): CatFavouriteRepository {

  override fun addFavoriteCat(catId: Int) {
       return localDataSource.addFavoriteCat(catId)
    }
    override fun removeFavoriteCat(catId: Int) {
        return localDataSource.removeFavoriteCat(catId)
    }

    override fun isFavouriteCat(catId: Int): Single<Boolean> {
        return localDataSource.isFavouriteCat(catId)
    }

    override fun getFavoriteCats(): Observable<List<String>> {
        return localDataSource.getFavoriteCats()
    }
}