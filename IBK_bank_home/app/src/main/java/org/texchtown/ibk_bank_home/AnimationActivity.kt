package org.texchtown.ibk_bank_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import org.texchtown.ibk_bank_home.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        val animationBounceUp = AnimationUtils.loadAnimation(this, R.anim.bounce_up)
        val animationBounceDown = AnimationUtils.loadAnimation(this, R.anim.bounce_down)

        binding.bounceImg.startAnimation(animationBounceUp)

        val toLockPage = Intent(this,LockActivity::class.java)

        animationBounceUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(toLockPage)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }
}