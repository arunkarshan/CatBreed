package com.example.codereviewtask_jc51.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher

interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(params: Parameter): Result
}
