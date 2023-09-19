package org.texchtown.cgv.fragment

import android.R
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.texchtown.cgv.adapter.EventAdapter
import org.texchtown.cgv.adapter.MovieTalkAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.data.youtube.Item
import org.texchtown.cgv.data.youtube.youtubeResponse
import org.texchtown.cgv.databinding.FragmentMovieTalkBinding
import org.texchtown.retrofit_demo.RetrofirInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieTalkFragment : Fragment() {

    private lateinit var binding: FragmentMovieTalkBinding
    private val part= "snippet"
    private val maxResult = 10
    private lateinit var query: String
    private val type = "video"
    private val key = "AIzaSyDMevaHIFyfALOM8ERiO5RKGv7S5koKHJw"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieTalkBinding.inflate(inflater, container, false)
        query = "최신 영화 후기"
        getYoutubeData(part, maxResult, query, type, key)
        return binding.root
    }

//    private fun setUpRecyclerView() {
//        binding.rvMovieTalk.setHasFixedSize(true)
//        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.rvMovieTalk.layoutManager = linearLayoutManager
//        val adapter = MovieTalkAdapter(requireContext(), PageLists.movieTalkSlide)
//        adapter.notifyDataSetChanged()
//        binding.rvMovieTalk.adapter = adapter
//
//    }

    private fun getYoutubeData(part: String, maxResult: Int, query: String, type: String, key: String) {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()

        RetrofirInstance.youtubeAppInterface.getVideo(part, maxResult, query, type, key).enqueue(object : Callback<youtubeResponse?> {
            override fun onResponse(
                call: Call<youtubeResponse?>,
                response: Response<youtubeResponse?>
            ) {
                if(response.isSuccessful){
                    val response = response.body() as youtubeResponse
                    val items: List<Item> = response.items
                    Log.d(TAG, "movietalk: " + items)

                    binding.rvMovieTalk.setHasFixedSize(true)
                    val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    binding.rvMovieTalk.layoutManager = linearLayoutManager
                    val adapter = MovieTalkAdapter(requireContext(), items)
                    adapter.notifyDataSetChanged()
                    binding.rvMovieTalk.adapter = adapter


                } else {
                    Log.d(ContentValues.TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<youtubeResponse?>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })
    }

}


//        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
//        lifecycle.addObserver(youTubePlayerView)
//
//        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//            override fun onReady(youTubePlayer: YouTubePlayer) {
//                val videoId = "gXWXKjR-qII"
//                youTubePlayer.loadVideo(videoId, 100.toFloat())
//            }
//        })