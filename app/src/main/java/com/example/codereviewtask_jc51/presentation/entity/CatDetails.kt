package com.example.codereviewtask_jc51.presentation.entity

import com.example.codereviewtask_jc51.R

data class CatDetails(
    val id: Int,
    val breedName: String,
    val picture: String,
    val rate: Double,
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
    val rating:Float = rate.toFloat()

    val favouriteImage
        get() = if (this.favorite) R.drawable.ic_far_heart_selected else R.drawable.ic_far_heart
}