package com.example.codereviewtask_jc51.data.datasource

import com.example.codereviewtask_jc51.data.di.RemoteDataSource
import com.example.codereviewtask_jc51.domain.datasource.CatFavouriteDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

class CatFavouriteRemoteDataSource: CatFavouriteDataSource {

    override fun addFavoriteCat(catId: Int) {
        throw RuntimeException("This method should not be called from Remote")
    }
    override fun removeFavoriteCat(catId: Int) {
        throw RuntimeException("This method should not be called from Remote")
    }

    override fun isFavouriteCat(catId: Int): Single<Boolean> {
        throw RuntimeException("This method should not be called from Remote")
    }

    override fun getFavoriteCats(): Observable<List<String>> {
        throw RuntimeException("This method should not be called from Remote")
    }
}