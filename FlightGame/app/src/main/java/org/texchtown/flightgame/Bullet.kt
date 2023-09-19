package org.texchtown.flightgame

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import org.texchtown.flightgame.GameView.Companion.screenRatioX
import org.texchtown.flightgame.GameView.Companion.screenRatioY


class Bullet internal constructor(res: Resources?) {
    var x = 0
    var y = 0
    var width: Int
    var height: Int
    var bullet: Bitmap

    init {
        bullet = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.bullet)
        width = bullet.width
        height = bullet.height
        width /= 4
        height /= 4
        width = (width * screenRatioX!!).toInt()
        height = (height * screenRatioY!!).toInt()
        bullet = Bitmap.createScaledBitmap(bullet, width, height, false)
    }

    fun getCollisionShape():Rect {
        return Rect(x, y, x + width, y + height)
    }
}