package org.texchtown.retrofit_demo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofirInstance {

    private val boxOfficeBaseUrl = "https://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"
    private val boxOfficeMovieDetailBaseUrl ="https://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/"
    private val naverBaseUrl = "https://openapi.naver.com/v1/search/"
    private val youtubeBaseUrl = "https://www.googleapis.com/youtube/v3/"

    //youtube video search
    private val youtubeRetrofit by lazy {
        Retrofit.Builder().baseUrl(youtubeBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val youtubeAppInterface by lazy {
        youtubeRetrofit.create(ApiInterface::class.java)
    }


    //weekly box office
    private val boxRetrofit by lazy {
        Retrofit.Builder().baseUrl(boxOfficeBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val boxAppInterface by lazy {
        boxRetrofit.create(ApiInterface::class.java)
    }

    //box office movie detail
    private val movieRetrofit by lazy {
        Retrofit.Builder().baseUrl(boxOfficeMovieDetailBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieAppInterface by lazy {
        movieRetrofit.create(ApiInterface::class.java)
    }


    //naver movie search
    private val naverRetrofit by lazy {
        Retrofit.Builder().baseUrl(naverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val naverAppInterface by lazy {
        naverRetrofit.create(ApiInterface::class.java)
    }
}