package com.example.assignmentskillforge.network

import com.example.assignmentskillforge.data.SkillforgeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface SkillforgeApiService {
    @GET
    suspend fun getSkillforgeData(@Url url: String): SkillforgeResponse
}

object RetrofitClient {
    // A placeholder base URL (Retrofit requires a valid base URL, even if we pass a full URL dynamically)
    private const val BASE_URL = "https://api.github.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: SkillforgeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SkillforgeApiService::class.java)
    }
}
