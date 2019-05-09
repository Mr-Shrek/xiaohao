package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.UserInfo;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class UserInfoManager extends BaseBeanManager<UserInfo, Long> {
    public UserInfoManager(AbstractDao dao) {
        super(dao);
    }
}
