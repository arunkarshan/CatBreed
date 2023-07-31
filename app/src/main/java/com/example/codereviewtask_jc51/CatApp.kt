package com.example.codereviewtask_jc51

import android.app.Application
import android.util.Log
import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver
import timber.log.Timber

class CatApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initialize()
    }

    private fun initialize() {
        ApplicationDependencies.catRepository = CatRepository()
        ApplicationDependencies.favoriteCatSaver = FavoriteCatSaver(this)
    }
}