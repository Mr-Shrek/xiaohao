package com.yhw.alixiaohao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class SPUtil {
    private static Context context;
    public static void init(Context context1){
        context = context1;
    }
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";
    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param object
     */
    public static void setParam(String key, Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            if(TextUtils.isEmpty(object.toString())){
                editor.remove(key);
            }else {
                editor.putString(key, (String) object);
            }
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @return
     */
    public static String getSPString(String key){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }
}
