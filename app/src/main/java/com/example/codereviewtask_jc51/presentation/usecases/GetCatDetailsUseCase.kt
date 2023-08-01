package com.example.codereviewtask_jc51.presentation.usecases

import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import com.example.codereviewtask_jc51.presentation.di.IoDispatcher
import com.example.codereviewtask_jc51.presentation.entity.CatDetails
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.entity.toUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.rx3.asFlow
import javax.inject.Inject

class GetCatDetailsUseCase @Inject constructor(
    private val catRepository: CatRepository,
    private val catFavouriteRepository: CatFavouriteRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseFlowUseCase<GetCatDetailsUseCase.Params, CatDetails> {
    override suspend fun invoke(params: Params): Flow<CatDetails> {
        return Singles.zip(
            catRepository.fetchCat(catId = params.catId).map { it.toUi() } ,
            catFavouriteRepository.isFavouriteCat(catId = params.catId)
        ).map { (cat, isFavourite) ->
                cat.apply{favorite = isFavourite}
            }.toObservable().asFlow().flowOn(dispatcher)
    }

    data class Params(val catId:Int)
}