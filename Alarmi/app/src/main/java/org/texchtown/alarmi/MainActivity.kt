package org.texchtown.alarmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import org.texchtown.alarmi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val alarmFragment = AlarmFragment()
    private val sleepingFragment = SleepingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize fragment and bottom navigation
        if(savedInstanceState == null) {
            replaceFragment(alarmFragment)
            binding.bn.menu.findItem(R.id.nav_alarm).isChecked = true
        }

        binding.bn.setOnItemSelectedListener(this)
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
            R.id.nav_alarm -> replaceFragment(alarmFragment)
            R.id.nav_night -> replaceFragment(sleepingFragment)
        }
        return true
    }
}