package org.texchtown.flightgame

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import org.texchtown.flightgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //set full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(binding.root)

        binding.play.setOnClickListener {
            val intent= Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        val pref: SharedPreferences = getSharedPreferences("game", MODE_PRIVATE)
        binding.highScoreTxt.text = pref.getInt("highscore", 0).toString()
    }

}