package org.texchtown.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "positive.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tb_positive";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SAYING = "positive_saying";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SAYING + " STRING);";
        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE " + TABLE_NAME);
        onCreate(DB);
    }

    void addData(String str){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SAYING, str);
        long result = DB. insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Log.d(TAG, "addBook: db insert failed");
        }else {
            Log.d(TAG, "addBook: db insert success");
        }
    }

    Cursor readAllData() {
        String query = "select positive_saying from tb_positive order by _id desc limit 1";
        SQLiteDatabase DB = this.getReadableDatabase();

        Cursor cursor = DB.rawQuery(query, null);

        return cursor;
    }

    void deleteNeedlessData(String lastData) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete(TABLE_NAME, null, null);
        this.addData(lastData);

    }
}
