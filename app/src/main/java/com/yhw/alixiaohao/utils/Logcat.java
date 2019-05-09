package com.yhw.alixiaohao.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Logcat --
 */
public class Logcat {

    private String TAG;

    /**
     * 绑定 TAG 为类名
     * @param c
     */
    public Logcat(Class c){
        TAG = c.getSimpleName();
    }

    public void d(String msg){
        Log.d(TAG,TextUtils.isEmpty(msg) ? "" : msg);
    }

    public void i(String msg){
        Log.i(TAG,TextUtils.isEmpty(msg) ? "" : msg);
    }

    public void e(String msg){
        Log.e(TAG,TextUtils.isEmpty(msg) ? "" : msg);
    }

    public void v(String msg){
        Log.v(TAG,TextUtils.isEmpty(msg) ? "" : msg);
    }

    public void w(String msg){
        Log.w(TAG,TextUtils.isEmpty(msg) ? "" : msg);
    }
}
