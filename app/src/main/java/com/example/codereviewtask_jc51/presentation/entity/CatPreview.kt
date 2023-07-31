package com.example.codereviewtask_jc51.presentation.entity

import com.example.codereviewtask_jc51.R
import com.fasterxml.jackson.annotation.JsonProperty

data class CatPreview(
    val id: Int,
    @JsonProperty(value = "breed_name")
    val breedName: String,
    val picture: String,
    val rate: Double
) {
    var favorite = false

    val favouriteImage
        get() = if (this.favorite) R.drawable.ic_far_heart_selected else R.drawable.ic_far_heart
}