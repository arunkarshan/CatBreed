package com.example.codereviewtask_jc51.presentation.usecases

import com.example.codereviewtask_jc51.domain.repository.CatRepository
import com.example.codereviewtask_jc51.domain.usecase.BaseFlowUseCase
import com.example.codereviewtask_jc51.presentation.di.IoDispatcher
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.rx3.asFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetCatFactsUseCase @Inject constructor(
    private val catRepository: CatRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseFlowUseCase<GetCatFactsUseCase.Params, String> {
    override suspend fun invoke(params: Params): Flow<String> {
        return Observable.intervalRange(
            params.start,
            params.count,
            params.initialDelay,
            params.period,
            TimeUnit.SECONDS,
            Schedulers.computation()
        )
            .repeat()
            .flatMap { catRepository.fetchCatFact(it.toInt()).toObservable() }
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .asFlow().flowOn(dispatcher)
    }

    data class Params(
        val start:Long,
        val count:Long,
        val initialDelay:Long,
        val period:Long,
    )
}