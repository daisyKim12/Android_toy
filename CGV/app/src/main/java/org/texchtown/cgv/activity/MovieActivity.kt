package org.texchtown.cgv.activity

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import org.texchtown.cgv.R
import org.texchtown.cgv.adapter.EventAdapter
import org.texchtown.cgv.adapter.MovieTalkAdapter
import org.texchtown.cgv.adapter.StealShotAdapter
import org.texchtown.cgv.adapter.TrailerAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.data.moviedetail.MovieInfo
import org.texchtown.cgv.data.moviedetail.movieDetailResponse
import org.texchtown.cgv.data.navermovie.Item
import org.texchtown.cgv.data.navermovie.naverMovieSearchResponse
import org.texchtown.cgv.data.naverstealshot.naverStealShotResponse
import org.texchtown.cgv.data.summary.summaryResponse
import org.texchtown.cgv.data.youtube.youtubeResponse
import org.texchtown.cgv.databinding.ActivityMovieBinding
import org.texchtown.retrofit_demo.RetrofirInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class MovieActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMovieBinding
    //naver? movie search
    private lateinit var movieName: String
    private lateinit var movieCode: String
    private var rating: Int = 0
    private val key = "58a935c25ef014f930c9df8032340dd4"
    //youtube search
    private val part= "id"
    private val maxResult = 4
    private lateinit var query: String
    private val type = "video"
    private val key_youtube = "AIzaSyDMevaHIFyfALOM8ERiO5RKGv7S5koKHJw"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = org.texchtown.cgv.databinding.ActivityMovieBinding.inflate(layoutInflater)
        movieName = intent.getStringExtra("movieName").toString()
        movieCode = intent.getStringExtra("movieCode").toString()
        rating = intent.getIntExtra("rating", -1).toInt()
        binding.tbTitle.text = movieName
        setContentView(binding.root)

        addTab()

        binding.tbBack.setOnClickListener(this)

        getNaverMovieData(movieName)
        getMovieDetailDataByCode(key, movieCode)
        query = movieName + "trailer"
        //setActor(rating)
        getTrailerData(part, maxResult, query,type, key_youtube)

    }

    private fun addTab() {
        //add tab in tab layout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("상세정보"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("실관람평(0)"))

        //add tab listener
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tb_back -> finish()
        }
    }

//    private fun setActor(rating: Int) {
//        val actorList = PageLists.actorList[rating]
//        var cnt = 0
//        for(actor in actorList) {
//            RetrofirInstance.naverAppInterface.getStealShotPhoto(actor+ " 인물 사진", 1).enqueue(object : Callback<naverStealShotResponse?> {
//                override fun onResponse(
//                    call: Call<naverStealShotResponse?>,
//                    response: Response<naverStealShotResponse?>
//                ) {
//                    if(response.isSuccessful){
//                        val response = response.body() as naverStealShotResponse
//                        val itemX = response.items
//                        when(cnt){
//                            0-> Glide.with(this@MovieActivity).load(itemX[0].link).into(binding.imgActor1)
//                            1-> Glide.with(this@MovieActivity).load(itemX[0].link).into(binding.imgActor2)
//                            2-> Glide.with(this@MovieActivity).load(itemX[0].link).into(binding.imgActor3)
//                            3-> Glide.with(this@MovieActivity).load(itemX[0].link).into(binding.imgActor4)
//                        }
//                    } else {
//                        Log.d(TAG, "onResponse: response not successful")
//                    }
//                }
//                override fun onFailure(call: Call<naverStealShotResponse?>, t: Throwable) {
//                    Log.d(TAG, "onFailure: call fail")
//                }
//            })
//
//            when(cnt){
//                0-> binding.txtActor1.text = actorList[0]
//                1-> binding.txtActor2.text = actorList[1]
//                2-> binding.txtActor3.text = actorList[2]
//                3-> binding.txtActor4.text = actorList[3]
//            }
//            cnt++
//        }
//    }

    private fun getTrailerData(part: String, maxResult: Int, query: String, type: String, key: String) {
       RetrofirInstance.youtubeAppInterface.getVideo(part, maxResult, query, type, key).enqueue(object : Callback<youtubeResponse?> {
           override fun onResponse(
               call: Call<youtubeResponse?>,
               response: Response<youtubeResponse?>
           ) {
               if(response.isSuccessful){
                   val response = response.body() as youtubeResponse
                   val items: List<org.texchtown.cgv.data.youtube.Item> = response.items
                   Log.d(TAG, "movietalk: " + items)

                   binding.rvTrailer.setHasFixedSize(true)
                   val linearLayoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
                   binding.rvTrailer.layoutManager = linearLayoutManager
                   val adapter = TrailerAdapter(items)
                   adapter.notifyDataSetChanged()
                   binding.rvTrailer.adapter = adapter


               } else {
                   Log.d(ContentValues.TAG, "onResponse: response not successful")
               }
           }

           override fun onFailure(call: Call<youtubeResponse?>, t: Throwable) {
               Log.d(ContentValues.TAG, "onFailure: call fail")
           }
       })
   }

