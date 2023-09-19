package org.texchtown.takoyaki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import org.texchtown.takoyaki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.start.setImageResource(org.texchtown.takoyaki.R.drawable.btn_start_unclicked)

        binding.start.setOnClickListener {
            binding.start.setImageResource(org.texchtown.takoyaki.R.drawable.btn_start_clicked)
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}