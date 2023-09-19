package org.texchtown.flightgame

import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {
    private val TAG = "GameActivity"
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //get screen size
        val displayMetrics = DisplayMetrics()
        // on below line we are getting metrics for display using window manager.
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // on below line we are getting height
        // and width using display metrics.
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        Log.d(TAG, "x: " + height.toString() + "y: " + width.toString())

        //make a gameView object using context and screen size
        gameView = GameView(this, width, height)
        setContentView(gameView)


        val handler = Handler()

        val r: Runnable = object : Runnable {
            override fun run() {
                Log.d(TAG, "run: "  + "x")
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(r, 1000)

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