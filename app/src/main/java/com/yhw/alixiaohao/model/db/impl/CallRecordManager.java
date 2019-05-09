package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class CallRecordManager extends BaseBeanManager<CallRecord,Long> {
    public CallRecordManager(AbstractDao dao) {
        super(dao);
    }
}
