package com.example.projecthairgate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "Oliver";
    private static final String DATABASE_NAME = "userface.db";
    private static final String TABLE_NAME = "saved_face";
    private static final String COL2 = "FACE_IMAGE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
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

        int resultUpdate;
        long resultInsert;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("SELECT FACE_IMAGE FROM " + TABLE_NAME,null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        if(data.moveToNext()) {

            resultUpdate = db.update(TABLE_NAME, contentValues, null, null);

            data.close();

            if (resultUpdate == -1) {
                Log.d(TAG, "addData: failed");
                return false;

            } else {
                Log.d(TAG, "addData: successfully added data");
                return true;
            }

        } else {

            resultInsert = db.insert(TABLE_NAME, null, contentValues);

            data.close();

            if (resultInsert == -1) {
                Log.d(TAG, "addData: failed");
                return false;

            } else {
                Log.d(TAG, "addData: successfully added data");
                return true;
            }
        }
    }

    public byte[] getContent() {

        byte[] imageBytes;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT FACE_IMAGE FROM " + TABLE_NAME,null);

        if (!data.moveToNext()) {
            return null;
        }
        imageBytes = data.getBlob(0);

        data.close();

        return imageBytes;
    }
}
