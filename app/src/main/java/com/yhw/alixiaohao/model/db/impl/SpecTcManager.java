package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.SpecTC;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class SpecTcManager extends BaseBeanManager<SpecTC, Long> {
    public SpecTcManager(AbstractDao dao) {
        super(dao);
    }
}
