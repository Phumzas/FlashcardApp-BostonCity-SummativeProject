package com.example.androidsummative1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(title TEXT primary key, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertuserdata(String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("description", description);

        long result = DB.insert("Userdetails", null, contentValues);
        return result != -1; // Correct insertion check
    }

    public Boolean updateuserdata(String title, String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description);

        Cursor cursor = DB.rawQuery("Select * from Userdetails where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "title=?", new String[]{title});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }
    }

    public Boolean deletedata(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where title=?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "title=?", new String[]{title});
            cursor.close();
            return result != -1;
        } else {
            cursor.close();
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Userdetails", null);
    }
}
