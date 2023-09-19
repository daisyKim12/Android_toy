package org.texchtown.takoyaki

import android.util.Log

class Timer internal constructor(count: Int) : Thread() {
    private val TAG = "Timer"
    private var count: Int
    private var shouldExit = false

    init {
        this.count = count
    }

    fun setShouldExit(shouldExit: Boolean){
        this.shouldExit = shouldExit
    }

    fun getCount(): Int {
        return count
    }

    override fun run() {

        try {
            while(count > 0){
                count --
                if (shouldExit) {
                    break
                }
                Log.d(TAG, "run: " + "thread is running")
                Thread.sleep(1000)
            }
            Log.d(TAG, "run: " + "thread is completed")
        }catch (e:InterruptedException) {
            e.printStackTrace()
        }

    }

    fun getCountNotic(time: Int): Boolean {
        if(count<time){
            return true
        }
        else {
            return false
        }
    }
}