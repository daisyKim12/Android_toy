package org.texchtown.flightgame

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import org.texchtown.flightgame.GameView.Companion.screenRatioX
import org.texchtown.flightgame.GameView.Companion.screenRatioY


class Bird internal constructor(res: Resources?) {
    var speed = 20
    var wasShot = true
    var x = 0
    var y: Int
    var width: Int
    var height: Int
    var birdCounter = 1
    var bird1: Bitmap
    var bird2: Bitmap
    var bird3: Bitmap
    var bird4: Bitmap
    private var bird: Bitmap

    init {
        bird1 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.bird1)
        bird2 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.bird2)
        bird3 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.bird3)
        bird4 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.bird4)
        width = bird1.width
        height = bird1.height
        width /= 6
        height /= 6
        width = (width * screenRatioX!!).toInt()
        height = (height * screenRatioY!!).toInt()
        bird1 = Bitmap.createScaledBitmap(bird1, width, height, false)
        bird2 = Bitmap.createScaledBitmap(bird2, width, height, false)
        bird3 = Bitmap.createScaledBitmap(bird3, width, height, false)
        bird4 = Bitmap.createScaledBitmap(bird4, width, height, false)
        bird = getBird()
        y = -height
    }
    fun getBird(): Bitmap {
            if (birdCounter == 1) {
                birdCounter++
                return bird1
            }
            if (birdCounter == 2) {
                birdCounter++
                return bird2
            }
            if (birdCounter == 3) {
                birdCounter++
                return bird3
            }
            birdCounter = 1
            return bird4
    }

    fun getCollisionShape():Rect {
        return Rect(x, y, x + width, y + height)
    }



}