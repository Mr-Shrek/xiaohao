package com.yhw.alixiaohao.model.http;




import com.alibaba.fastjson.JSONObject;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface MyService {

    /**
     * 校验手机号是否可以登陆
     * @param requestBodyMap
     * @return
     */
    @Multipart
    @POST("/checkUserName")
    Observable<JSONObject> checkUserName(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 发送短信验证码
     * @return
     */
    @Multipart
    @POST("/sendCode")
    Observable<JSONObject> sendCode(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 短信验证码校验
     * @return
     */
    @Multipart
    @POST("/checkCode")
    Observable<JSONObject> checkCode(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 获取菜单数据“我的”
     * @return
     */
    @Multipart
    @POST("/getu")
    Observable<JSONObject> getu(@PartMap Map<String, RequestBody> requestBodyMap);


    /**
     * 短信发送通知接口
     * @return
     */
    @Multipart
    @POST("/smsc_notify")
    Observable<JSONObject> sms_notify(@PartMap Map<String, RequestBody> requestBodyMap);
}
