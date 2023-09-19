package org.texchtown.ibk_bank_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.texchtown.ibk_bank_home.databinding.ActivityAnimationBinding
import org.texchtown.ibk_bank_home.databinding.ActivityLockBinding

class LockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        binding.button.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}