package com.example.projecthairgate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "Oliver";
    public static final String DATABASE_NAME = "userface.db";
    public static final String TABLE_NAME = "saved_face";
    public static final String COL1 = "ID";
    public static final String COL2 = "FACE_IMAGE";


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, " +
                "FACE_IMAGE BLOB) ";
        db.execSQL(createTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public boolean addData(byte[] item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        db.replace(TABLE_NAME,null,contentValues);


        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            Log.d(TAG, "addData: failed");
            return false;
            
        }
        else{
            Log.d(TAG, "addData: successfully added data");
            return true;
        }
    }
    public Cursor getContent() {
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor data = db.rawQuery("SELECT FACE_IMAGE FROM " + TABLE_NAME,null);
        return data;
    }
}
