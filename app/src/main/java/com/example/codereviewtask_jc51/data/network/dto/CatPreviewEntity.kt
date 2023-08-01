package com.example.codereviewtask_jc51.data.network.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CatPreviewEntity(
    val id: Int?,
    @JsonProperty(value = "breed_name")
    val breedName: String?,
    val picture: String?,
    val rate: Double?
)