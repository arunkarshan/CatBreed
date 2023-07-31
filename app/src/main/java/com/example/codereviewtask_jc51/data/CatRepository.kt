package com.example.codereviewtask_jc51.data

import com.example.codereviewtask_jc51.data.remote.CatApi
import com.example.codereviewtask_jc51.data.remote.dto.CatsResponse
import com.example.codereviewtask_jc51.domain.model.CatDetails
import com.example.codereviewtask_jc51.domain.model.CatPreview
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleSource
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class CatRepository {

    companion object {
        private const val BASE_URL = "https://catbreeds.proxy.beeceptor.com/"
    }

    private var client = OkHttpClient
        .Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    private val catsService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            JacksonConverterFactory.create(
                jacksonObjectMapper().registerKotlinModule()
            )
        )
        .addCallAdapterFactory(
            RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())
        )
        .client(client)
        .build()
        .create(CatApi::class.java)

    fun fetchCats(): Single<List<CatPreview>> {
        return catsService.getCats()
            .compose(CatPreviewResponseExtractor)
    }

    fun fetchCat(catId: Int): Single<CatDetails> {
        return catsService.getCatDetails(catId)
            .map { it.data }
    }

    fun fetchCatFact(factId: Int): Single<String> {
        return catsService.getCatFact(factId)
            .map { it.data }
    }
}

object CatPreviewResponseExtractor: SingleTransformer<CatsResponse, List<CatPreview>> {
    override fun apply(upstream: Single<CatsResponse>): SingleSource<List<CatPreview>> {
        return upstream.map { it.data }
    }
}