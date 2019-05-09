package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.SIMStatus;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class SIMStatusManager extends BaseBeanManager<SIMStatus, Long> {
    public SIMStatusManager(AbstractDao dao) {
        super(dao);
    }
}
