package org.texchtown.done

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import org.texchtown.done.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    // widget
    var settingDialog: Dialog? = null
    var createDialog: Dialog? = null
    private lateinit var binding: ActivityMainBinding
    //var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingDialog = Dialog(this)
        createDialog = Dialog(this)

        //set toolbar
        setSupportActionBar(binding.toolbarMain)
        var actionBar: ActionBar? = null
        actionBar = supportActionBar

        //set status bar
        transparentStatusBar()

        //set listener for bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_nav_today -> onFragmentChanged(0)
            R.id.main_nav_total -> onFragmentChanged(1)
            R.id.main_nav_list -> onFragmentChanged(2)
        }
        return true
    }

    fun onFragmentChanged(Index: Int) {
        val todayFragment: Fragment = TodayFragment()
        val totalFragment: Fragment = TotalFragment()
        when (Index) {
            0 ->                 //replace container with today fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main, todayFragment).commit()
            1 -> supportFragmentManager.beginTransaction()
                .replace(R.id.container_main, totalFragment).commit()
            2 -> {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemSelected = item.itemId
        when (itemSelected) {
            R.id.main_toolbar_setting -> {
                Log.d(TAG, "onOptionsItemSelected: setting clicked")
                openSetting()
            }
            R.id.main_toolbar_add -> {
                Log.d(TAG, "onOptionsItemSelected: add clicked")
                openAddHabit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openSetting() {
        val settingDialog = SettingDialog()
        settingDialog.show(supportFragmentManager, "setting")
    }

    fun openAddHabit() {
        val habitDialog = HabitDialog()
        habitDialog.show(supportFragmentManager, "create new habit")
    }

    fun transparentStatusBar() {
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
    }

    fun moveToHabitSpecific(name: String?) {
        val intent = Intent(this@MainActivity, SubActivity::class.java)
        intent.putExtra("swiped element", name)
        startActivity(intent)
    }

    fun moveToGroup(group: String?) {
        //move to fragment 2 to show habits inside a perticular group
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}