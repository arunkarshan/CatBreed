package com.example.codereviewtask_jc51

import android.app.Application
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import timber.log.Timber

class CatApp: Application() {
    val catRepository by lazy {CatRepository()}
    val favoriteCatSaver by lazy {
        FavoriteCatSaver(this)
    }

    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
    }
}