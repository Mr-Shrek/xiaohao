package com.yhw.alixiaohao.model.http;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yhw.alixiaohao.common.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    private RetrofitManager(){
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance(){

        if (mRetrofitManager == null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private void initRetrofit() {
        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HttpCommonInterceptor());

        if (AppConfig.DEBUG){
            builder.addInterceptor(LoginInterceptor);
        }

        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
    //json格式数据交互
    //MyService:
    //    @POST("/sendCode")
    //    @Headers("Content-Type:application/json")
    //    Observable<JSONObject> sendCode(@Body RequestBody requestBody);
    public RequestBody getForApplication(String jsonResult){
        return RequestBody.create(MediaType.parse("application/json;charset=utf-8"),jsonResult);
    }

    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }


    //from-data - MyService：
    //      @Multipart
    //      @POST("/login")
    //      Observable<JSONObject> userLogin(@PartMap Map<String, RequestBody> requestBodyMap);
    //比如可以这样生成Map<String, RequestBody> requestBodyMap
    //Map<String, String> requestDataMap这里面放置上传数据的键值对。
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

}