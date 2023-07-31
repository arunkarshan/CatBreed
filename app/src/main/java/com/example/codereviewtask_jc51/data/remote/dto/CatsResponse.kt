package com.example.codereviewtask_jc51.data.remote.dto

import com.example.codereviewtask_jc51.domain.model.CatPreview

data class CatsResponse(
    val status: Int,
    val data: List<CatPreview>
)