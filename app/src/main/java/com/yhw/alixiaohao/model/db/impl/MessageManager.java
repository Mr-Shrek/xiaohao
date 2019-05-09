package com.yhw.alixiaohao.model.db.impl;

import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.model.db.BaseBeanManager;

import org.greenrobot.greendao.AbstractDao;

public class MessageManager extends BaseBeanManager<Message, Long> {

    public MessageManager(AbstractDao dao) {
        super(dao);
    }
}
