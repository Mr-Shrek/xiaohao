package com.yhw.alixiaohao.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * DensityUtil
 * @author 崔俊领 <br/>
 * 主要功能：px和dp相互转换类
 * Created by Administrator on 2016/4/13.
 */
public class DensityUtil {
	private static Context context;
	public static void init(Context context1){
		context = context1;
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * @param dpValue:dp值。float类型
	 * @return int
	 * @throws null
	 */
	public static int dp2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	/**
	 * 根据手机的分辨率从 dx 的单位 转成为 dp
	 * @param pxValue:px值。float类型
	 * @return int
	 * @throws null
	 */
	public static int px2dp(float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	public static int getScreenW() {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}
	public static int getScreenH() {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获取状态栏高度
	 */
	public static int getStatusBarHeight(){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	/**
	 * 获取是否存在NavigationBar
	 */
	public static boolean checkDeviceHasNavigationBar() {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;
	}
	/**
	 * 获取导航栏高度
	 * @return
	 */
	public static int getDaoHangHeight() {
		int result = 0;
		int resourceId=0;
		int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
		if (rid!=0){
			resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
			result = context.getResources().getDimensionPixelSize(resourceId);
			Log.e("",">>>>>>>>navigation_bar_height:"+result);
			return result;
		}else return 0;
	}
}