//    private fun setUpRecyclerView() {
//        binding.rvTrailer.setHasFixedSize(true)
//        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        binding.rvTrailer.layoutManager = linearLayoutManager
//        val adapter = TrailerAdapter(PageLists.trailerSlide)
//        adapter.notifyDataSetChanged()
//        binding.rvTrailer.adapter = adapter
//    }

   private fun getMovieDetailDataByCode(key: String, movieCode: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()

        RetrofirInstance.movieAppInterface.getMovieDetailData(key, movieCode).enqueue(object : Callback<movieDetailResponse?> {
            override fun onResponse(
                call: Call<movieDetailResponse?>,
                response: Response<movieDetailResponse?>
            ) {
                if(response.isSuccessful){
                    val response = response.body() as movieDetailResponse
                    val movieInfo: MovieInfo = response.movieInfoResult.movieInfo

                    val detail: String = movieInfo.openDt + ", " + movieInfo.prdtStatNm +
                            ", " + movieInfo.showTm + "분, "  + movieInfo.companys[0].companyNm + ", " + movieInfo.audits[0].watchGradeNm
                    //+ movieInfo.genres[0] + ", "
                    binding.txtDetail.text = detail

                 } else {
                     Log.d(TAG, "onResponse: response not successful")
                 }
                 progressDialog.dismiss()
            }

            override fun onFailure(call: Call<movieDetailResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })

    }

    private fun getNaverMovieData(movieName: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()

        RetrofirInstance.naverAppInterface.getNaverMovieData(movieName).enqueue(object :
            Callback<naverMovieSearchResponse?> {
            override fun onResponse(
                call: Call<naverMovieSearchResponse?>,
                response: Response<naverMovieSearchResponse?>
            ) {
                if(response.isSuccessful){
                    Log.d(TAG, "onResponse: reponse successful")
                    val response = response.body() as naverMovieSearchResponse
                    val item: Item = response.items[0]

                    Glide.with(this@MovieActivity).load(item.image).into(binding.imgPoster);
                    binding.txtTitle.text = movieName
                    binding.txtSubtitle.text = item.subtitle



                } else {
                    Log.d(TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<naverMovieSearchResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })


        RetrofirInstance.naverAppInterface.getStealShotPhoto(movieName+ " 스틸컷", 10).enqueue(object : Callback<naverStealShotResponse?> {
            override fun onResponse(
                call: Call<naverStealShotResponse?>,
                response: Response<naverStealShotResponse?>
            ) {
                if(response.isSuccessful){
                    val response = response.body() as naverStealShotResponse
                    val itemX = response.items
                    //set adapter
                    val adapter = StealShotAdapter(this@MovieActivity, itemX)
                    val viewPager2: ViewPager2 = binding.viewPagerPic
                    viewPager2.adapter = adapter

                    //set bottom grid view
                    Glide.with(this@MovieActivity).load(itemX[0].link).into(binding.imgSteal1)
                    Glide.with(this@MovieActivity).load(itemX[1].link).into(binding.imgSteal2)
                    Glide.with(this@MovieActivity).load(itemX[2].link).into(binding.imgSteal3)
                    Glide.with(this@MovieActivity).load(itemX[3].link).into(binding.imgSteal4)
                    Glide.with(this@MovieActivity).load(itemX[4].link).into(binding.imgSteal5)

                    for(i in 4 downTo 0) {
                        Log.d(TAG, "onResponse: "+ itemX[i].link)
                    }

                } else {
                    Log.d(TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<naverStealShotResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })

        RetrofirInstance.naverAppInterface.getSummary(movieName + " 줄거리").enqueue(object : Callback<summaryResponse?> {
            override fun onResponse(
                call: Call<summaryResponse?>,
                response: Response<summaryResponse?>
            ) {
                if (response.isSuccessful) {
                    val response = response.body() as summaryResponse
                    val item: org.texchtown.cgv.data.summary.Item = response.items[1]
                    Log.d(TAG, "onResponse: "+item)
                    binding.txtStory.text = item.description
                } else {
                    Log.d(TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<summaryResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })
    }

    private fun getHumanPic(search: String) {
        RetrofirInstance.naverAppInterface.getStealShotPhoto(search, 1).enqueue(object : Callback<naverStealShotResponse?> {
            override fun onResponse(
                call: Call<naverStealShotResponse?>,
                response: Response<naverStealShotResponse?>
            ) {
                if(response.isSuccessful){
                    val response = response.body() as naverStealShotResponse
                    val itemX = response.items

                } else {
                    Log.d(TAG, "getHumanPic: response not successful")
                }
            }

            override fun onFailure(call: Call<naverStealShotResponse?>, t: Throwable) {
                Log.d(TAG, "getHumanPic: call fail")
            }
        })
    }

}