package com.yhw.alixiaohao.model.db;

import com.yhw.alixiaohao.MyApplication;
import com.yhw.alixiaohao.model.db.impl.BindTelManager;
import com.yhw.alixiaohao.model.db.impl.CallRecordManager;
import com.yhw.alixiaohao.model.db.impl.MessageManager;
import com.yhw.alixiaohao.model.db.impl.MyUserManager;
import com.yhw.alixiaohao.model.db.impl.SIMStatusManager;
import com.yhw.alixiaohao.model.db.impl.SmallNumberManager;
import com.yhw.alixiaohao.model.db.impl.SpecTcManager;
import com.yhw.alixiaohao.model.db.impl.UserInfoManager;
import com.yhw.alixiaohao.model.db.impl.UserManager;

/**
 * Created by Jowney on 2018/5/14.
 */

public class ManagerFactory {
    /**
     * 每一个BeanManager都管理着数据库中的一个表，我将这些管理者在ManagerFactory中进行统一管理
     */
    UserManager userManager;

    CallRecordManager callRecordManager;

    SIMStatusManager statusManager;

    MessageManager messageManager;

    UserInfoManager userInfoManager;

    BindTelManager bindTelManager;

    SpecTcManager specTcManager;

    SmallNumberManager smallNumberManager;

    MyUserManager myUserManager;

    private static ManagerFactory mInstance = null;

    /**
     * 获取DaoFactory的实例
     *
     * @return
     */
    public static ManagerFactory getInstance() {
        if (mInstance == null) {
            synchronized (ManagerFactory.class) {
                if (mInstance == null) {
                    mInstance = new ManagerFactory();
                }
            }
        }
        return mInstance;
    }

    public synchronized MyUserManager getMyUserManager(){
        if (myUserManager == null){
            myUserManager = new MyUserManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getMyUserDao());
        }
        return myUserManager;
    }

    public synchronized SmallNumberManager getSmallNumberManager(){
        if (smallNumberManager == null){
            smallNumberManager = new SmallNumberManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getSmallNumberDao());
        }
        return smallNumberManager;
    }

    public synchronized SpecTcManager getSpecTcManager(){
        if (specTcManager == null){
            specTcManager = new SpecTcManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getSpecTCDao());
        }
        return specTcManager;
    }

    public synchronized BindTelManager getBindTelManager(){
        if (bindTelManager == null){
            bindTelManager = new BindTelManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getBindTelDao());
        }
        return bindTelManager;
    }

    public synchronized UserInfoManager getUserInfoManager(){
        if (userInfoManager == null){
            userInfoManager = new UserInfoManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getUserInfoDao());
        }
        return userInfoManager;
    }

    public synchronized MessageManager getMessageManager() {
        if (messageManager == null){
            messageManager = new MessageManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getMessageDao());
        }
        return messageManager;
    }

    public synchronized UserManager getUserManager() {
        if (userManager == null){
            userManager = new UserManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getUserDao());
        }
        return userManager;
    }

    public synchronized CallRecordManager getCallRecordManager(){
        if (callRecordManager == null){
            callRecordManager = new CallRecordManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getCallRecordDao());
        }
        return callRecordManager;
    }

    public synchronized SIMStatusManager getStatusManager(){
        if (statusManager == null){
            statusManager = new SIMStatusManager(DaoManager.getInstance(MyApplication.getContext()).getDaoSession().getSIMStatusDao());
        }
        return statusManager;
    }
}