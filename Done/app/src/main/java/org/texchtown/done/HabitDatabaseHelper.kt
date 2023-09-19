package org.texchtown.done

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class HabitDatabaseHelper  //query
//    private static final String CREATE_HABIT_QUERY = "create table tb_habit (_id integer primary key autoincrement, name text, build_or_quit boolean" +
//            ", goal integer, mon boolean, tue boolean, wed boolean, thu boolean, fri boolean, sat boolean, sun boolean, h_group text" +
//            ", color integer, notification boolean, count integer, create_date text, strike integer);";
//    private static final String CREATE_DATE_QUERY = "create table tb_date (clicked_date text, finished_habit_name text, finished_habit_status boolean);";
//    private static final String UPDATE_HABIT_QUERY = "drop table tb_habit";
//    private static final String UPDATE_DATE_QUERY = "drop table tb_date";
//    private static final String READ_TODAY_QUERY = "select name, build_or_quit, goal, h_group, color, count from tb_habit";
//    private static final String CHECK_HABIT_STATUS = "select finished_habit_name from tb_date where finished_habit_status=true";
internal constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
            "create table tb_habit (_id integer primary key autoincrement, name text, build_or_quit boolean" +
                    ", goal integer, mon boolean, tue boolean, wed boolean, thu boolean, fri boolean, sat boolean, sun boolean, h_group text" +
                    ", color integer, notification boolean, count integer, create_date text, strike integer);"
        )
        sqLiteDatabase.execSQL("create table tb_date (clicked_date text, finished_habit_name text, finished_habit_status boolean);")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("drop table tb_habit")
        sqLiteDatabase.execSQL("drop table tb_date")
        onCreate(sqLiteDatabase)
    }

    fun addHabit(
        name: String?,
        status: Boolean?,
        goal: Int?,
        mon: Boolean?,
        tue: Boolean?,
        wed: Boolean?,
        thu: Boolean?,
        fri: Boolean?,
        sat: Boolean?,
        sun: Boolean?,
        group: String?,
        color: Int,
        notification: Boolean?
    ) {
        val sqLiteDatabase = this.writableDatabase

        //use content value objet to pass data groups that we want to save
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, name)
        contentValues.put(COLUMN_STATUS, status)
        contentValues.put(COLUMN_GOAL, goal)
        contentValues.put(COLUMN_MON, mon)
        contentValues.put(COLUMN_TUE, tue)
        contentValues.put(COLUMN_WED, wed)
        contentValues.put(COLUMN_THU, thu)
        contentValues.put(COLUMN_FRI, fri)
        contentValues.put(COLUMN_SAT, sat)
        contentValues.put(COLUMN_SUN, sun)
        contentValues.put(COLUMN_GROUP, group)
        contentValues.put(COLUMN_COLOR, color)
        contentValues.put(COLUMN_NOTIFY, notification)
        contentValues.put(COLUMN_COUNT, 0)
        contentValues.put(COLUMN_CREATE_DATE, todayDate())
        contentValues.put(COLUMN_STRIKE, 0)

        //pass content value to sql database object
        val result = sqLiteDatabase.insert(TABLE_HABIT, null, contentValues)
        if (result == -1L) {
            Log.d(TAG, "addHabit: save habit data into db failed")
        }
    }

    fun addTodayHabit(name: String?) {
        val sqLiteDatabase = this.writableDatabase
        //use content value objet to pass data groups that we want to save
        val contentValues = ContentValues()
        contentValues.put(COLUMN_CLICKED_DATE, todayDate())
        contentValues.put(COLUMN_FINISHED_NAME, name)
        contentValues.put(COLUMN_FINISHED_STATUS, false)
        //pass content value to sql database object
        val result = sqLiteDatabase.insert(TABLE_DATE, null, contentValues)
        if (result == -1L) {
            Log.d(TAG, "updateDate: save date data into db failed")
        }
    }

    fun checkTodayHabit(): ArrayList<String> {
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery(
            "select finished_habit_name from tb_date where finished_habit_status=true" + " and clicked_date=" + todayDate(),
            null
        )
        val checkedHabit = ArrayList<String>()
        if (cursor.count == 0) {
            Log.d(TAG, "checkTodayHabit: cursor with no data")
        } else {
            while (cursor.moveToNext()) {
                checkedHabit.add(cursor.getString(0))
            }
        }
        return checkedHabit
    }

    fun updateTodayHabit(name: String, clickStatus: Boolean?) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_FINISHED_STATUS, clickStatus)
        val result = sqLiteDatabase.update(
            TABLE_DATE,
            contentValues,
            "clicked_date=? and finished_habit_name=?",
            arrayOf(todayDate(), name)
        ).toLong()
        if (result == -1L) {
            Log.d(TAG, "addHabit: update data failed")
        }
    }

    fun readTodayData(): Cursor? {
        val sqLiteDatabase = this.readableDatabase
        var cursor: Cursor? = null
        var todayQuery = ""
        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]
        when (day) {
            Calendar.MONDAY -> todayQuery = " where mon=true;"
            Calendar.TUESDAY -> todayQuery = " where tue=true;"
            Calendar.WEDNESDAY -> todayQuery = " where wed=true;"
            Calendar.THURSDAY -> todayQuery = " where thu=true;"
            Calendar.FRIDAY -> todayQuery = " where fri=true;"
            Calendar.SATURDAY -> todayQuery = " where sat=true;"
            Calendar.SUNDAY -> todayQuery = " where sun=true;"
            else -> Log.d(TAG, "readTodayData: error at reading day of week")
        }
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(
                ("select name, build_or_quit, goal, h_group, color" +
                        ", count from tb_habit" + todayQuery), null
            )
        }
        return cursor
    }

    fun updateCount(name: String, count: Int) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_COUNT, count)
        val result =
            sqLiteDatabase.update(TABLE_HABIT, contentValues, "name=?", arrayOf(name)).toLong()
        if (result == -1L) {
            Log.d(TAG, "addHabit: update data failed")
        }
    }

    fun todayDate(): String {
        val today = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val currentDate = dateFormat.format(today)
        Log.d(
            TAG,
            "todayDate(current date): $currentDate"
        )
        return currentDate
    }

    fun readGroupDetailData(group: String): Cursor {
        val sqLiteDatabase = this.readableDatabase
        return sqLiteDatabase.rawQuery(
            "select name, count, goal, color from tb_habit where h_group=?", arrayOf(group)
        )
    }

    fun readAllGroup(): ArrayList<String> {
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("select h_group from tb_habit", null)
        val group_name = ArrayList<String>()
        if (cursor.count == 0) {
            Log.d(TAG, "readAllGroup: cursor with no data")
        } else {
            while (cursor.moveToNext()) {
                if (!group_name.contains(cursor.getString(0))) {
                    group_name.add(cursor.getString(0))
                }
            }
        }
        return group_name
    }

    fun readHabitDetailByName(name: String): Cursor {
        val sqLiteDatabase = this.readableDatabase
        return sqLiteDatabase.rawQuery(
            "select goal, count, strike, color from tb_habit where name=?", arrayOf(name)
        )
    }

    fun readDateByName(name: String): Cursor {
        val sqLiteDatabase = this.readableDatabase
        return sqLiteDatabase.rawQuery(
            "select clicked_date from tb_date " +
                    "where finished_habit_name=? and finished_habit_status=1", arrayOf(name)
        )
    }

    companion object {
        //TAG
        private val TAG = "HabitDatabaseHelper"

        //database create var
        private val DATABASE_NAME = "habit.db"
        private val DATABASE_VERSION = 1

        //new habit table & column name
        private val TABLE_HABIT = "tb_habit"
        private val COLUMN_NAME = "name"
        private val COLUMN_STATUS = "build_or_quit"
        private val COLUMN_GOAL = "goal"
        private val COLUMN_MON = "mon"
        private val COLUMN_TUE = "tue"
        private val COLUMN_WED = "wed"
        private val COLUMN_THU = "thu"
        private val COLUMN_FRI = "fri"
        private val COLUMN_SAT = "sat"
        private val COLUMN_SUN = "sun"
        private val COLUMN_GROUP = "h_group"
        private val COLUMN_COLOR = "color"
        private val COLUMN_NOTIFY = "notification"
        private val COLUMN_COUNT = "count"
        private val COLUMN_CREATE_DATE = "create_date"
        private val COLUMN_STRIKE = "strike"

        //keep track of habit table & column name
        private val TABLE_DATE = "tb_date"
        private val COLUMN_CLICKED_DATE = "clicked_date"
        private val COLUMN_FINISHED_NAME = "finished_habit_name"
        private val COLUMN_FINISHED_STATUS = "finished_habit_status"
    }
}