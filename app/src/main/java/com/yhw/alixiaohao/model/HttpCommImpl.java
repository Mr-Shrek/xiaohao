package com.yhw.alixiaohao.model;

import com.alibaba.fastjson.JSONObject;
import com.yhw.alixiaohao.model.http.BaseObserver;
import com.yhw.alixiaohao.model.http.CallBackInteface;
import com.yhw.alixiaohao.model.http.MyService;
import com.yhw.alixiaohao.model.http.RetrofitManager;
import com.yhw.alixiaohao.utils.Logcat;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HttpCommImpl {

    private final Logcat log = new Logcat(HttpCommImpl.class);

    private CallBackInteface callBackInteface;

    public HttpCommImpl(){

    }

    /**
     * 校验手机号是否可登陆
     * @param tel
     */
    public void checkUserName(String tel){
//        JSONObject json = new JSONObject();
//        json.put("tel", tel);
//        String string = json.toJSONString();
        Map<String,String> map = new HashMap<>();
        map.put("tel", tel);
//        log.i("校验手机号是否可登陆入参：" + string);
        log.i("校验手机号是否可登陆入参：" + map);
//        RetrofitManager.getInstance().createReq(MyService.class).checkUserName(RetrofitManager.getInstance().getForApplication(string))
        RetrofitManager.getInstance().createReq(MyService.class).checkUserName(RetrofitManager.getInstance().generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);
                        log.i("校验手机号是否可登陆出参：" + value);
                        callBackInteface.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBackInteface.onError(e);
                    }
                });
    }


    /**
     * 发送验证码
     * @param tel
     */
    public void sendCode(String tel){
        Map<String,String> map = new HashMap<>();
        map.put("tel", tel);
        log.i("获取验证码入参：" + map);
        RetrofitManager.getInstance().createReq(MyService.class).sendCode(RetrofitManager.getInstance().generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);
                        log.i("获取验证码出参：" + value);
                        callBackInteface.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBackInteface.onError(e);
                    }
                });
    }

    /**
     * 检测验证码
     */
    public void checkCode(String tel, String code){
        Map<String,String> map = new HashMap<>();
        map.put("tel", tel);
        map.put("code",code);
        log.i("检测验证码入参：" + map);
        RetrofitManager.getInstance().createReq(MyService.class).checkCode(RetrofitManager.getInstance().generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);
                        log.i("检测验证码出参：" + value);
                        callBackInteface.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBackInteface.onError(e);
                    }
                });
    }

    /**
     * 获取菜单“我的”以及其关联数据
     * @param userkey
     */
    public void getu(String userkey){
        Map<String,String> map = new HashMap<>();
        map.put("userkey", userkey);
        log.i("获取菜单入参：" + map);
        RetrofitManager.getInstance().createReq(MyService.class).getu(RetrofitManager.getInstance().generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);
                        log.i("获取菜单出参：" + value);
                        callBackInteface.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBackInteface.onError(e);
                    }
                });
    }


    /**
     * 短信发送通知接口
     * @param userkey key
     * @param content content
     * @param time time
     * @param recipientNumber 短信接收人
     * @param virtualNumber 虚拟号码
     */
    public void smsc_notify(String userkey, String content, String time, String recipientNumber, String virtualNumber){
        Map<String,String> map = new HashMap<>();
        map.put("userkey", userkey);
        map.put("content", content);
        map.put("time", time);
        map.put("recipientNumber", recipientNumber);
        map.put("virtualNumber", virtualNumber);
        log.i("短信发送通知接口入参：" + map);
        RetrofitManager.getInstance().createReq(MyService.class).sms_notify(RetrofitManager.getInstance().generateRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onNext(Object value) {
                        super.onNext(value);
                        log.i("短信发送通知接口出参：" + value);
                        callBackInteface.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        callBackInteface.onError(e);
                    }
                });
    }

    public void setCallBackInteface(CallBackInteface callBackInteface) {
        this.callBackInteface = callBackInteface;
    }

}
