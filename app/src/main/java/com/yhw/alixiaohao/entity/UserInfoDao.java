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
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Long> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CheckCellnumber = new Property(1, String.class, "checkCellnumber", false, "CHECK_CELLNUMBER");
        public final static Property BindStatus = new Property(2, String.class, "bindStatus", false, "BIND_STATUS");
        public final static Property Status = new Property(3, String.class, "status", false, "STATUS");
        public final static Property Uprice = new Property(4, String.class, "uprice", false, "UPRICE");
        public final static Property SurplusMinute = new Property(5, String.class, "surplusMinute", false, "SURPLUS_MINUTE");
        public final static Property BalanceNum = new Property(6, int.class, "balanceNum", false, "BALANCE_NUM");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CHECK_CELLNUMBER\" TEXT," + // 1: checkCellnumber
                "\"BIND_STATUS\" TEXT," + // 2: bindStatus
                "\"STATUS\" TEXT," + // 3: status
                "\"UPRICE\" TEXT," + // 4: uprice
                "\"SURPLUS_MINUTE\" TEXT," + // 5: surplusMinute
                "\"BALANCE_NUM\" INTEGER NOT NULL );"); // 6: balanceNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String checkCellnumber = entity.getCheckCellnumber();
        if (checkCellnumber != null) {
            stmt.bindString(2, checkCellnumber);
        }
 
        String bindStatus = entity.getBindStatus();
        if (bindStatus != null) {
            stmt.bindString(3, bindStatus);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
 
        String uprice = entity.getUprice();
        if (uprice != null) {
            stmt.bindString(5, uprice);
        }
 
        String surplusMinute = entity.getSurplusMinute();
        if (surplusMinute != null) {
            stmt.bindString(6, surplusMinute);
        }
        stmt.bindLong(7, entity.getBalanceNum());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String checkCellnumber = entity.getCheckCellnumber();
        if (checkCellnumber != null) {
            stmt.bindString(2, checkCellnumber);
        }
 
        String bindStatus = entity.getBindStatus();
        if (bindStatus != null) {
            stmt.bindString(3, bindStatus);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(4, status);
        }
 
        String uprice = entity.getUprice();
        if (uprice != null) {
            stmt.bindString(5, uprice);
        }
 
        String surplusMinute = entity.getSurplusMinute();
        if (surplusMinute != null) {
            stmt.bindString(6, surplusMinute);
        }
        stmt.bindLong(7, entity.getBalanceNum());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // checkCellnumber
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // bindStatus
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // status
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // uprice
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // surplusMinute
            cursor.getInt(offset + 6) // balanceNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCheckCellnumber(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBindStatus(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStatus(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUprice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSurplusMinute(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBalanceNum(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
