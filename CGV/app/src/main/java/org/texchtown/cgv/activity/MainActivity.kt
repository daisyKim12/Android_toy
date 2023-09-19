package org.texchtown.cgv.activity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.texchtown.cgv.R
import org.texchtown.cgv.adapter.FragmentAdapter
import org.texchtown.cgv.databinding.ActivityMainBinding
import org.texchtown.cgv.viewmodel.ScrollViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FragmentAdapter
    private lateinit var viewModel: ScrollViewModel

    //fab actions
//    private val fromBottom_up: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_btn_up)}
//    private val toBottom_up: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_btn_up)}

    //scroll
    private var top: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeStatusBar(R.color.transparent)
        setAdapterForFragment()
        setDrawerBtn()

    }

    override fun onResume() {
        super.onResume()
        listenFragmentScroll()
    }

    private fun setDrawerBtn() {
        binding.tbNav.setOnClickListener {
            binding.root.openDrawer(GravityCompat.END)
        }

        val header: View = binding.navView.getHeaderView(0)
        val btnLogin: View = header.findViewById(R.id.txt_login)
        btnLogin.setOnClickListener {
            Log.d(TAG, "setDrawerBtn: clicked")
        }

    }



    private fun setAdapterForFragment() {
        adapter = FragmentAdapter(supportFragmentManager, lifecycle)

        //add tab in tab layout
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("홈"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("이벤트"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("무비톡"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("패스트오더"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("기프트샵"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("@CGV"))

        binding.container.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab != null) {
                    binding.container.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.container.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    fun changeStatusBar(color: Int) {
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, color)
    }



    private fun listenFragmentScroll() {
        viewModel = ViewModelProvider(this).get(ScrollViewModel::class.java)
        viewModel.getSelectedScrollPosition().observe(this, Observer { item ->
            if(item > 50){
                //scroll down
                if(top == false) {
                    Log.d(ContentValues.TAG, "listenFragmentScroll: "+ item.toString())
                    binding.fabQuickBooking.y = 1630.toFloat()
                    binding.fabUp.visibility = View.VISIBLE
                    top = true
                }

            } else {
                //on top
                if(top == true) {
                    binding.fabQuickBooking.y = 1830.toFloat()
                    binding.fabUp.visibility = View.INVISIBLE
                    top = false
                }
            }
        })
    }



}