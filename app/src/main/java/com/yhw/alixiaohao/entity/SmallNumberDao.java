package com.yhw.alixiaohao.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SMALL_NUMBER".
*/
public class SmallNumberDao extends AbstractDao<SmallNumber, Long> {

    public static final String TABLENAME = "SMALL_NUMBER";

    /**
     * Properties of entity SmallNumber.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Number = new Property(1, String.class, "number", false, "NUMBER");
        public final static Property Area = new Property(2, String.class, "area", false, "AREA");
    }


    public SmallNumberDao(DaoConfig config) {
        super(config);
    }
    
    public SmallNumberDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SMALL_NUMBER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NUMBER\" TEXT," + // 1: number
                "\"AREA\" TEXT);"); // 2: area
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SMALL_NUMBER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SmallNumber entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(2, number);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SmallNumber entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String number = entity.getNumber();
        if (number != null) {
            stmt.bindString(2, number);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SmallNumber readEntity(Cursor cursor, int offset) {
        SmallNumber entity = new SmallNumber( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // number
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // area
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SmallNumber entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setArea(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SmallNumber entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SmallNumber entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SmallNumber entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
