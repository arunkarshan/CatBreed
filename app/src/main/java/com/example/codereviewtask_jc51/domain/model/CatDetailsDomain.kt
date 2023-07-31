package com.example.codereviewtask_jc51.domain.model

data class CatDetailsDomain(
    val id: Int,
    val breedName: String,
    val picture: String,
    val rate: Double,
    val funFact: String,
    val lifespan: String,
    val origin: String
)