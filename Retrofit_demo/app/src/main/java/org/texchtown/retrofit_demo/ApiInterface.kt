package org.texchtown.retrofit_demo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    ///searchWeeklyBoxOfficeList.json?
    //key=f5eef3421c602c6cb7ea224104795888&targetDt=20230212&weekGb=0&multiMovieYn=N
    @GET("searchWeeklyBoxOfficeList.json?")
    fun getData(@Query("key") key:String,
            @Query("targetDt")targetDt:String,
            @Query("weekGb") weekGb:String,
            @Query("multiMovieYn") multiMovieYn: String
    ): Call<responceDataClass>

}