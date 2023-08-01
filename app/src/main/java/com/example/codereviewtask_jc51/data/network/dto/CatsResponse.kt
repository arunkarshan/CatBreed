package com.example.codereviewtask_jc51.data.network.dto

data class CatsResponse(
    val status: Int,
    val data: List<CatPreviewEntity>
)