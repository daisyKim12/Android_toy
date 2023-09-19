package org.texchtown.alarmi

import kotlin.random.Random

data class AlarmTime(
    var id: Int = getAutoId(),
    var hour: Int = 99,
    var minute: Int = 99,
    var enable: Boolean = true,
    var repeat: String = ""
){
    companion object {
        fun getAutoId(): Int {
            val random = Random
            return random.nextInt(100)
        }
    }
}
