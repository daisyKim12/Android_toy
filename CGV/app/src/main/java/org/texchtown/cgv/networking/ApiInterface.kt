package org.texchtown.retrofit_demo

import org.texchtown.cgv.data.boxoffice.boxOfficeResponse
import org.texchtown.cgv.data.moviedetail.movieDetailResponse
import org.texchtown.cgv.data.navermovie.naverMovieSearchResponse
import org.texchtown.cgv.data.naverstealshot.naverStealShotResponse
import org.texchtown.cgv.data.summary.summaryResponse
import org.texchtown.cgv.data.youtube.youtubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    // searchWeeklyBoxOfficeList.json?
    // key=f5eef3421c602c6cb7ea224104795888&targetDt=20230212&weekGb=0&multiMovieYn=N
    @GET("searchWeeklyBoxOfficeList.json?")
    fun getBoxOfficeData(
        @Query("key") key:String,
        @Query("targetDt")targetDt:String,
        @Query("weekGb") weekGb:String,
        @Query("multiMovieYn") multiMovieYn: String
    ): Call<boxOfficeResponse>

    //http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/
    //searchMovieInfo.json?
    //key=58a935c25ef014f930c9df8032340dd4
    //&movieCd=20124079
    @GET("searchMovieInfo.json?")
    fun getMovieDetailData(
        @Query("key") key:String,
        @Query("movieCd")movieCd:String
    ): Call<movieDetailResponse>

    // https://openapi.naver.com/v1/search/
    // movie.json
    // ?query=앤트맨과 와스프: 퀀텀매니아
    @Headers(
        "X-Naver-Client-Id: vSM_VvrQVkm97KZjpadU",
        "X-Naver-Client-Secret: kxSGWoK4DW"
    )
    @GET("movie.json")
    fun getNaverMovieData(
        @Query("query") query: String
    ): Call<naverMovieSearchResponse>

    // https://openapi.naver.com/v1/search/
    // image
    // ?query=앤트맨과 와스프: 퀀텀매니아 스틸컷&display=5
    @Headers(
        "X-Naver-Client-Id: vSM_VvrQVkm97KZjpadU",
        "X-Naver-Client-Secret: kxSGWoK4DW"
    )
    @GET("image")
    fun getStealShotPhoto(
        @Query("query") query: String,
        @Query("display") display: Int
    ): Call<naverStealShotResponse>

    // https://openapi.naver.com/v1/search/
    // encyc.json
    // ?query=앤트맨과 와스프 줄거리

    @Headers(
        "X-Naver-Client-Id: vSM_VvrQVkm97KZjpadU",
        "X-Naver-Client-Secret: kxSGWoK4DW"
    )
    @GET("encyc.json")
    fun getSummary(
        @Query("query") query: String
    ): Call<summaryResponse>

    // https://www.googleapis.com/youtube/v3/
    // search
    // ?part=snippet&maxResults=4&q=앤트맨과 와스프&type=video&key=AIzaSyDMevaHIFyfALOM8ERiO5RKGv7S5koKHJw

    @GET("search")
    fun getVideo(
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("q") q: String,
        @Query("type") type: String,
        @Query("key") key: String
    ): Call<youtubeResponse>

}