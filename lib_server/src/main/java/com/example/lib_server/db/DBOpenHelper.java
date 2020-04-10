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

    private final static String TAG = "DBOpenHelper";

    private final static String DATABASE_NAME = "com_android_peter_provider.db";
    public final static String DATABASE_STUDENT_TABLE_NAME = "student";
    private final static int DATABASE_VERSION = 1;

    private Context mContext;

    /**
     * student table
     * @id primary key
     * @name student's name. e.g:peter.
     * @gender student's gender. e.g: 0 male; 1 female.
     * @number student's number. e.g: 201804081702.
     * @score student's score. more than 0 and less than 100. e.g:90.
     * */
    private final static String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + DATABASE_STUDENT_TABLE_NAME
            + "(id INTEGER PRIMARY KEY,"
            + "name TEXT VARCHAR(20) NOT NULL,"
            + "gender BIT DEFAULT(1),"
            + "number TEXT VARCHAR(12) NOT NULL,"
            + "score INTEGER CHECK(score >= 0 and score <= 100))";

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
