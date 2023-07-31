package com.example.codereviewtask_jc51.data.repository

import com.example.codereviewtask_jc51.data.network.dto.CatDetailsEntity
import com.example.codereviewtask_jc51.data.network.dto.CatPreviewEntity
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain

fun CatPreviewEntity.toDomain() = id?.let {
    CatPreviewDomain(
        id = it,
        breedName = breedName ?: "",
        picture = picture ?: "",
        rate = rate ?: 0.0
    )
}

fun CatDetailsEntity.toDomain() = CatDetailsDomain(
    id = id,
    breedName = breedName ?: "",
    picture = picture ?: "",
    rate = rate ?: 0.0,
    funFact = funFact ?: "",
    lifespan = lifespan ?: "",
    origin = origin ?: ""
)