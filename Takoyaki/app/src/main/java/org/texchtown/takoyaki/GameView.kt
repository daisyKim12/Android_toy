package org.texchtown.takoyaki

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import java.util.concurrent.TimeUnit

class GameView(private val activity: PlayActivity, screenX: Int, screenY: Int) :
    SurfaceView(activity), Runnable{

    private val TAG = "GameView"

    private var mainThread: Thread? = null
    private var isPlaying = false
    private val background: Background
    private val screenX: Int
    private val screenY: Int
    private val paint: Paint
    private val paintText1: Paint
    private val paintText2: Paint
    private var pourPressed: Boolean
    private var takoPressed: Boolean
    private var pickPressed: Boolean
    private var saucePressed: Boolean
    private var handPressed: Boolean
    private var takoyakoState: Bitmap


    //thread for game over
    val timer: Timer
    //
    private var score = 0

    //human
    private var humanId = 1
    private val human: Human
    private var humanCounter = 0

    //button
    private var button: Button

    //tako
    private var tako: Tako


    init {
        background = Background(screenX, screenY, resources)
        this.screenX = screenX
        this.screenY = screenY
        screenRatioX = 1920f / screenX
        screenRatioY = 1080f / screenY

        human = Human(screenX, resources)

        button = Button(screenX, screenY, resources)

        paint = Paint()
        //init text style
        paintText1 = Paint()
        paintText2 = Paint()
        paintText1.setTextSize(90f)
        paintText1.setColor(Color.BLACK)
        paintText2.setTextSize(128f)
        paintText2.setColor(Color.BLACK)

        //button pressed init
        pourPressed = true
        takoPressed = false
        pickPressed = false
        saucePressed = false
        handPressed = false

        //tako
        tako = Tako(screenX, screenY, resources)
        takoyakoState = tako.takoyaki1

        timer = Timer(200)
    }

    companion object {
        var screenRatioX: Float? = null
        var screenRatioY: Float? = null
    }

    override fun run() {
        //only when the player is Playing main thread runs
        startCountDown()
        while(isPlaying) {
            update()            //update the position of animation
            Log.d(TAG, "run: " + pourPressed.toString() + ", " + takoPressed.toString() + ", " + pickPressed.toString()
                    + ", " + saucePressed.toString() + ", " + handPressed.toString())
            drawBackground()    //draw the view every while loop
            sleep()             //delay to make 60 frame for sec
        }
    }

    private fun update() {
        if(human.x > screenX/2)
                human.x -= (30 * screenRatioX!!).toInt()
        if(timer.getCount() <= 200 && timer.getCount() >180)
            humanId = 1
        if (timer.getCount() <= 180 && timer.getCount() >170){
            humanId = 2
            //moving human rapidly
            when(humanCounter) {
                0 -> {
                    humanCounter++
                    human.x = screenX/2-40
                }
                1 -> {
                    humanCounter++
                    human.x = screenX/2+40
                }
                2 -> {
                    humanCounter++
                    human.x = screenX/2+40
                }
                3 -> {
                    humanCounter = 0
                    human.x = screenX/2-40
                }
                else -> {
                    humanCounter = 0
                    human.x = screenX/2
                    Log.d(TAG, "update: error")
                }

            }
            Log.d(TAG, "update: "+humanCounter.toString())
            Log.d(TAG, "update: "+human.x.toString())

        }
        if(timer.getCount() <= 170 && timer.getCount() >0)
            humanId = 3


        //takoyaki state
        if(tako.tako1Active == true) {
            if(tako.tako1Thread.getCount() <30 && tako.tako1Thread.getCount() >25)
                takoyakoState = tako.takoyaki1
            if(tako.tako1Thread.getCount() <25 && tako.tako1Thread.getCount() >20)
                takoyakoState = tako.takoyaki4
            if(tako.tako1Thread.getCount() <20 && tako.tako1Thread.getCount() >15)
                takoyakoState = tako.takoyaki3
            if(tako.tako1Thread.getCount() <15 && tako.tako1Thread.getCount() >10)
                takoyakoState = tako.takoyaki2
            if(tako.tako1Thread.getCount() <10)
                takoyakoState = tako.burnt
        }
    }

    private fun drawBackground() {
        //this is where the image for the screen is made
        if (holder.surface.isValid) {  // this is for ensuring that surfaceView is successfully initialize
            val canvas: Canvas = holder.lockCanvas()    //getting the current screen
            //drawing the canvas to display on a screen using drawBitmap
            //image, x axis, y axis, paint class is needed
            canvas.drawBitmap(background.background, null, Rect(0,0, screenX, screenY), paint)
            //draw human
//            Log.d(TAG, "drawHuman: "+humanId.toString())
            canvas.drawBitmap(human.getHuman(humanId), null,
                Rect(human.x-human.width/2,human.y-human.height/2, human.x+human.width/2, human.y+human.height/2), paint)
            //drawing score and time
            canvas.drawText("TIME", screenX/ 6f, 120f, paintText1)
            canvas.drawText("SCORE", screenX*2/ 3f, 120f, paintText1)
            canvas.drawText(timer.getCount().toString(), screenX/ 6f, 240f, paintText2)
            canvas.drawText(score.toString(), screenX*2/ 3f, 240f, paintText2)

            //draw buttons
            canvas.drawBitmap(button.buttonPour, null, button.getButtonRect(1), paint)
            canvas.drawBitmap(button.buttonTako, null, button.getButtonRect(2), paint)
            canvas.drawBitmap(button.buttonPick, null, button.getButtonRect(3), paint)
            canvas.drawBitmap(button.buttonSauce, null, button.getButtonRect(4), paint)
            canvas.drawBitmap(button.buttonHand, null, button.getButtonRect(5), paint)

            //draw tako
            canvas.drawBitmap(takoyakoState, null, tako.activeTakoRect(1), paint)


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
        mainThread= Thread(this)
        mainThread?.start()
    }

    fun pause() {
        isPlaying = false
        mainThread?.join()

    }

    //this is for moving the flight by touching the screen
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            //the screen is pressed
            MotionEvent.ACTION_DOWN-> {
                Log.d(TAG, "onTouchEvent: " + "(x,y) = (" + event.x + "," + event.y + ")")
                if(event.y > button.layoutMarginTop) {
                    //change icon
                    //tako event listener is placed here
                    val takoId = tako.takoCoorninateCheck(event.x.toInt(), event.y.toInt())
                    if(takoId != null) {
                        tako.takoThreadStart(takoId)
                    }

                } else {
                    if(event.x > button.pourLeft && event.x < button.handRight) {
                        pourPressed = false
                        takoPressed = false
                        pickPressed = false
                        saucePressed = false
                        handPressed = false
                    }
                }

            }
            //the screen press is released
            MotionEvent.ACTION_UP-> {
                if(event.x > button.pourLeft && event.x < button.handRight) {
                    //check button pressed here
                    if (button.buttonCoordinateCheck(1, event.x, event.y))
                        pourPressed = true
                    if (button.buttonCoordinateCheck(2, event.x, event.y))
                        takoPressed = true
                    if (button.buttonCoordinateCheck(3, event.x, event.y))
                        pickPressed = true
                    if (button.buttonCoordinateCheck(4, event.x, event.y))
                        saucePressed = true
                    if (button.buttonCoordinateCheck(5, event.x, event.y))
                        handPressed = true
                }

            }
        }
        return true     //it has to return true to know just after the user touch the screen
    }

    private fun startCountDown() {
        timer.start()

        if(timer.getCount() == 0){
            timer.setShouldExit(true)
            timer.join()
        }
    }

}