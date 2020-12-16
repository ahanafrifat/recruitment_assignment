package com.aah.recruitmentassignment.network.retrofit

import com.aah.recruitmentassignment.utils.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val REQUEST_TIMEOUT = 5
    private var retrofit: Retrofit? = null
    private val apiClient: Retrofit?
        private get() {
            if (retrofit == null) {
                val okHttpClientBuilder = OkHttpClient.Builder()
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                okHttpClientBuilder.addInterceptor(logging)
                okHttpClientBuilder
                        .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                        .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                        .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                        .build()

                val ignoreUnknownProperties = GsonBuilder().setExclusionStrategies().create()

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(ignoreUnknownProperties))
                        .client(okHttpClientBuilder.build())
                        .build()
            }
            return retrofit
        }
    val api: Api
        get() = apiClient!!.create(Api::class.java)
}