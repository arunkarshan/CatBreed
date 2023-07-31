package com.example.codereviewtask_jc51.data

import android.content.Context
import io.reactivex.rxjava3.core.Observable

class FavoriteCatSaver(
    private val context: Context
) {
    private val preferences = context.getSharedPreferences("favorite_cats", Context.MODE_PRIVATE)

    fun addFavoriteCat(catId: Int) {
        preferences.edit().let {
            it.putInt("id", catId)
            it.apply()
        }
    }

    fun getFavoriteCat(): Observable<Int> {
        return Observable.just(
            preferences.getInt("id", 0)
        )
    }
}