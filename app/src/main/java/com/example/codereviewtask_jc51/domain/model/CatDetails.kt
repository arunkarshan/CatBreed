package com.example.codereviewtask_jc51.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CatDetails(
    val id: Int,
    @JsonProperty(value = "breed_name")
    val breedName: String,
    val picture: String,
    val rate: Double,
    @JsonProperty(value = "fun_fact")
    val funFact: String,
    val lifespan: String,
    val origin: String
) {

    companion object {
        val EMPTY = CatDetails(
            id = -100500,
            breedName = "empty",
            picture = "none",
            rate = 0.0,
            funFact = "placeholder",
            lifespan = "forever",
            origin = "universe"
        )
    }

    var favorite = false
}