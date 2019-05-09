package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.MyUser;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class MyUserManager extends BaseBeanManager<MyUser, Long> {

    public MyUserManager(AbstractDao dao) {
        super(dao);
    }



}
