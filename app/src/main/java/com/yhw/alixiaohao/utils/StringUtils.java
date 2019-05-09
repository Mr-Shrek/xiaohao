package com.yhw.alixiaohao.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;

/**
 * 字符串操作工具类
 * 如：小写字符转换成大写,提取纬度,提取经度,给文本增加图片
 *
 */
public class StringUtils {

    /**
     * 小写字符转换成大写
     *
     * @param str 转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCase(String str) {
        String upStr = str.toUpperCase();
        String lowStr = str.toLowerCase();
        StringBuffer buf = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == lowStr.charAt(i)) {
                buf.append(upStr.charAt(i));
            } else {
                buf.append(upStr.charAt(i));
            }
        }
        return buf.toString();
    }

    /**
     * 大写字符转换成小写
     *
     * @param str 转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerCase(String str) {
        String upStr = str.toUpperCase();
        String lowStr = str.toLowerCase();
        StringBuffer buf = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == upStr.charAt(i)) {
                buf.append(lowStr.charAt(i));
            } else {
                buf.append(lowStr.charAt(i));
            }
        }
        return buf.toString();
    }

    /**
     * 提取纬度(字符串)
     *
     * @param str 纬度
     * @return Double
     */
    public static Double getLan(String str) {
        if (!str.isEmpty()) {
            String array[] = str.split(",");
            return Double.parseDouble(array[0]);
        }
        return 0.0;
    }

    /**
     * 提取经度(字符串)
     *
     * @param str 经度
     * @return Double
     */
    public static Double getLongitude(String str) {
        if (!str.isEmpty()) {
            String array[] = str.split(",");
            return Double.parseDouble(array[1]);
        }
        return 0.0;
    }

    /**
     * 给文本增加图片
     *
     * @param context 上下文
     * @param text    文本内容
     * @param resId   增加的图片资源
     * @return 增加后的文本内容
     */
    public static CharSequence addPic(final Context context, String text, int resId) {
        String html = text + "![](" + resId + ")";
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int id = Integer.parseInt(source);
                Drawable d = context.getResources().getDrawable(id);
                d.setBounds(0, 0, d.getIntrinsicWidth() / 10 * 8, d.getIntrinsicHeight() / 10 * 8);
                return d;
            }
        };
        return Html.fromHtml(html, imgGetter, null);
    }


    /**
     * 判断str1中是否包含str2
     * @param str1 -
     * @param str2 -
     * @return -
     */
    public static boolean contains(String str1, String str2){
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) return false;
        return str1.contains(str2);
    }
    /**
     * 判断str1与str2是否相等
     * @param str1 -
     * @param str2 -
     * @return -
     */
    public static boolean equals(String str1, String str2){
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) return false;
        return str1.equals(str2);
    }

}
