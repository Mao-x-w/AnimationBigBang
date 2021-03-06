package com.lib_greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.lib_greendao.model.DBTemperature;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<DBTemperature, Long> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity DBTemperature.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Time = new Property(1, Long.class, "time", false, "TIME");
        public final static Property Temperature = new Property(2, String.class, "temperature", false, "TEMPERATURE");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TIME\" INTEGER," + // 1: time
                "\"TEMPERATURE\" TEXT);"); // 2: temperature
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DBTemperature entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(3, temperature);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DBTemperature entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(2, time);
        }
 
        String temperature = entity.getTemperature();
        if (temperature != null) {
            stmt.bindString(3, temperature);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DBTemperature readEntity(Cursor cursor, int offset) {
        DBTemperature entity = new DBTemperature( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // time
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // temperature
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DBTemperature entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTime(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setTemperature(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DBTemperature entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DBTemperature entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DBTemperature entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
