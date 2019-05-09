package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.adapter.BhUserAdapter;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.common.AppConfig;
import com.yhw.alixiaohao.common.CodeNum;
import com.yhw.alixiaohao.common.UserKey;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.model.HttpCommImpl;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.model.http.CallBackInteface;
import com.yhw.alixiaohao.receive.ReceiverListner;
import com.yhw.alixiaohao.utils.StringUtils;
import com.yhw.alixiaohao.utils.TimeUtil;
import com.yhw.alixiaohao.utils.Utils;
import com.yhw.alixiaohao.view.HorizontalListView;
import com.yhw.alixiaohao.widget.AfterTextWatcher;
import com.yhw.alixiaohao.widget.TitleManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DxEditActivity extends BaseActivity implements TitleManager.OnTitleClickListener,CallBackInteface {

    @BindView(R.id.dx_edit_btn_send)
    Button btnSend;
    @BindView(R.id.dx_edit_message)
    EditText etMessage;
    @BindView(R.id.dx_edit_bottom_leftImg)
    ImageView btnImg;

    @BindView(R.id.dx_edit_addressee)
    EditText etAddressee; // 收件人
    @BindView(R.id.dx_edit_lv)
    HorizontalListView lv; // 横向联系人
    private List<User> list;
    private BhUserAdapter adapter;

    private String api;
    private HttpCommImpl httpComm;

    private String content; // 记录短信内容
    private String phone; // 记录接收人号码

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
        return R.layout.activity_dx_edit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        @SuppressLint("ResourceType") TitleManager titleManager = new TitleManager(this,R.mipmap.back,getString(R.string.back),getString(R.string.text_dx_new_msg),"",R.mipmap.title_right_bh);
        titleManager.setOnTitleClickListener(this);

        httpComm = new HttpCommImpl();
        httpComm.setCallBackInteface(this);

        list = new ArrayList<>();
        adapter = new BhUserAdapter(this, list);
        lv.setAdapter(adapter);
        etAddressee.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    list.clear();
                    String content = s.toString();
                    List<User> users = ManagerFactory.getInstance().getUserManager().query("where phone_number like ?",content+"%");
                    for (User u : users){
                        list.add(u);
                    }
                    adapter.notifyDataSetChanged();
                } else{
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TextUtils.isEmpty(list.get(position).getPhoneNumber())) {
                    etAddressee.setText(list.get(position).getPhoneNumber());
                }
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }


    @OnClick({R.id.dx_edit_btn_send})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.dx_edit_btn_send:
                content = etMessage.getText().toString().trim();
                phone = etAddressee.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "接收人不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                btnSend.setEnabled(false);
                api = AppConfig.SMSC_NOTIFY;
                String time = TimeUtil.getCurrTimeByInternet(this);
                httpComm.smsc_notify(UserKey.key, content, TimeUtil.getCurrTime(""), phone, "虚拟号码");
                break;
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onResponse(Object data) {
        JSONObject json = (JSONObject) data;
        //        String api = json.getString("api");//请求id
        switch (api){
            case AppConfig.SMSC_NOTIFY:
                if (checkResponse(json)){
                    SendMsgIfSuc(phone, content);
                } else {
                    btnSend.setEnabled(true);
                    String code =json.getString("code");
                    if ( !showErrorTip(code)){
                        //...
                        switch (code){
                            case CodeNum.E00201:
                                Toast.makeText(this, "账号异常", Toast.LENGTH_SHORT).show();
                                break;
                            case CodeNum.E00202:
                                Toast.makeText(this, "通话分钟/余额不足", Toast.LENGTH_SHORT).show();
                                break;
                            case CodeNum.E00203:
                                Toast.makeText(this, "虚拟号码或套餐无短信发送权限", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onError(Throwable e) {

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
            Toast.makeText(DxEditActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // 广播注册
        intentfilter.addAction(SMS_SEND_ACTION);
        registerReceiver(receiverlistner, intentfilter);
    }
    /*******************************************************end*****************************************************/
}
