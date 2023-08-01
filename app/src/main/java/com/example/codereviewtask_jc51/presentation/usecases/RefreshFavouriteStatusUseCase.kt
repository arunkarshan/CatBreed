package com.example.codereviewtask_jc51.presentation.usecases

import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import com.example.codereviewtask_jc51.presentation.di.IoDispatcher
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx3.asFlow
import javax.inject.Inject

class RefreshFavouriteStatusUseCase @Inject constructor(
    private val catFavouriteRepository: CatFavouriteRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseFlowUseCase<RefreshFavouriteStatusUseCase.Params, List<CatPreview>> {
    override suspend fun invoke(params: Params): Flow<List<CatPreview>> {
        return  catFavouriteRepository.getFavoriteCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .asFlow().map { favs ->
                params.breeds.map {
                    it.apply{ favorite = id.toString() in  favs}
                }
            }.flowOn(dispatcher)
    }

    data class Params(val breeds: List<CatPreview>)
}