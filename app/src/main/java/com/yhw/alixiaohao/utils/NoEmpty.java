package com.yhw.alixiaohao.utils;

import android.text.TextUtils;

public class NoEmpty<T> {

    public NoEmpty(){

    }

    public static String strIsEmpty(String str){
        if (TextUtils.isEmpty(str)){
            return "";
        }
        return str;
    }

    public boolean checkNull(T t){
        if (t == null){
            return false;
        }
        return true;
    }

}
