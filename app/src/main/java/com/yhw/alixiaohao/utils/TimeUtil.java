package com.yhw.alixiaohao.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;

import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.entity.Message;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TimeUtil {

    /**
     * 获取当前时间
     * @param format
     * @return
     */
    public static String getCurrTime(String format){
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 从网络获取北京时间
     * @return
     */
    public static String getCurrTimeByInternet(final Activity activity){
        /*new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("http://www.bjtime.cn");//取得资源对象
                    URLConnection uc = url.openConnection();//生成连接对象
                    uc.connect(); //发出连接
                    final long ld = uc.getDate(); //取得网站日期时间
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //已在主线程中，可以更新UI
//                            return timeStamp2Date(ld, "");
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();*/

        //Date date = new Date(ld); //转换为标准时间对象
        //分别取得时间中的小时，分钟和秒，并输出

        return getCurrTime("");
    }

    /**
     *  Long 转 string
     * @param time
     * @param format
     * @return
     */
    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    public static long getCurrTimeLong(){
        return System.currentTimeMillis();
    }

    /**
     * 获取T天前后的时间
     * @param t 。
     * @return /
     */
    public static String getTDate(int t){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar =Calendar. getInstance();
        calendar.add( Calendar. DATE, t); //向前走一天
        return df.format(calendar.getTime());
    }

    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour
     * 开始小时，例如22
     * @param beginMin
     * 开始小时的分钟数，例如30
     * @param endHour
     * 结束小时，例如 8
     * @param endMin
     * 结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int beginSecond, int endHour, int endMin,int endSecond) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;
        startTime.second = beginSecond;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;
        endTime.second = endSecond;

        if (!startTime.before(endTime)) {
            // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
            // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        startTime = null;
        endTime = null;
        return result;
    }


    /**
     * 判断当前系统时间是否在指定时间的范围内
     *
     * @param beginHour
     * 开始小时，例如22
     * @param beginMin
     * 开始小时的分钟数，例如30
     * @param endHour
     * 结束小时，例如 8
     * @param endMin
     * 结束小时的分钟数，例如0
     * @return true表示在范围内，否则false
     */
    public static boolean isCurrentInTimeScope(int beginHour, int beginMin, int endHour, int endMin) {
        boolean result = false;
        final long aDayInMillis = 1000 * 60 * 60 * 24;
        final long currentTimeMillis = System.currentTimeMillis();

        Time now = new Time();
        now.set(currentTimeMillis);

        Time startTime = new Time();
        startTime.set(currentTimeMillis);
        startTime.hour = beginHour;
        startTime.minute = beginMin;

        Time endTime = new Time();
        endTime.set(currentTimeMillis);
        endTime.hour = endHour;
        endTime.minute = endMin;

        if (!startTime.before(endTime)) {
            // 跨天的特殊情况（比如22:00-8:00）
            startTime.set(startTime.toMillis(true) - aDayInMillis);
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
            Time startTimeInThisDay = new Time();
            startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis);
            if (!now.before(startTimeInThisDay)) {
                result = true;
            }
        } else {
            // 普通情况(比如 8:00 - 14:00)
            result = !now.before(startTime) && !now.after(endTime); // startTime <= now <= endTime
        }
        startTime = null;
        endTime = null;
        return result;
    }


    /***
     * 获取当前日期距离过期时间的日期差值
     * @param endTime
     * @return
     */
    public static String dateDiff(String endTime) {
        String strTime = null;
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = sd.format(curDate);
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(str).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            if (day >= 1) {
                strTime = day + "天" + hour + "时";
            } else {

                if (hour >= 1) {
                    strTime = day + "天" + hour + "时" + min + "分";

                } else {
                    if (sec >= 1) {
                        strTime = day + "天" + hour + "时" + min + "分" + sec + "秒";
                    } else {
                        strTime = "显示即将到期";
                    }
                }
            }

            return strTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 由过去的某一时间,计算距离当前的时间
     */
    public static String calculateTime(String time) {
        long nowTime = System.currentTimeMillis(); // 获取当前时间的毫秒数
        String msg = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 指定时间格式
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");// 指定时间格式
        SimpleDateFormat df1 = new SimpleDateFormat("MM-dd");// 指定时间格式
        Date setTime = null; // 指定时间
        try {
            setTime = sdf.parse(time); // 将字符串转换为指定的时间格式
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String hhmm = df.format(setTime);
        String mmdd = df1.format(setTime);
        long reset = setTime.getTime(); // 获取指定时间的毫秒数
        long dateDiff = nowTime - reset;
        if (dateDiff < 0) {
            msg = "输入的时间不对";
        } else {
            long dateTemp1 = dateDiff / 1000; // 秒
            long dateTemp2 = dateTemp1 / 60; // 分钟
            long dateTemp3 = dateTemp2 / 60; // 小时
            long dateTemp4 = dateTemp3 / 24; // 天数
            long dateTemp5 = dateTemp4 / 30; // 月数
            long dateTemp6 = dateTemp5 / 12; // 年数
            if (dateTemp6 > 0) {
                msg = dateTemp6 + "年前";
            } else if (dateTemp5 > 0) {
                msg = dateTemp5 + "个月前";
            } else if (dateTemp4 > 0) {
                msg = dateTemp4 + "天前 " + hhmm;
            } else if (dateTemp3 > 0) {
//                msg = dateTemp3 + "小时前";
                msg = df.format(setTime);
            } else if (dateTemp2 > 0) {
                msg = dateTemp2 + "分钟前";
//                msg = df.format(setTime);
            } else if (dateTemp1 > 0) {
                msg = "刚刚";
//                msg = df.format(setTime);
            }
        }
        return msg;
    }


    /**
     * 通话-根据时间排序
     * @param list
     */
    public static void thlistSort(List<CallRecord> list) {
        Collections.sort(list, new Comparator<CallRecord>() {
            @Override
            public int compare(CallRecord o1, CallRecord o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getStartTime());
                    Date dt2 = format.parse(o2.getStartTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    /**
     * 短信-根据时间排序
     * @param list
     */
    public static void dxlistSort(List<Message> list) {
        Collections.sort(list, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(o1.getTime());
                    Date dt2 = format.parse(o2.getTime());
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

}
