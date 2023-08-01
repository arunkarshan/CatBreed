package com.example.codereviewtask_jc51.presentation.usecases

import com.example.codereviewtask_jc51.domain.repository.CatFavouriteRepository
import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import com.example.codereviewtask_jc51.domain.usecase.BaseUseCase
import com.example.codereviewtask_jc51.domain.usecase.BlockingUseCase
import com.example.codereviewtask_jc51.presentation.entity.CatPreview
import com.example.codereviewtask_jc51.presentation.entity.toUi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.Singles
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.rx3.asFlow
import javax.inject.Inject

class SetCatFavouriteStatusUseCase @Inject constructor(
    private val catFavouriteRepository: CatFavouriteRepository
): BlockingUseCase<SetCatFavouriteStatusUseCase.Params, Unit> {
    override fun invoke(params: Params) {
        return catFavouriteRepository.run{
            if(params.favourite) {
                addFavoriteCat(params.catId)
            } else {
                removeFavoriteCat(params.catId)
            }
        }
    }

    data class Params(val catId:Int, val favourite: Boolean)
}