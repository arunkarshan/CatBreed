package com.example.codereviewtask_jc51.data.datasource

import com.example.codereviewtask_jc51.domain.datasource.CatBreedDataSource
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain
import io.reactivex.rxjava3.core.Single

class CatBreedLocalDataSource: CatBreedDataSource {

    // When caching is enabled, provide the cached data using the local data source

    override fun fetchCats(): Single<List<CatPreviewDomain>> {
        throw RuntimeException("This method should not be called from Local")
    }

    override fun fetchCat(catId: Int): Single<CatDetailsDomain> {
        throw RuntimeException("This method should not be called from Local")
    }

    override fun fetchCatFact(factId: Int): Single<String> {
        throw RuntimeException("This method should not be called from Local")
    }
}