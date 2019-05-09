package com.yhw.alixiaohao.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.entity.UserDao;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.receive.ReceiverListner;
import com.yhw.alixiaohao.utils.TimeUtil;
import com.yhw.alixiaohao.utils.phoneUtils.SMSContentObserver;

import org.greenrobot.greendao.query.QueryBuilder;

public class MyService extends Service {

    private SMSContentObserver smsObserver;// 短信观察者
    protected static final int MSG_INBOX = 1;
    private Message msg; // 短信接收者信息


    //服务创建
    @Override
    public void onCreate() {
        super.onCreate();
        // - 注册短信观察者
        smsObserver = new SMSContentObserver(this, smsHandler);
        getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);
    }

    // 服务启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁
    @Override
    public void onDestroy() {
        if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);// 取消监听短信数据库的变化
        }
        stopSelf(); //自杀服务
        super.onDestroy();
    }

    //绑定服务
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    // IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
    // 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
    // 不要直接实现这个接口，而应该从Binder派生。
    // Binder类已实现了IBinder接口
    class MyBinder extends Binder {
        /**
         * 获取Service的方法 * @return 返回PlayerService
         */
        public MyService getService() {
            return MyService.this;
        }
    }

    /**
     * 短信观察者回调
     */
    @SuppressLint("HandlerLeak")
    public Handler smsHandler = new Handler() {
        //这里可以进行回调的操作
        //TODO
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INBOX:
                    getSmsFromPhone();
                    break;
            }
        }
    };

    private Uri SMS_INBOX = Uri.parse("content://sms/inbox");
    public void getSmsFromPhone() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[] { "body","address","date" };//"_id", "address", "person",, "date", "type
        String where = " date >  "
                + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur)
            return;
        if (cur.moveToFirst()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String body = cur.getString(cur.getColumnIndex("body"));// 短信内容
            int dateColumn = cur.getColumnIndex("date");  //时间
            String date = TimeUtil.timeStamp2Date(Long.parseLong(cur.getString(dateColumn)),null);
            //TODO 这里是具体处理逻辑
            if (body.contains("[From{")){
                QueryBuilder<User> qb = ManagerFactory.getInstance().getUserManager().queryBuilder();
                QueryBuilder<User> userQb = qb.where(UserDao.Properties.PhoneNumber.eq(number));
                User user = userQb.unique();
                int txId = user == null ? R.mipmap.lxr_tx_man1 : user.getTxId();
                String name = user == null ? number : user.getName();
                Message msg = new Message();
                msg.setPhoneNumber(number);
                msg.setTxId(txId);
                msg.setContent(body);
                msg.setName(name);
                msg.setTime(date);
                msg.setType(1);
                msg.setMsgType(Message.TYPE_RECEIVED);
                ManagerFactory.getInstance().getMessageManager().saveOrUpdate(msg);// 保存到sqlite
                /*msgList.add(msg);
                // 当有新消息时，刷新ListView中的显示
                adapter.notifyItemInserted(msgList.size() - 1);
                // 将ListView定位到最后一行
                msgRecyclerView.scrollToPosition(msgList.size() - 1);*/
            }
        }
    }

}