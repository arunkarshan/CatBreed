package com.example.codereviewtask_jc51.data.datasource

import android.content.Context
import com.example.codereviewtask_jc51.domain.datasource.CatFavouriteDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

const val CONST_FAVOURITE_KEY = "favourite-"


class CatFavouriteLocalDataSource constructor(
    context: Context
): CatFavouriteDataSource {

    private val preferences = context.getSharedPreferences("favorite_cats", Context.MODE_PRIVATE)

    override fun addFavoriteCat(catId: Int) {
        preferences.edit().apply {
            putBoolean("$CONST_FAVOURITE_KEY$catId", true)
        }.apply()
    }
    override fun removeFavoriteCat(catId: Int) {
        preferences.edit().apply {
            remove("$CONST_FAVOURITE_KEY$catId")
        }.apply()
    }

    override fun isFavouriteCat(catId: Int): Single<Boolean> {
        return Single.just(
            preferences.getBoolean("$CONST_FAVOURITE_KEY$catId", false)
        )
    }

    override fun getFavoriteCats(): Observable<List<String>> {
        return Observable.just(
            preferences.all
                .filter { it.key.contains(CONST_FAVOURITE_KEY) && it.value == true}
                .map { it.key.replace(CONST_FAVOURITE_KEY, "") }
        )
    }
}