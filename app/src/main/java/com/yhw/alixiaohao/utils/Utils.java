package com.yhw.alixiaohao.utils;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yhw.alixiaohao.activity.DxInfoActivity;
import com.yhw.alixiaohao.receive.ReceiverListner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Utils {

    /**
     * 去除重复
     * @param list
     * @return
     */
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    /**
       用指定sim卡拨号
        添加了一个携带值
        phoneAccountHandleList.get(id)
        id为0即为卡1 ，1即为卡二  希望对你有帮助
        所有的选卡外呼都是在TelephonyManager和TelecomManager这两个类里面找
        这是Android 原生的，从5.1版本开始原生就支持双卡拨电话了自己多看看api
    * */
    public static void call(Context context, int id, String telNum){
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

        if(telecomManager != null){
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + telNum));
            intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandleList.get(id));
            context.startActivity(intent);
        }
    }


    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex2 = "^\\d{11}$";
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex2);
    }


    /**
     * 关闭软件盘
     */
    public static void closeInoutDecorView(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
