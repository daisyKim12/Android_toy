package org.texchtown.cgv.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.texchtown.cgv.R
import org.texchtown.cgv.adapter.EventAdapter
import org.texchtown.cgv.adapter.EventTopAdvAdapter
import org.texchtown.cgv.adapter.HomeTopAdvAdapter
import org.texchtown.cgv.data.adv.PageLists
import org.texchtown.cgv.databinding.FragmentEventBinding
import java.util.*

class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding
    private var viewPager2: ViewPager2? = null

    //auto slide
    private var currentPage: Int = 0
    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        eventTabSetting()
        setUpViewPager()
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpViewPager() {
        val adapter = EventTopAdvAdapter(PageLists.eventAdvSlides)
        viewPager2 = binding.viewPagerTopAdv
        viewPager2?.offscreenPageLimit=3
        viewPager2?.adapter = adapter


        var transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(22))

        viewPager2?.setPageTransformer(transform)

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
        binding.rvEvent.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvEvent.layoutManager = linearLayoutManager
        val adapter = EventAdapter(PageLists.eventSlides)
        adapter.notifyDataSetChanged()
        binding.rvEvent.adapter = adapter

    }

    private fun eventTabSetting() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Special"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("영화"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("맴버십/Club"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("극장"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("제휴"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("지난이벤트"))
    }
}