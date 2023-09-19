package org.texchtown.flightgame

import android.R.attr.bitmap
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList


// surfaceview is used for rapidly changing views
public class GameView(private val activity: GameActivity, screenX: Int, screenY: Int) :
    SurfaceView(activity), Runnable {

    private val TAG = "GameView"

    private var thread: Thread? = null
    private var isPlaying = false
    private val background1: Background
    private val background2: Background
    private val screenX: Int
    private val screenY: Int
    private val paint: Paint
    private val flight: Flight
    //bullets
    private val bullets: MutableList<Bullet>
    //birds
    private val birds: Array<Bird?>
    private val random: Random
    //game over
    private var isGameOver = false
    //score
    private var score = 0
    private val prefs: SharedPreferences


    companion object {
        var screenRatioX: Float? = null
        var screenRatioY: Float? = null
    }

    init {
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE)

        background1 = Background(screenX, screenY, resources)
        background2 = Background(screenX, screenY, resources)
        this.screenX = screenX
        this.screenY = screenY
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY
        background2.x = screenX

        paint = Paint()
        paint.setTextSize(128f)
        paint.setColor(Color.WHITE)

        flight = Flight(this, screenY, resources)

        bullets = ArrayList()

        birds = arrayOfNulls<Bird>(4)
        for (i in 0..3) {
            val bird = Bird(resources)
            birds[i] = bird
        }
        random = Random()
    }

    override fun run() {
        //only when the player is Playing while statement is run
        while(isPlaying) {
            update()            //update the position of animation
            draw()              //draw the view every while loop
            sleep()             //delay to make 60 frame for sec
        }
    }

    private fun update() {
        //this is for moving the background 10 pixel at a time
        background1.x -= (10 * screenRatioX!!).toInt()
        background2.x -= (10 * screenRatioX!!).toInt()
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX
        }

        //when the flight should go up flight.y is increased by 30dp
        if (flight.isGoingUp) {
            flight.y -= (30 * screenRatioY!!).toInt()
        }else {
            flight.y += (30 * screenRatioY!!).toInt()
        }
        //insure the flight go up and down the screen
        if (flight.y < 0) flight.y = 0
        if (flight.y >= screenY - flight.height) flight.y = screenY - flight.height

        //update for bullets
        val trash: MutableList<Bullet> = ArrayList()
        for (bullet in bullets) {
            if (bullet.x > screenX) {
                trash.add(bullet)
            }

            bullet.x += (50 * screenRatioX!!).toInt()

            //checking if the bird is hit

            for (bird in birds) {
                if(bird != null) {
                    if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())
                    ) {
                        score++
                        bird.x = -500
                        bullet.x = screenX + 500
                        bird.wasShot = true
                    }
                }
            }
        }
        for (bullet in trash) {
            bullets.remove(bullet)
        }

        //update for birds
        for (bird in birds) {
            if(bird != null) {
                bird.x -= bird.speed
                if (bird.x + bird.width < 0) {
                    if (!bird.wasShot) {
                        isGameOver = true
                        return
                    }
                    val bound = (30 * screenRatioX!!).toInt()
                    bird.speed = random.nextInt(bound)
                    if (bird.speed < 10 * screenRatioX!!) bird.speed = (10 * screenRatioX!!).toInt()
                    bird.x = screenX
                    bird.y = random.nextInt(screenY - bird.height)
                    bird.wasShot = false
                }
                //when rectangle around flight and bird intersect it detect an collision
                if (Rect.intersects(bird.getCollisionShape(), flight.getCollisionShape())) {
                    isGameOver = true
                    return
                }
            }
        }
    }
    private fun draw() {
        //this is where the image for the screen is made
        if (holder.surface.isValid) {  // this is for ensuring that surfaceView is successfully initialize
            val canvas: Canvas = holder.lockCanvas()    //getting the current screen
            //drawing the canvas to display on a screen using drawBitmap
            //image, x axis, y axis, paint class is needed
            canvas.drawBitmap(background1.background, null, Rect(background1.x-screenX/2, 0, background1.x+screenX/2, screenY),paint)
            canvas.drawBitmap(background2.background, null, Rect(background2.x-screenX/2, 0, background2.x+screenX/2, screenY), paint)

            //draw birds before is game over to prevent disappearing
            for (bird in birds) {
                canvas.drawBitmap(bird!!.getBird(), null, Rect(bird.x,bird.y,bird.x + bird.width,bird.y + bird.height), paint)
            }

            canvas.drawText(score.toString() + " ", screenX/ 2f, 164f, paint)

            //when game is over change the flight to crash
            if(isGameOver == true) {
                isPlaying = false
                //change flight to dead flight
                canvas.drawBitmap(flight.getDead(), null, Rect(flight.x,flight.y,flight.x + flight.width,flight.y + flight.height), paint )
                holder.unlockCanvasAndPost(canvas)
                //saving the high score in sharedpref
                saveIfHighScore()
                waitBeforeExiting()

                return
            }


            //draw flight in canvas late to show it above background
            canvas.drawBitmap(flight.getFlight(), null, Rect(flight.x,flight.y,flight.x + flight.width,flight.y + flight.height), paint)
            Log.d(TAG, "draw: " + "flight.x: " + flight.x.toString() + " flight.y: " + flight.y.toString())
            Log.d(TAG, "draw: " + "flight.width: " + flight.width.toString() + " flight.height: " + flight.height.toString())

            //draw bullets
            for (bullet in bullets) {
                canvas.drawBitmap(bullet.bullet, null, Rect(bullet.x, bullet.y, bullet.x+bullet.width, bullet.y+bullet.height), paint)
            }

            // show a canvas on a screen
            holder.unlockCanvasAndPost(canvas)
        }
    }
    private fun sleep() {
        try{
            Thread.sleep(17)
        } catch (e:InterruptedException) {
            e.printStackTrace()
        }
    }



    fun resume(){
        isPlaying = true
        thread= Thread(this)
        thread?.start()
    }

    fun pause() {
        isPlaying = false
        thread?.join()

    }

    //this is for moving the flight by touching the screen
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //check the touch action is left or right of the screen
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> if (event.x < screenX / 2) {
                //when left screen is touched make flight up
                flight.isGoingUp = true
            }
            MotionEvent.ACTION_UP -> {
                flight.isGoingUp = false
                //when right screen is touched make the flight shoot
                if (event.x > screenX / 2) {
                    flight.toShoot++
                }
            }
        }
        return true     //it has to return true to know just after the user touch the screen
    }

    //add a Bullet object inside a List
    fun newBullet() {
        val bullet = Bullet(resources)
        bullet.x = flight.x + flight.width
        bullet.y = flight.y + flight.height / 2
        bullets.add(bullet)
    }
    //saving high score
    private fun saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            val editor = prefs.edit()
            editor.putInt("highscore", score)
            editor.apply()
        }
    }
    //sleep the thread for 3sec and start the main activity and finish the game activity
    private fun waitBeforeExiting() {
        try {
            Thread.sleep(3000)
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}