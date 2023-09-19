package org.texchtown.retrofit_demo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofirInstance {

    private val baseUrl = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val appInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}