package com.example.codereviewtask_jc51.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow


interface BaseFlowUseCase<in Parameter, out Result> {
    suspend operator fun invoke(params: Parameter): Flow<Result>
}
