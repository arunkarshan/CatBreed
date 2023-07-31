package com.example.codereviewtask_jc51.domain.model

data class CatPreview(
    val id: Int,
    val breed_name: String,
    val picture: String,
    val rate: Double
) {
    var favorite = false
}