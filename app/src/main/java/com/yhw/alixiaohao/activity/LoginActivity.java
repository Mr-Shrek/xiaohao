package com.yhw.alixiaohao.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.base.PermissionBaseActivity;
import com.yhw.alixiaohao.common.AppConfig;
import com.yhw.alixiaohao.common.CodeNum;
import com.yhw.alixiaohao.common.SIMConfig;
import com.yhw.alixiaohao.entity.SIMStatus;
import com.yhw.alixiaohao.entity.SIMStatusDao;
import com.yhw.alixiaohao.model.HttpCommImpl;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.model.http.CallBackInteface;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.Utils;
import com.yhw.alixiaohao.utils.simUtils.SIMInfoUtil;
import com.yhw.alixiaohao.view.CountdownTextView;
import com.yhw.alixiaohao.widget.AfterTextWatcher;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends PermissionBaseActivity implements CallBackInteface {

    private final Logcat log = new Logcat(LoginActivity.class);

    @BindView(R.id.loginPhoneEt)
    EditText phoneEt;
    @BindView(R.id.loginCodeEt)
    EditText codeEt;
    @BindView(R.id.loginGetCodeBtn)
    CountdownTextView getCodeBtn;

    private String tel;//输入的手机号码
    private String code;//网络获取的验证码

    private boolean isFirst = true; // 判断是否是首次登陆,默认是首次登陆

    private HttpCommImpl httpComm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_SMS
        }, 0);

        /*codeEt.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 4){
                    httpComm.checkCode(tel, code);
                }
            }
        });*/
        getCodeBtn.init("%s", 10);//初始化倒计时时间
        //倒计时按钮时间结束回调
        getCodeBtn.setOnResultCallBackInterface(new CountdownTextView.OnResultCallBackInterface() {
            @Override
            public void callBack() {
                code = "";
                getCodeBtn.setText("获取验证码");
            }
        });
        //...
        List<SIMStatus> list = ManagerFactory.getInstance().getStatusManager().queryAll();
        if (list != null && list.size() > 0){
            for (SIMStatus s : list){
                log.e("s:"+s.toString());
            }
        }
    }

    @OnClick({R.id.loginGetCodeBtn,R.id.loginLoginBtn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginGetCodeBtn:
                tel = phoneEt.getText().toString().trim();
                if (Utils.isMobileNO(tel)){
                    api = AppConfig.CHECK_USER_NAME;
                    httpComm.checkUserName(tel);
                } else {
                    Toast.makeText(this, "手机号格式输入错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.loginLoginBtn:
                String code = codeEt.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (code.length() != 4 ){
                    Toast.makeText(this, "验证码位数输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                api = AppConfig.CHECK_CODE;
                httpComm.checkCode(tel,code);
                break;
        }
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0:
                Toast.makeText(LoginActivity.this, "权限获取成功", Toast.LENGTH_LONG).show();
                httpComm = new HttpCommImpl();
                httpComm.setCallBackInteface(this);
                SIMConfig.cardSlotCount = SIMInfoUtil.getCardSlotCount(this);// 获取卡槽数量
                SIMConfig.simCount = SIMInfoUtil.getCardCount(this);// 获取sim卡数量
                if (SIMConfig.cardSlotCount == -1 || SIMConfig.simCount == -1){
                    Toast.makeText(this, "获取基础信息失败", Toast.LENGTH_SHORT).show();
                }
                List<Integer> slots = SIMInfoUtil.getSlotStatus(); //获取卡槽状态
                if (slots.size() == 1 && SIMConfig.simCount == 1){ //如果获取到卡槽，并且sim卡的数量为1
                    SIMConfig.currSim = slots.get(0);
                }
                break;
        }
    }

    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        switch (requestCode) {
            case 0:
                Toast.makeText(LoginActivity.this, "权限获取失败", Toast.LENGTH_LONG).show();
//                finish();
                break;
        }
    }

    private String api = "";

    @Override
    public void onResponse(Object data) {
        JSONObject json = (JSONObject) data;
//        String api = json.getString("api");//请求id
        switch (api){
            case AppConfig.CHECK_USER_NAME://验证手机号是否可登陆
                if (checkResponse(json)){
                    api = AppConfig.SEND_CODE;
                    httpComm.sendCode(tel);
                } else{
                    String code =json.getString("code");
                    if ( !showErrorTip(code)){
                        //...
                    }
                }
                break;
            case AppConfig.SEND_CODE://获取短信验证码
                if (checkResponse(json)){
                    Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
                    getCodeBtn.start(0);
                } else {
                    String code = json.getString("code");
                    if (!showErrorTip(code)){
                        switch (code){
                            case CodeNum.E00000:
                                Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
                break;
            case AppConfig.CHECK_CODE://校验验证码
                if (checkResponse(json)){
                    login();
                } else{
                    String code =json.getString("code");
                    if ( !showErrorTip(code)){
                        //...
                    }
                }
                break;
        }
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this, "接口服务维护中...", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    /**
     * 登陆校验
     */
    private void login(){
        log.e("simCount:" + SIMConfig.simCount);
        log.e("cardCount:" + SIMConfig.cardSlotCount);
        QueryBuilder<SIMStatus> qb = ManagerFactory.getInstance().getStatusManager().queryBuilder();
        QueryBuilder<SIMStatus> statusQb = qb.where(SIMStatusDao.Properties.Account.eq(tel));
        SIMStatus simStatus;
        if (statusQb != null && statusQb.unique() != null){ // 查询到表中包含当前手机信息，曾经已登陆过
            isFirst = false;
            simStatus = statusQb.unique();
        } else { // 该号码第一次登陆本系统
            isFirst = true;
            simStatus = new SIMStatus();
        }
        if (SIMConfig.currSim != -1){// 判断获取权限后 查询到手机仅插入一张sim卡，并获取到sim位置
            simStatus.setAccount(tel);
            SIMConfig.tel = tel;
            if (SIMConfig.currSim == 0){ // 判断当前可用的sim是卡 1
                simStatus.setSim1(tel);
                simStatus.setSim2("");
                SIMConfig.sim1 = tel;
            }else{ // 判断当前可用的sim是卡 2
                simStatus.setSim2(tel);
                simStatus.setSim1("");
                SIMConfig.sim2 = tel;
            }
            ManagerFactory.getInstance().getStatusManager().saveOrUpdate(simStatus);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }
        if (!isFirst){ // 非首次登陆
            int count = 0;// 记录保存了几个SIM卡手机号
            if (!TextUtils.isEmpty(simStatus.getSim1())){
                count ++;
            }
            if (!TextUtils.isEmpty(simStatus.getSim2())){
                count ++;
            }
            if (count == SIMConfig.simCount){//记录的手机号数量和当前登陆数量相同进入主页
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }else{//新插入了SIM卡，再次配置SIM卡信息
                Intent in = new Intent(LoginActivity.this, InitSIMActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("simInfo", simStatus);
                in.putExtras(b);
                startActivity(in);
            }
        } else {
            Intent in = new Intent(LoginActivity.this, InitSIMActivity.class);
            simStatus.setAccount(tel);
            Bundle b = new Bundle();
            b.putSerializable("simInfo", simStatus);
            in.putExtras(b);
            startActivity(in);
        }
        finish();
    }

}
