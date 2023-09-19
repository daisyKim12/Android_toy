package org.texchtown.ibk_bank_home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import org.texchtown.ibk_bank_home.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val productFragment = ProductFragment()
    private val TAG = "MainActivity"
    private var RUN_ONCE = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        //initialize fragment and bottom navigation
        if(savedInstanceState == null) {
            replaceFragment(homeFragment)
            binding.bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
        }
        //transparentStatusBar()

        binding.bottomNavigationView.setOnItemSelectedListener(this)

        Log.d(TAG, "onCreate: ")

    }

    override fun onStart() {
    super.onStart()
        screenProtection("invisible")

        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
    super.onResume()
        screenProtection("invisible")

          Log.d(TAG, "onResume: ")

    }

    override fun onPause() {
    super.onPause()
        screenProtection("visible")
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
    super.onStop()
        screenProtection("visible")
        Log.d(TAG, "onStop: ")

    }

    override fun onRestart() {
    super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus)
            screenProtection("invisible")
        else
            screenProtection("visible")
    }

    private fun screenProtection(visibility : String) {
        when(visibility) {
            "visible" -> binding.protectionImg.visibility = View.VISIBLE
            "invisible" -> binding.protectionImg.visibility = View.INVISIBLE

        }
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.commit()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(homeFragment)
            R.id.nav_product -> replaceFragment(productFragment)
        }
        return true
    }


    private fun transparentStatusBar() {
    val window = this.window
    window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
    }

}