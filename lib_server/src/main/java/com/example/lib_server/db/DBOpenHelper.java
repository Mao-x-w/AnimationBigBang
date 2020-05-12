package com.example.lib_server.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * User: laomao
 * Date: 2020/4/7
 * Time: 7:30 PM
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VWESION = 1;
    private String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "+ BOOK_TABLE_NAME + "(id integer primary key autoincrement,name varchar(30))";
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "+USER_TABLE_NAME+"(id integer  primary key autoincrement,name varchar(30),sex integer)";

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VWESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
