package org.texchtown.takoyaki

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Background internal constructor(screenX: Int, screenY: Int, res: Resources?) {
    var x = 0
    var y = 0
    var background: Bitmap

    init {
        val image = R.drawable.bg_play
        background = BitmapFactory.decodeResource(res, image)
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false)
    }
}