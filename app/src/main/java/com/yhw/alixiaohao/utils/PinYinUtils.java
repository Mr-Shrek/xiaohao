package com.yhw.alixiaohao.utils;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by e on 2017/6/24.
 */

public class PinYinUtils {

    private static Logcat log = new Logcat(PinYinUtils.class);

    public static StringBuffer sb = new StringBuffer();

    /**
     * 返回首字母，大写
     * @param str
     * @return
     */
    public static String getFirstLetter(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        // 得到一个字符串的拼音的大写
        String pinyinStr = getPinyin(str).toUpperCase();
        // 取拼音字符串的第一个字母
//        String first = pinyinStr.substring(0, 1).toLowerCase();
//        if (first.matches("[a-z]")) {
//
//        } else {
//
//        }
        char firstCahr = pinyinStr.charAt(0);
        // 不是A-Z字母
        if (firstCahr > 90 || firstCahr < 65) {
            return "#";
        }else{ // 代表是A-Z
            return String.valueOf(firstCahr);
        }

    }

    /**
     * 得到一个字符串的拼音读音
     *
     * @param chineseStr
     * @return
     */
    public static String getPinyin(String chineseStr) {
        StringBuffer sb = new StringBuffer();
        // 将汉字拆分成一个个的char
        char[] chars = chineseStr.toCharArray();
        // 遍历汉字的每一个char
        for (int i = 0; i < chars.length; i++) {

            try {
                // 汉字的所有读音放在一个pinyins数组
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultFormat());
                if (pinyins == null) {
                    sb.append(chars[i]);
                } else {
                    sb.append(pinyins[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYin(String chines) {
        sb.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0]);
                } catch (Exception e) {
//                    e.printStackTrace();
                    log.i("汉字转换拼音失败，汉字内容："+chines);
                }
            } else {
                sb.append(nameChar[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 设置默认的输出格式
     *
     * @return
     */
    public static HanyuPinyinOutputFormat getDefaultFormat() {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        // 去除声调
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 小写
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 包含Unicode特殊字符
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return outputFormat;
    }
}