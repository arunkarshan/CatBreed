package com.example.codereviewtask_jc51.presentation.entity

import com.example.codereviewtask_jc51.data.network.dto.CatDetailsEntity
import com.example.codereviewtask_jc51.data.network.dto.CatPreviewEntity
import com.example.codereviewtask_jc51.domain.model.CatDetailsDomain
import com.example.codereviewtask_jc51.domain.model.CatPreviewDomain

fun CatPreviewDomain.toUi() =  CatPreview(
    id = id,
    breedName = breedName,
    picture = picture,
    rate = rate
)

fun CatDetailsDomain.toUi() = CatDetails(
    id = id,
    breedName = breedName,
    picture = picture,
    rate = rate,
    funFact = funFact,
    lifespan = lifespan,
    origin = origin
)