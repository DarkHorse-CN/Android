package com.darkhorse.android.lib.network

import com.darkhorse.android.core.network.ApiFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * OkHttp + Retrofit 的 ApiFactory 实现
 */
@Singleton
class OkHttpApiFactory @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : ApiFactory {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val defaultRetrofit: Retrofit by lazy {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    override fun <T : Any> create(apiClass: Class<T>, baseUrl: String?): T {
        return if (baseUrl != null) {
            val contentType = "application/json".toMediaType()
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory(contentType))
                .build()
                .create(apiClass)
        } else {
            defaultRetrofit.create(apiClass)
        }
    }
}
