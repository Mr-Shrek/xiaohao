package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.adapter.DxInfoAdapter;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.other.ParamEvent;
import com.yhw.alixiaohao.other.bh.BhEvent;
import com.yhw.alixiaohao.receive.ReceiverListner;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.TimeUtil;
import com.yhw.alixiaohao.utils.phoneUtils.SMSContentObserver;
import com.yhw.alixiaohao.widget.TitleManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DxInfoActivity extends BaseActivity implements TitleManager.OnTitleClickListener {

    private final Logcat log = new Logcat(DxInfoActivity.class);

    private List<Message> msgList = new ArrayList<>();
    @BindView(R.id.dx_info_message)
    EditText inputText;
    @BindView(R.id.dx_info_msg_recycle_view)
    RecyclerView msgRecyclerView;
    private DxInfoAdapter adapter;// 短信适配器
//    private SMSContentObserver smsObserver;// 短信观察者
//    protected static final int MSG_INBOX = 1;
    private Message msg; // 短信接收者信息
    private Message tempMsg;// 临时存储要发送的短信
    private int tempIndex; // 记录临时存储的当前在发送的短信索引

    // 自定义ACTION常数作为Intent Filter识别常数
    private String SMS_SEND_ACTION = "SMS_SEND_ACTION";
    // receiverlistner作为registerReceiver方法的receiver参数
    private ReceiverListner receiverlistner = new ReceiverListner();
    // intentfilter作为registerReceiver方法的filter参数
    private IntentFilter intentfilter = new IntentFilter();


    @Override
    public void afterSetView() {
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_dx_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        msg = (Message) getIntent().getSerializableExtra("data");
        if (msg == null){
            finish();
            return;
        }
        @SuppressLint("ResourceType") TitleManager titleManager = new TitleManager(this,R.mipmap.back,getString(R.string.back),msg.getName(),"",R.mipmap.title_right_bh);
        titleManager.setOnTitleClickListener(this);

        List<Message> messages = ManagerFactory.getInstance().getMessageManager().query("where phone_number = ? order by time asc", msg.getPhoneNumber());
        for (Message sm : messages){
            log.e("sm:"+sm.getContent()+" time:"+sm.getTime());
            msgList.add(sm);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new DxInfoAdapter(msgList, this);
        msgRecyclerView.setAdapter(adapter);

        // - 注册短信观察者
//        smsObserver = new SMSContentObserver(this, smsHandler);
        receiverlistner.setOnSMSCallBackListener(new ReceiverListner.OnSMSCallBackListener() {
            @Override
            public void onCallBack(boolean flag) {
                if (flag){
                    if (tempMsg != null){
                        tempMsg.setType(1);
                        ManagerFactory.getInstance().getMessageManager().saveOrUpdate(tempMsg); // 保存到SqlLite
                        // 当有新消息时，刷新ListView中的显示
                        log.e("temIndex:"+tempIndex + "  size:"+msgList.size());
                        adapter.notifyDataSetChanged();
                       /* adapter.notifyItemInserted(tempIndex);
                        // 将ListView定位到最后一行
                        msgRecyclerView.scrollToPosition(tempIndex);*/
                    }
                }else {
                    if (tempMsg != null) {
                        tempMsg.setType(2);
                        ManagerFactory.getInstance().getMessageManager().saveOrUpdate(tempMsg); // 保存到SqlLite
                        /*// 当有新消息时，刷新ListView中的显示
                        adapter.notifyItemRangeRemoved(0, oldContentSize);
                        // 将ListView定位到最后一行
                        msgRecyclerView.scrollToPosition(tempIndex);*/
                    }
                }
            }
        });

    }

    @OnClick({R.id.dx_info_btn_send})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dx_info_btn_send:
                String content = inputText.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(this, "发送信息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SendMsgIfSuc(msg.getPhoneNumber(), content);
                Message msg = new Message();
                msg.setPhoneNumber(this.msg.getPhoneNumber());
                msg.setMsgType(Message.TYPE_SENT);
                msg.setName(this.msg.getName());
                msg.setTxId(this.msg.getTxId());
                msg.setContent(content);
                msg.setType(0);
                msg.setTime(TimeUtil.getCurrTime("yyyy-MM-dd HH:mm:ss"));
                tempMsg = msg;
                ManagerFactory.getInstance().getMessageManager().saveOrUpdate(tempMsg); // 保存到SqlLite
                msgList.add(msg);
                tempIndex = msgList.size() - 1;
                // 当有新消息时，刷新ListView中的显示
                adapter.notifyItemInserted(tempIndex);
                // 将ListView定位到最后一行
                msgRecyclerView.scrollToPosition(tempIndex);
                // 清空输入框中的内容
                inputText.setText("");
                /*SmsManager smsManager = SmsManager.getDefault();
                if(content.length() > 70) {
                    List<String> contents = smsManager.divideMessage(content);
                    for(String sms : contents) {
                        smsManager.sendTextMessage(msg.getPhoneNumber(), null, sms, null, null);
                    }
                } else {
                    smsManager.sendTextMessage(msg.getPhoneNumber(), null, content, null, null);
                }
                Toast.makeText(DxInfoActivity.this, "发送完成", Toast.LENGTH_SHORT).show();*/
                break;
        }
    }


    /***************************************************************************************************************
     * 功能：发送短信并监听是否发送成功
     * 参数：num-电话号码	msg-信息内容
     * 说明：自定义方法SendMsgIfSuc，短信发送成功时Toast提醒
     * 日期：2014.01.20
     * 作者：lwj
     ***************************************************************************************************************/
    private void SendMsgIfSuc(String num, String msg)
    {
        SmsManager sms = SmsManager.getDefault();
        try{
            // 创建ACTION常数的Intent，作为PendingIntent的参数
            Intent SendIt = new Intent(SMS_SEND_ACTION);

            // 接收消息传送后的广播信息SendPendIt，作为信息发送sendTextMessage方法的sentIntent参数
            PendingIntent SendPendIt = PendingIntent.getBroadcast(getApplicationContext(), 0, SendIt, PendingIntent.FLAG_CANCEL_CURRENT);

            // 发送短信
            sms.sendTextMessage(num, null, msg, SendPendIt, null);

        }catch (Exception e) {
            // 异常提醒
            Toast.makeText(DxInfoActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // 广播注册
        intentfilter.addAction(SMS_SEND_ACTION);
        registerReceiver(receiverlistner, intentfilter);
    }
    /*******************************************************end*****************************************************/


    /**
     * 短信观察者回调
     *//*
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
    };*/


    @Override
    protected void onResume() {
        super.onResume();
        /*if (smsObserver != null) {
            getContentResolver().registerContentObserver(SMS_INBOX, true,
                    smsObserver);
        }*/
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        /*if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);// 取消监听短信数据库的变化
        }*/

    }


    /*private Uri SMS_INBOX = Uri.parse("content://sms/inbox");
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
            if (number.equals(msg.getPhoneNumber())){
                Message msg = new Message();
                msg.setPhoneNumber(number);
                msg.setTxId(this.msg.getTxId());
                msg.setContent(body);
                msg.setName(this.msg.getName());
                msg.setTime(date);
                msg.setType(1);
                msg.setMsgType(Message.TYPE_RECEIVED);
                ManagerFactory.getInstance().getMessageManager().saveOrUpdate(msg);// 保存到sqlite
                msgList.add(msg);
                // 当有新消息时，刷新ListView中的显示
                adapter.notifyItemInserted(msgList.size() - 1);
                // 将ListView定位到最后一行
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
            }
        }
    }*/

    @Override
    public void destroy() {
        /*if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);
        }*/
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {
        BhEvent bhEvent = new BhEvent();
        bhEvent.setPhone(msg.getPhoneNumber());
        ParamEvent<BhEvent> paramEvent = new ParamEvent<>();
        paramEvent.setType(2);
        paramEvent.setT(bhEvent);
        EventBus.getDefault().post(paramEvent);
        finish();
    }
}
