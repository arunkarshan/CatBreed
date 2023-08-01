package com.example.codereviewtask_jc51.data.network.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CatDetailsEntity(
    val id: Int,
    @JsonProperty(value = "breed_name")
    val breedName: String?,
    val picture: String?,
    val rate: Double?,
    @JsonProperty(value = "fun_fact")
    val funFact: String?,
    val lifespan: String?,
    val origin: String?
)