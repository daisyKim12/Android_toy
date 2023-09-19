package org.texchtown.takoyaki

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import org.texchtown.takoyaki.GameView.Companion.screenRatioX
import org.texchtown.takoyaki.GameView.Companion.screenRatioY

class Human internal constructor(screenX: Int, res: Resources?){

    var x: Int
    var y: Int
    var width: Int
    var height: Int
    var human1: Bitmap
    var human2: Bitmap
    var human31: Bitmap
    var human32: Bitmap
    //counter for animation
    var human3Counter = 0

    init {
        //get flight1 flight2 bitmap
        human1 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.human1)
        human2 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.human2)
        human31 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.human3_1)
        human32 = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.human3_2)


        width = human1.width-180
        height = human2.height-180
        width = (width * screenRatioX!!).toInt()
        height = (height * screenRatioY!!).toInt()

        //resize flight1 flight2 bitmap
        human1 = Bitmap.createScaledBitmap(human1, width, height, false)
        human2 = Bitmap.createScaledBitmap(human2, width, height, false)
        human31 = Bitmap.createScaledBitmap(human31, width, height, false)
        human32 = Bitmap.createScaledBitmap(human32, width, height, false)

        //y for center vertical, x for 64dp margin
        //x = screenX /2
        x = screenX
        y = (150 * screenRatioY!!).toInt()


    }

    fun getHuman(humanId: Int): Bitmap {
        when(humanId) {
            1 -> return human1
            2 -> return human2
            3 -> {
                if (human3Counter == 0){
                    human3Counter++
                    return human31
                } else {
                    human3Counter--
                    return human32
                }
            }
            else -> {
                return human1
            }
        }
    }

}