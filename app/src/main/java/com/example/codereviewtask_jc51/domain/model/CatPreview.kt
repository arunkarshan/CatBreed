package com.example.codereviewtask_jc51.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CatPreview(
    val id: Int,
    @JsonProperty(value = "breed_name")
    val breedName: String,
    val picture: String,
    val rate: Double
) {
    var favorite = false
}