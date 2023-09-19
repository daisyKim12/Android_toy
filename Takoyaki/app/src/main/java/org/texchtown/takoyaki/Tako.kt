package org.texchtown.takoyaki

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log

class Tako internal constructor(screenX: Int, screenY: Int, res: Resources?) {

    private val TAG = "Tako"

    private val tako1: Rect = Rect(581, 393, 684, 466)
    private val tako2: Rect = Rect(743, 393, 857, 466)
    private val tako3: Rect = Rect(913, 393, 1025, 466)
    private val tako4: Rect = Rect(1075, 393, 1190, 466)

    var takoyaki1: Bitmap
    var takoyaki2: Bitmap
    var takoyaki3: Bitmap
    var takoyaki4: Bitmap
    var burnt: Bitmap

    private val screenX: Int
    private val screenY: Int

    var width: Int
    var height: Int

    //tako start flag
    var tako1Active : Boolean
    var tako2Active : Boolean
    var tako3Active : Boolean
    var tako4Active : Boolean

    //tako thread
    var tako1Thread: Timer
    var tako2Thread: Timer
    var tako3Thread: Timer
    var tako4Thread: Timer

    init {
        this.screenX = screenX
        this.screenY = screenY

        tako1Active = false
        tako2Active = false
        tako3Active = false
        tako4Active = false

        tako1Thread = Timer(30)
        tako2Thread = Timer(30)
        tako3Thread = Timer(30)
        tako4Thread = Timer(30)

        takoyaki1 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.takoyaki1)
        takoyaki2 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.takoyaki2)
        takoyaki3 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.takoyaki3)
        takoyaki4 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.takoyaki4)
        burnt = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.burnt)

        width = takoyaki1.width
        height = takoyaki1.height
//        width = width-60
//        height = height-60
        width = (width * GameView.screenRatioX!!).toInt()
        height = (height * GameView.screenRatioY!!).toInt()

        takoyaki1 = Bitmap.createScaledBitmap(takoyaki1, screenX, screenY, false)
        takoyaki2 = Bitmap.createScaledBitmap(takoyaki2, screenX, screenY, false)
        takoyaki3 = Bitmap.createScaledBitmap(takoyaki3, screenX, screenY, false)
        takoyaki4 = Bitmap.createScaledBitmap(takoyaki4, screenX, screenY, false)
        burnt = Bitmap.createScaledBitmap(burnt, screenX, screenY, false)

    }

    fun takoCoorninateCheck(x: Int, y: Int): Int? {
        if(tako1.contains(x,y)){
            return 1
        } else if (tako2.contains(x,y)){
            return 2
        } else if (tako3.contains(x,y)){
            return 3
        } else if (tako4.contains(x,y)){
            return 4
        } else {
            return null
        }
    }

    fun takoThreadStart(takoId: Int) {
        when(takoId){
            1-> {
                tako1Active = true
                tako1Thread.start()
                if(tako1Thread.getCount() == 0){
                    tako1Thread.setShouldExit(true)
                    tako1Thread.join()
                }
            }
            2-> {
                tako2Active = true
                tako2Thread.start()
                if(tako2Thread.getCount() == 0){
                    tako2Thread.setShouldExit(true)
                    tako2Thread.join()
                }
            }
            3-> {
                tako3Active = true
                tako3Thread.start()
                if(tako3Thread.getCount() == 0){
                    tako3Thread.setShouldExit(true)
                    tako3Thread.join()
                }
            }
            4-> {
                tako4Active = true
                tako4Thread.start()
                if(tako4Thread.getCount() == 0){
                    tako4Thread.setShouldExit(true)
                    tako4Thread.join()
                }
            }
            else-> {
                Log.d(TAG, "takoThreadStart: error")
            }
        }
    }

    fun activeTakoRect(takoId: Int): Rect {
        var activeTako: Rect = Rect(0,0,0,0)
        when (takoId) {
            1-> if(tako1Active == true) {
                activeTako = tako1
                Log.d(TAG, "activeTakoRect: " +activeTako)
            }
            2-> if(tako2Active == true) {
                activeTako = tako2
            }
            3-> if(tako3Active == true) {
                activeTako = tako3
            }
            4-> if(tako4Active == true) {
                activeTako = tako4
            }
            else -> {
                Log.d(TAG, "activeTakoRect: error")
                activeTako=Rect(0,0,screenX, screenY)
            }
        }
        return activeTako
    }

//    fun takoyakiState(takoId: Int): Bitmap{
//        var activeTako: Rect = Rect(0,0,0,0)
//        when (takoId) {
//            1-> if(tako1Active == true) {
//                activeTako = tako1
//            }
//            2-> if(tako2Active == true) {
//                activeTako = tako2
//            }
//            3-> if(tako3Active == true) {
//                activeTako = tako3
//            }
//            4-> if(tako4Active == true) {
//                activeTako = tako4
//            }
//            else -> {
//                Log.d(TAG, "activeTakoRect: error")
//                activeTako=Rect(0,0,screenX, screenY)
//            }
//        }
//        return activeTako
//        tako1Thread.getCountNotic(20)
//    }
}
