package org.texchtown.ibk_bank_home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import org.texchtown.ibk_bank_home.databinding.FragmentHomeBinding
import org.texchtown.ibk_bank_home.databinding.FragmentProductBinding


class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private var viewPager2:ViewPager2? = null

    private val TAG = "ProductFragment"
    private val SCROLL_POSITION = "save_scroll_position"
    private var scrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(inflater, container, false)
        setUpViewPager(binding)
        Log.d(TAG, "onCreateView: ")
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null) {
            scrollPosition = savedInstanceState?.getInt(SCROLL_POSITION, -1)
        }
        Log.d(TAG, "onViewStateRestored: ")
    }

    override fun onResume() {
        super.onResume()

        //change advertisement photo
        //moveViewPage(binding)

        binding.root.post(Runnable { binding.root.scrollTo(0, scrollPosition) })
        binding.root.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            scrollPosition = scrollY
        }
        Log.d(TAG, "onResume: ")
        Log.d(TAG, scrollPosition.toString())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_POSITION, scrollPosition)
        Log.d(TAG, "onSaveInstanceState: ")
        Log.d(TAG, scrollPosition.toString())
    }

    private fun setUpViewPager(binding: FragmentProductBinding){
        val adapter = ProductAdapter(PageLists.introSlides)
        viewPager2 = binding.viewPager
        viewPager2?.adapter = adapter
        //viewPager2?.registerOnPageChangeCallback(pager2Callback)
        binding.dotsIndicator.setViewPager2(viewPager2!!)
    }

//    private fun moveViewPage(binding: FragmentProductBinding){
//        val adapter = ProductAdapter(PageLists.introSlides)
//        viewPager2 = binding.viewPager
//        viewPager2?.adapter = adapter
//        var past = viewPager2?.currentItem
//        if(past != null) {
//            when(past){
//                0 -> viewPager2?.setCurrentItem(1)
//                1 -> viewPager2?.setCurrentItem(2)
//                2 -> viewPager2?.setCurrentItem(3)
//                3 -> viewPager2?.setCurrentItem(0)
//            }
//        }
//    }


}