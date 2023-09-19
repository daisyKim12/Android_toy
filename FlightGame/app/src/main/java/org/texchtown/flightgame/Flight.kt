package org.texchtown.flightgame

import android.R
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import org.texchtown.flightgame.GameView.Companion.screenRatioX
import org.texchtown.flightgame.GameView.Companion.screenRatioY

class Flight internal constructor(private val gameView: GameView, screenY: Int, res: Resources?){

    var toShoot = 0
    var isGoingUp = false
    var x: Int
    var y: Int
    var width: Int
    var height: Int
    var flight1: Bitmap
    var flight2: Bitmap
    //counter for animation
    var wingCounter = 0
    var shootCounter = 1

    //shoot
    var shoot1: Bitmap
    var shoot2: Bitmap
    var shoot3: Bitmap
    var shoot4: Bitmap
    var shoot5: Bitmap

    //dead bitmap
    private var dead: Bitmap

    init {

        //get flight1 flight2 bitmap
        flight1 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.fly1)
        flight2 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.fly2)

        width = flight1.width
        height = flight1.height
        width /= 4
        height /= 4
        width = (width * screenRatioX!!).toInt()
        height = (height * screenRatioY!!).toInt()

        //resize flight1 flight2 bitmap
        flight1 = Bitmap.createScaledBitmap(flight1, width, height, false)
        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false)

        //dead bitmap
        dead = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.dead)
        dead = Bitmap.createScaledBitmap(dead, width, height, false)


        //y for center vertical, x for 64dp margin
        y = screenY / 2
        x = (64 * screenRatioX!!).toInt()

        //shoot
        shoot1 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.shoot1)
        shoot2 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.shoot2)
        shoot3 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.shoot3)
        shoot4 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.shoot4)
        shoot5 = BitmapFactory.decodeResource(res, org.texchtown.flightgame.R.drawable.shoot5)
        //shoot resize
        shoot1 = Bitmap.createScaledBitmap(shoot1, width, height, false)
        shoot2 = Bitmap.createScaledBitmap(shoot2, width, height, false)
        shoot3 = Bitmap.createScaledBitmap(shoot3, width, height, false)
        shoot4 = Bitmap.createScaledBitmap(shoot4, width, height, false)
        shoot5 = Bitmap.createScaledBitmap(shoot5, width, height, false)

    }

    fun getFlight():Bitmap {
        //shooting animation
        if (toShoot != 0) {
            if (shootCounter == 1) {
                shootCounter++
                return shoot1
            }
            if (shootCounter == 2) {
                shootCounter++
                return shoot2
            }
            if (shootCounter == 3) {
                shootCounter++
                return shoot3
            }
            if (shootCounter == 4) {
                shootCounter++
                return shoot4
            }
            shootCounter = 1
            toShoot--
            gameView.newBullet()
            return shoot5
        }

        //wing animation
        if(wingCounter == 0) {
            wingCounter++
            return flight1
        }

        wingCounter--
        return flight2
    }

    fun getCollisionShape():Rect {
        return Rect(x, y, x + width, y + height)
    }

    fun getDead(): Bitmap {
        return dead
    }
}
