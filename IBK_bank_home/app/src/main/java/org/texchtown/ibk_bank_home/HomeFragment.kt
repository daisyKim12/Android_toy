package org.texchtown.ibk_bank_home

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import org.texchtown.ibk_bank_home.databinding.FragmentHomeBinding
import org.texchtown.ibk_bank_home.databinding.FragmentProductBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var viewPager2: ViewPager2? = null

    private val TAG = "HomeFragment"
    private val SCROLL_POSITION = "save_scroll_position"
    private var scrollPosition = 0

    //fragment has no onRestart so I am using a Flag
    //private var firstVisit = true

    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpViewPager(binding)
        Log.d(TAG, "onCreateView: ")

        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null) {
            scrollPosition = savedInstanceState?.getInt(SCROLL_POSITION, -1)
        }
    }

    override fun onResume() {
        super.onResume()
        //change advertisement photo
        moveViewPage(binding)

        binding.root.post(Runnable { binding.root.scrollTo(0, scrollPosition) })
        binding.root.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            scrollPosition = scrollY
        }
        Log.d(TAG, "onResume: ")
        Log.d(TAG, scrollPosition.toString())
    }

    //fragment 사이 왕복은 여기까지만
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
        Log.d(TAG, scrollPosition.toString())
    }



    //여긴 home으로 나갈 때만 불림
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION, scrollPosition)
        Log.d(TAG, "onSaveInstanceState: ")
        Log.d(TAG, scrollPosition.toString())
    }

    private fun setUpViewPager(binding: FragmentHomeBinding){
        val adapter = HomeAdapter(PageLists.homeSlides)
        viewPager2 = binding.viewPager
        viewPager2?.adapter = adapter
        //viewPager2?.registerOnPageChangeCallback(pager2Callback)
    }

    private fun moveViewPage(binding: FragmentHomeBinding){
        val adapter = HomeAdapter(PageLists.homeSlides)
        viewPager2 = binding.viewPager
        viewPager2?.adapter = adapter
        var past = viewPager2?.currentItem
        if(past != null) {
           when(past){
               0 -> viewPager2?.setCurrentItem(1)
               1 -> viewPager2?.setCurrentItem(0)
           }
        }
    }

}