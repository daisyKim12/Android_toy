package org.texchtown.flightgame

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.security.AccessController.getContext


class Background internal constructor(screenX: Int, screenY: Int, res: Resources?) {
    var x = 0
    var y = 0
    var background: Bitmap

    init {
        val image = org.texchtown.flightgame.R.drawable.background
        background = BitmapFactory.decodeResource(res, image)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}
