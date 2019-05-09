package com.yhw.alixiaohao.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 安装包工具类
 */
public class PackageUtil {

    private static Logcat log = new Logcat(PackageUtil.class);

    /**
     * 获取软件信息
     * @param context -
     * @return -
     */
    public static PackageInfo getPackageInfo(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            log.e(e.getLocalizedMessage());
        }
        return  new PackageInfo();
    }

}
