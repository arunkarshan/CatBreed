package com.example.codereviewtask_jc51.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CatDetails(
    val id: Int,
    @JsonProperty(value = "breed_name")
    val breedName: String,
    val picture: String,
    val rate: Double,
    val fun_fact: String,
    val lifespan: String,
    val origin: String
) {

    companion object {
        val EMPTY = CatDetails(
            id = -100500,
            breedName = "empty",
            picture = "none",
            rate = 0.0,
            fun_fact = "placeholder",
            lifespan = "forever",
            origin = "universe"
        )
    }

    var favorite = false
}