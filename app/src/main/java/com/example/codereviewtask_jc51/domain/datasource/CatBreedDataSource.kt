package com.example.codereviewtask_jc51.domain.datasource

import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain
import io.reactivex.rxjava3.core.Single

interface CatBreedDataSource {
    fun fetchCats(): Single<List<CatPreviewDomain>>

    fun fetchCat(catId: Int): Single<CatDetailsDomain>

    fun fetchCatFact(factId: Int): Single<String>
}