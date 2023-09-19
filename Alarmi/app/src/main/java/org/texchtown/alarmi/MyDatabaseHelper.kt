package org.texchtown.alarmi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "alarm.db"
        private const val DATABASE_VERSION = 1
        private val TBL_ALARM = "tb1_alarm"
        private const val ID = "id"
        private const val HOUR = "hour"
        private const val MINUTE = "minute"
        private const val ENABLE = "enable"
        private const val REPEAT = "repeat"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAlarm = ("CREATE TABLE " + TBL_ALARM + "(" + ID + "INTEGER PRIMARY KEY," + HOUR + "INTEGER," + MINUTE + "INTEGER," + ENABLE + "BOOLEAN," + REPEAT + "TEXT" + ")")
        db?.execSQL(createTblAlarm)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ALARM")
        onCreate(db)
    }

    fun insertAlarm(data: AlarmTime): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, data.id)
        contentValues.put(HOUR, data.hour)
        contentValues.put(MINUTE, data.minute)
        contentValues.put(ENABLE, data.enable)
        contentValues.put(REPEAT, data.repeat)

        val success = db.insert(TBL_ALARM, null, contentValues)
        db.close()
        return success
    }
}