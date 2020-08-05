package com.example.lib_server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_server.db.DBOpenHelper;

/**
 * User: laomao
 * Date: 2020/4/7
 * Time: 3:07 PM
 */
public class CustomContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.lib_server.CustomContentProvider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        mUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {
        initProviderData();

        return true;
    }

    private void initProviderData() {
        mDb = new DBOpenHelper(getContext()).getWritableDatabase();
        mDb.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'android ');");
        mDb.execSQL("insert into book values(4,'ios ');");
        mDb.execSQL("insert into book values(5,'Html5 ');");
        mDb.execSQL("insert into user values(1,'jake ',1);");
        mDb.execSQL("insert into user values(2,'jasmine ',0);");
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        mDb.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        int delete = mDb.delete(tableName, selection, selectionArgs);

        if (delete > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);

        int update = mDb.update(tableName, values, selection, selectionArgs);
        
        if (update > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return update;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
