package com.example.codereviewtask_jc51

import com.example.codereviewtask_jc51.data.CatRepository
import com.example.codereviewtask_jc51.data.FavoriteCatSaver

object ApplicationDependencies {
    lateinit var catRepository: CatRepository
    lateinit var favoriteCatSaver: FavoriteCatSaver
}
