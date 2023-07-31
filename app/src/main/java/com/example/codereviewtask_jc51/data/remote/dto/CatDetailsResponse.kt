package com.example.codereviewtask_jc51.data.remote.dto

import com.example.codereviewtask_jc51.domain.model.CatDetails

data class CatDetailsResponse(
    val status: Int,
    val data: CatDetails
)