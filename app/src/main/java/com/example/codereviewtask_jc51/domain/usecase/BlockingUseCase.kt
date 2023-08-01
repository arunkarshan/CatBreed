package com.example.codereviewtask_jc51.domain.usecase

interface BlockingUseCase<in Parameter, out Result> {
    operator fun invoke(params: Parameter): Result
}
