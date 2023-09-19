package org.texchtown.fullconcentration3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class TitleDatabaseHelper extends SQLiteOpenHelper {
    //TAG
    private static final String TAG = "DBHelper";

    //database create var
    private static final String DATABASE_NAME = "positive.db";
    private static final int DATABASE_VERSION = 1;

    //table & column name
    private static final String TABLE_NAME = "tb_positive";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SAYING = "saying";

    //query
    private static final String CREATE_QUERY = "create table tb_positive (_id integer primary key autoincrement, saying text);";
    private static final String UPDATE_QUERY = "drop table tb_positive";
    private static final String RAW_READ_QUERY = "select saying from tb_positive";

    public TitleDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME +
//                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_SAYING + " STRING);";
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        DB.execSQL("DROP TABLE " + TABLE_NAME);
        sqLiteDatabase.execSQL(UPDATE_QUERY);
        onCreate(sqLiteDatabase);
    }

    void deleteAndAddData(String str){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, null, null);
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SAYING, str);
        long result = sqLiteDatabase. insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            Log.d(TAG, "addDATA: database insert failed");
        }
    }

    Cursor readAllData() {
//        String RAW_READ_QUERY = "select saying from tb_positive order by _id desc limit 1";
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery(RAW_READ_QUERY, null);

        return cursor;
    }

    String readLastData() {
        Cursor cursor = this.readAllData();
        String output = null;
        while (cursor.moveToNext()){
            output = cursor.getString(0);
        }
        if(output == null) {
            Log.d(TAG, "readLastData: no data inside titledatabase");
        }
        return output;
    }

}
