package org.texchtown.cgv.fragment

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.texchtown.cgv.activity.MovieActivity
import org.texchtown.cgv.adapter.BottomSheetAdvAdapter
import org.texchtown.cgv.adapter.HomeTopAdvAdapter
import org.texchtown.cgv.adapter.MovieAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.data.boxoffice.WeeklyBoxOffice
import org.texchtown.cgv.data.boxoffice.boxOfficeResponse
import org.texchtown.cgv.databinding.BottomSheetLayoutBinding
import org.texchtown.cgv.databinding.FragmentHomeBinding
import org.texchtown.cgv.viewmodel.ScrollViewModel
import org.texchtown.retrofit_demo.RetrofirInstance
import retrofit2.*
import java.util.*


class HomeFragment : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private var viewPager2: ViewPager2? = null

    //auto slide
    private var currentPage: Int = 0
    private lateinit var timer: Timer

    //scroll Listener
    private lateinit var viewModel: ScrollViewModel

    //http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20230212&weekGb=0&multiMovieYn=N
    private val key = "b1a88557ed769d9424d91c407b517386"
    private val targetDt = "20230212"
    private val weekGb = "0"
    private val multiMovieYn = "N"
    //List
    var weeklyBoxOfficeList = listOf<WeeklyBoxOffice>()
//    var movieSearchList = mutableListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        movieTabSetting()
        setUpViewPager()
        showBottomSheetAdv()
        setUpRecyclerView()

        getBoxOfficeData(key, targetDt, weekGb, multiMovieYn)
        Log.d(TAG, "onResume: "+ weeklyBoxOfficeList)


        return binding.root
    }

    private fun setUpViewPager() {
        val adapter = HomeTopAdvAdapter(PageLists.homeAdvSlides)
        viewPager2 = binding.viewPagerTopAdv
        viewPager2?.adapter = adapter

        //auto slide using muti threading
        val handler = Handler()
        val Update = Runnable {
//            if (currentPage == 3) {
//                currentPage = 0
//            }
            binding.viewPagerTopAdv.setCurrentItem(currentPage++, true)
        }

        timer = Timer() // This will create a new Thread

        timer.schedule(object : TimerTask() {
            // task to be scheduled
            override fun run() {
                handler.post(Update)
            }
        }, 500, 3000)
    }

    private fun setUpRecyclerView() {
        binding.rvMovie.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovie.layoutManager = linearLayoutManager

    }


    override fun onResume() {
        super.onResume()



        viewModel = ViewModelProvider(requireActivity()).get(ScrollViewModel::class.java)
        binding.root.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            viewModel.setData(oldScrollY)
        }

    }


    private fun movieTabSetting() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("예매차트"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("현재상영작"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("ICECON"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("아트하우스"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("CGV Only"))
    }


    private fun showBottomSheetAdv() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val binding: BottomSheetLayoutBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)

        val adapter = BottomSheetAdvAdapter(PageLists.bottomSlides)
        viewPager2 = binding.viewPagerBottom
        viewPager2?.adapter = adapter

        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(2))

        viewPager2?.setPageTransformer(transform)

        binding.txtDismiss.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun getBoxOfficeData(key: String, targetDt: String, weekGb: String, multiMovieYn: String){

        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please wait while data is fetch")
        progressDialog.show()

        RetrofirInstance.boxAppInterface.getBoxOfficeData(key, targetDt, weekGb, multiMovieYn).enqueue(object :
            Callback<boxOfficeResponse?> {
            override fun onResponse(
                call: Call<boxOfficeResponse?>,
                response: Response<boxOfficeResponse?>
            ) {
                if(response.isSuccessful) {

                    Log.d(TAG, "onResponse: response successful")
                    val response = response.body() as boxOfficeResponse
                    val weeklyBoxOfficeList = response.boxOfficeResult.weeklyBoxOfficeList
                    Log.d(TAG, "onResponse: "+ weeklyBoxOfficeList)

                    //set adapter
                    if(weeklyBoxOfficeList != null) {
                        Log.d(TAG, "onCreateView: weeklyBoxOfficeList not null")
                        Log.d(TAG, "onCreateView: " + weeklyBoxOfficeList)
                        val adapter = MovieAdapter(requireContext(), this@HomeFragment,
                            weeklyBoxOfficeList, PageLists.moviePosterSlide)
                        adapter.notifyDataSetChanged()
                        binding.rvMovie.adapter = adapter
                    } else {
                        Log.d(TAG, "onCreateView: weeklyBoxOfficeList is null")
                    }

                } else {
                    Log.d(TAG, "onResponse: response not successful")
                }
                progressDialog.dismiss()

            }

            override fun onFailure(call: Call<boxOfficeResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: call fail")
                progressDialog.dismiss()
            }
        })
    }

    override fun onItemClick(movieName: String, movieCd: String, rating: Int) {
        Log.d(TAG, "onItemClick: clicked")
        val intent = Intent(activity, MovieActivity::class.java)
        //add movie name intent
        intent.putExtra("movieName", movieName)
        intent.putExtra("movieCode", movieCd)
        intent.putExtra("rating", rating)
        startActivity(intent)
    }

    override fun onFastTicketingClick(position: Int) {
        Log.d(TAG, "onFastTicketingClick: clicked")
        //new activity
    }
}