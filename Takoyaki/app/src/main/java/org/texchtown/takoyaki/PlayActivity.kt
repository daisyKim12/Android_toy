package org.texchtown.takoyaki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager

class PlayActivity : AppCompatActivity() {

    private val TAG = "PlayActivity"
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //get screen size
        val displayMetrics = DisplayMetrics()
        // on below line we are getting metrics for display using window manager.
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // on below line we are getting height and width using display metrics.
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        Log.d(TAG, "x: " + height.toString() + "y: " + width.toString())

        //make a gameView object using context and screen size
        gameView = GameView(this, width, height)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView!!.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView!!.resume()
    }
}