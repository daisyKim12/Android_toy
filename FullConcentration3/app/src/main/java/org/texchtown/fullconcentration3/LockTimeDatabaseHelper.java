package org.texchtown.fullconcentration3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class LockTimeDatabaseHelper extends SQLiteOpenHelper {
    //TAG
    private static final String TAG = "LockTimeDatabaseHelper";

    //database create var
    private static final String DATABASE_NAME = "time.db";
    private static final int DATABASE_VERSION = 1;

    //table & column name
    private static final String TABLE_NAME = "tb_lock_time";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_START_LOCK_TIME = "start_time";
    private static final String COLUMN_END_LOCK_TIME = "end_time";

    //query
    private static final String CREATE_QUERY = "create table tb_lock_time " +
            "(_id integer primary key autoincrement, date text, start_time text, end_time text);";
    private static final String UPDATE_QUERY = "drop table tb_lock_time";
    private static final String DATE_READ_QUERY = "select start_time, end_time from tb_lock_time";

    public LockTimeDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(UPDATE_QUERY);
        onCreate(sqLiteDatabase);
    }

    void deleteAndAddTime(String date, String start_time, String end_time) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null, null);
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_START_LOCK_TIME, start_time);
        contentValues.put(COLUMN_END_LOCK_TIME, end_time);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            Log.d(TAG, "deleteAndAddTime: database insert failed");
        }
    }

    Calendar readStartTimeFromDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select start_time from tb_lock_time", null);
        String output = null;
        while (cursor.moveToNext()) {
            output = cursor.getString(0);
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(output.substring(0,2)));
        c.set(Calendar.MINUTE, Integer.valueOf(output.substring(3)));
        Log.d(TAG, "readStartTimeFromDatabase:" + output.substring(0,2) +", "
        + output.substring(3));
        return c;
    }

    Calendar readEndTimeFromDatabase() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select end_time from tb_lock_time", null);
        String output = null;
        while (cursor.moveToNext()) {
            output = cursor.getString(0);
        }
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(output.substring(0,2)));
        c.set(Calendar.MINUTE, Integer.valueOf(output.substring(3)));
        Log.d(TAG, "readEndTimeFromDatabase:" + output.substring(0,2) +", "
                + output.substring(3));
        return c;
    }

}
