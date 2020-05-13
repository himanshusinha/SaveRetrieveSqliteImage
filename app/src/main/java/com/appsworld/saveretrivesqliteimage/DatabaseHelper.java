package com.appsworld.saveretrivesqliteimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String Database_Name = "names.db";
    public static final String Table_Name = "mytable";
    public DatabaseHelper(Context context)
    {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE "+ Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,newImage blob )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " +Table_Name);
        onCreate(db);

    }
    public boolean addData(String name, byte[] newEntryTag)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("newImage",newEntryTag);
        long result = db.insert(Table_Name,null,contentValues);
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public Cursor getdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + Table_Name;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}