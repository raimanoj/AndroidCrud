package com.example.myapplication.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.App

object DbHelper : SQLiteOpenHelper(App.getInstance().getContext(), "android_test.db", null, 1) {

    private const val CREATE_TABLE_INFO = "create table table_info (" +
            "id integer primary key autoincrement, " +
            "json_data text" +
            ")"

    private var sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_INFO)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun saveJsonData(jsonData: String) {
        ContentValues().run {
            put("json_data", jsonData)
            sqLiteDatabase.insert("table_info", null, this)
        }
    }

    fun getJsonData(): Cursor {
        return sqLiteDatabase.rawQuery("select * from table_info order by id desc", null)
    }

    fun updateJsonData(id: String, jsonData: String) {
        ContentValues().run {
            put("json_data", jsonData)
            sqLiteDatabase.update("table_info", this, "id = ?", arrayOf(id))
        }
    }

}