package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class UserManager extends BaseBeanManager<User, Long> {

    public UserManager(AbstractDao dao) {
        super(dao);
    }

}
