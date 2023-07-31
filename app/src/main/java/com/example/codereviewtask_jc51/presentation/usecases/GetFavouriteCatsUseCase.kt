package com.example.codereviewtask_jc51.presentation.usecases

import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.entity.toUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.rx3.asFlow
import javax.inject.Inject

class GetFavouriteCatsUseCase @Inject constructor(
    private val catRepository: CatRepository,
    private val catFavouriteRepository: CatFavouriteRepository
) : BaseFlowUseCase<Unit, List<CatPreview>> {
    override suspend fun invoke(params: Unit): Flow<List<CatPreview>> {
        return Singles.zip(
            catRepository.fetchCats().map { it.map { cat -> cat.toUi() } },
            catFavouriteRepository.getFavoriteCats().firstOrError()
        ).map { (cats, favoriteIds) ->
            cats.filter { it.id.toString() in favoriteIds }.map { it.apply { favorite = true } }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable().asFlow()
    }
}