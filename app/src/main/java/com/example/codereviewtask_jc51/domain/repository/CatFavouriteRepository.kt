package com.example.codereviewtask_jc51.domain.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CatFavouriteRepository  {
    fun addFavoriteCat(catId: Int)
    fun removeFavoriteCat(catId: Int)
    fun isFavouriteCat(catId: Int): Single<Boolean>
    fun getFavoriteCats(): Observable<List<String>>
}