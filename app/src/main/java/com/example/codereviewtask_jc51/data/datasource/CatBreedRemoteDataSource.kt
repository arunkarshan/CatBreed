package com.example.codereviewtask_jc51.data.datasource

import com.example.codereviewtask_jc51.data.network.CatApi
import com.example.codereviewtask_jc51.data.repository.toDomain
import com.example.codereviewtask_jc51.domain.datasource.CatBreedDataSource
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain
import io.reactivex.rxjava3.core.Single

class CatBreedRemoteDataSource(private val catsService: CatApi) : CatBreedDataSource {
    override fun fetchCats(): Single<List<CatPreviewDomain>> {
        return catsService.getCats()
            .map { it.data.mapNotNull { cat -> cat.toDomain() } }
    }

    override fun fetchCat(catId: Int): Single<CatDetailsDomain> {
        return catsService.getCatDetails(catId)
            .map { it.data.toDomain() }
    }

    override fun fetchCatFact(factId: Int): Single<String> {
        return catsService.getCatFact(factId)
            .map { it.data }
    }
}