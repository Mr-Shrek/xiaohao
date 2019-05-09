package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.SmallNumber;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class SmallNumberManager extends BaseBeanManager<SmallNumber, Long> {
    public SmallNumberManager(AbstractDao dao) {
        super(dao);
    }
}
