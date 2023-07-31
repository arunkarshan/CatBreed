package com.example.codereviewtask_jc51.data.repository

import com.example.codereviewtask_jc51.data.di.RemoteDataSource
import com.example.codereviewtask_jc51.domain.datasource.CatBreedDataSource
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: CatBreedDataSource
): CatRepository {
    // When caching is implemented, these functions will have
    // to make the decisions from which datasource the data
    // has to be fetched.
    override fun fetchCats(): Single<List<CatPreviewDomain>> {
        return remoteDataSource.fetchCats()
    }

    override fun fetchCat(catId: Int): Single<CatDetailsDomain> {
        return remoteDataSource.fetchCat(catId)
    }

    override fun fetchCatFact(factId: Int): Single<String> {
        return remoteDataSource.fetchCatFact(factId)
    }
}