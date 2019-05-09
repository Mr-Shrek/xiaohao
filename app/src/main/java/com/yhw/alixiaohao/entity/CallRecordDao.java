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
 * DAO for table "CALL_RECORD".
*/
public class CallRecordDao extends AbstractDao<CallRecord, Long> {

    public static final String TABLENAME = "CALL_RECORD";

    /**
     * Properties of entity CallRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Calling = new Property(1, String.class, "calling", false, "CALLING");
        public final static Property Called = new Property(2, String.class, "called", false, "CALLED");
        public final static Property CalledName = new Property(3, String.class, "calledName", false, "CALLED_NAME");
        public final static Property InventedNum = new Property(4, String.class, "inventedNum", false, "INVENTED_NUM");
        public final static Property StartTime = new Property(5, String.class, "startTime", false, "START_TIME");
        public final static Property CallDuration = new Property(6, String.class, "callDuration", false, "CALL_DURATION");
        public final static Property CallStatus = new Property(7, int.class, "callStatus", false, "CALL_STATUS");
        public final static Property CallType = new Property(8, int.class, "callType", false, "CALL_TYPE");
    }


    public CallRecordDao(DaoConfig config) {
        super(config);
    }
    
    public CallRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CALL_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CALLING\" TEXT," + // 1: calling
                "\"CALLED\" TEXT," + // 2: called
                "\"CALLED_NAME\" TEXT," + // 3: calledName
                "\"INVENTED_NUM\" TEXT," + // 4: inventedNum
                "\"START_TIME\" TEXT," + // 5: startTime
                "\"CALL_DURATION\" TEXT," + // 6: callDuration
                "\"CALL_STATUS\" INTEGER NOT NULL ," + // 7: callStatus
                "\"CALL_TYPE\" INTEGER NOT NULL );"); // 8: callType
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CALL_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CallRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String calling = entity.getCalling();
        if (calling != null) {
            stmt.bindString(2, calling);
        }
 
        String called = entity.getCalled();
        if (called != null) {
            stmt.bindString(3, called);
        }
 
        String calledName = entity.getCalledName();
        if (calledName != null) {
            stmt.bindString(4, calledName);
        }
 
        String inventedNum = entity.getInventedNum();
        if (inventedNum != null) {
            stmt.bindString(5, inventedNum);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(6, startTime);
        }
 
        String callDuration = entity.getCallDuration();
        if (callDuration != null) {
            stmt.bindString(7, callDuration);
        }
        stmt.bindLong(8, entity.getCallStatus());
        stmt.bindLong(9, entity.getCallType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CallRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String calling = entity.getCalling();
        if (calling != null) {
            stmt.bindString(2, calling);
        }
 
        String called = entity.getCalled();
        if (called != null) {
            stmt.bindString(3, called);
        }
 
        String calledName = entity.getCalledName();
        if (calledName != null) {
            stmt.bindString(4, calledName);
        }
 
        String inventedNum = entity.getInventedNum();
        if (inventedNum != null) {
            stmt.bindString(5, inventedNum);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(6, startTime);
        }
 
        String callDuration = entity.getCallDuration();
        if (callDuration != null) {
            stmt.bindString(7, callDuration);
        }
        stmt.bindLong(8, entity.getCallStatus());
        stmt.bindLong(9, entity.getCallType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CallRecord readEntity(Cursor cursor, int offset) {
        CallRecord entity = new CallRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // calling
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // called
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // calledName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // inventedNum
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // startTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // callDuration
            cursor.getInt(offset + 7), // callStatus
            cursor.getInt(offset + 8) // callType
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CallRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCalling(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCalled(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCalledName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setInventedNum(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setStartTime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCallDuration(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCallStatus(cursor.getInt(offset + 7));
        entity.setCallType(cursor.getInt(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CallRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CallRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CallRecord entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
