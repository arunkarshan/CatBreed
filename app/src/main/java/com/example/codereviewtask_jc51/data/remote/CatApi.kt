package com.example.codereviewtask_jc51.data.remote

import com.example.codereviewtask_jc51.data.remote.dto.CatDetailsResponse
import com.example.codereviewtask_jc51.data.remote.dto.CatFactResponse
import com.example.codereviewtask_jc51.data.remote.dto.CatsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CatApi {

    @GET("breeds")
    fun getCats(): Single<CatsResponse>

    @GET("breed/{catId}")
    fun getCatDetails(
        @Path("catId") catId: Int
    ): Single<CatDetailsResponse>

    @GET("fact/{factId}")
    fun getCatFact(
        @Path("factId") factId: Int
    ): Single<CatFactResponse>
}