package org.texchtown.takoyaki

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.util.Log
import org.texchtown.takoyaki.GameView.Companion.screenRatioX
import org.texchtown.takoyaki.GameView.Companion.screenRatioY

class Button internal constructor(screenX: Int, screenY: Int, res: Resources?) {

    private val TAG = "ButtonClass"
    private val screenX: Int
    private val screenY: Int
    var buttonPour: Bitmap
    var pourLeft: Int = 0
    var pourRight: Int = 0
    var pourTop: Int = 0
    var pourBottom: Int = 0
    var buttonTako: Bitmap
    var takoLeft: Int = 0
    var takoRight: Int = 0
    var takoTop: Int = 0
    var takoBottom: Int = 0
    var buttonPick: Bitmap
    var pickLeft: Int = 0
    var pickRight: Int = 0
    var pickTop: Int = 0
    var pickBottom: Int = 0
    var buttonSauce: Bitmap
    var sauceLeft: Int = 0
    var sauceRight: Int = 0
    var sauceTop: Int = 0
    var sauceBottom: Int = 0
    var buttonHand: Bitmap
    var handLeft: Int = 0
    var handRight: Int = 0
    var handTop: Int = 0
    var handBottom: Int = 0

    var width: Int
    var height: Int

    val layoutMarginTop: Int = screenY - 10
    val buttonMargin: Int = 12
    val layoutMarginHorizontal: Int

    init {
        this.screenX = screenX
        this.screenY = screenY

        //get button bitmap
        buttonPour = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.btn_pour)
        buttonTako = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.btn_tako)
        buttonPick = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.btn_pick)
        buttonSauce = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.btn_sauce)
        buttonHand = BitmapFactory.decodeResource(res, org.texchtown.takoyaki.R.drawable.btn_hand)

        width = buttonPour.width
        height = buttonPour.height
        width = width-60
        height = height-60
        width = (width * screenRatioX!!).toInt()
        height = (height * screenRatioY!!).toInt()

        layoutMarginHorizontal = (screenX - buttonMargin*4-width*5)/2

        //resize bitmap
        buttonPour = Bitmap.createScaledBitmap(buttonPour, screenX, screenY, false)
        buttonTako = Bitmap.createScaledBitmap(buttonTako, screenX, screenY, false)
        buttonPick = Bitmap.createScaledBitmap(buttonPick, screenX, screenY, false)
        buttonSauce = Bitmap.createScaledBitmap(buttonSauce, screenX, screenY, false)
        buttonHand = Bitmap.createScaledBitmap(buttonHand, screenX, screenY, false)

        buttonPosition()
    }

    private fun buttonPosition() {

        pourLeft = layoutMarginHorizontal
        pourTop = layoutMarginTop - height
        pourRight = pourLeft + width
        pourBottom = pourTop + height

        takoLeft = pourLeft + width + buttonMargin
        takoTop = layoutMarginTop - height
        takoRight = takoLeft + width
        takoBottom = takoTop + height

        pickLeft = takoLeft + width + buttonMargin
        pickTop = layoutMarginTop - height
        pickRight = pickLeft + width
        pickBottom = pickTop + height

        sauceLeft = pickLeft + width + buttonMargin
        sauceTop = layoutMarginTop - height
        sauceRight = sauceLeft + width
        sauceBottom = sauceTop + height

        handLeft = sauceLeft + width + buttonMargin
        handTop = layoutMarginTop - height
        handRight = handLeft + width
        handBottom = handTop + height
    }

    fun getButtonRect(buttonId: Int): Rect {
        when(buttonId) {
            1 -> return Rect(pourLeft, pourTop, pourRight, pourBottom)
            2 -> return Rect(takoLeft, takoTop, takoRight, takoBottom)
            3 -> return Rect(pickLeft, pickTop, pickRight, pickBottom)
            4 -> return Rect(sauceLeft, sauceTop, sauceRight, sauceBottom)
            5 -> return Rect(handLeft, handTop, handRight, handBottom)
            else -> {
                Log.d(TAG, "getButtonRect: error")
                return Rect(0, 0, screenX, screenY)
            }
        }
    }

    fun buttonCoordinateCheck(buttonId: Int, x: Float, y: Float): Boolean{
        when(buttonId) {
            1 -> {
                if(x > pourLeft && x < (pourRight+buttonMargin/2) && y > pourTop && y < pourBottom)
                    return true
            }
            2 -> {
                if(x > (takoLeft-buttonMargin/2) && x < (takoRight+buttonMargin/2) && y > takoTop && y < takoBottom)
                    return true
            }
            3 -> {
                if(x > (pickLeft-buttonMargin/2) && x < (pickRight+buttonMargin/2) && y > pickTop && y < pickBottom)
                    return true
            }
            4 -> {
                if(x > (sauceLeft-buttonMargin/2) && x < (sauceRight+buttonMargin/2) && y > sauceTop && y < sauceBottom)
                    return true
            }
            5 -> {
                if(x > (handLeft-buttonMargin/2) && x < handRight && y > handTop && y < handBottom)
                    return true
            }
            else -> {
                return false
            }
        }
        return false
    }

}