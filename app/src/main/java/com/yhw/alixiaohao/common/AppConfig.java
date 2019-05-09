package com.yhw.alixiaohao.common;

public class AppConfig {

    public static boolean DEBUG = true;
//    public static final String BASE_URL = "http://localhost:8080/";
    public static final String BASE_URL = "http://www.jvj.cn:9998/";

    public static final String CHECK_USER_NAME = "checkUserName";//验证手机号码是否可登陆

    public static final String SEND_CODE = "sendCode";//获取短信验证码

    public static final String CHECK_CODE = "checkCode";//验证短信验证码

    public static final String GET_U = "getu";//获取菜单“我的”以及其关联数据

    public static final String SMSC_NOTIFY = "smsc_notify"; // 短信发送通知接口

}
