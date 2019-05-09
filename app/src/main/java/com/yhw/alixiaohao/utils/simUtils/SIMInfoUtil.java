package com.yhw.alixiaohao.utils.simUtils;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.yhw.alixiaohao.utils.PhoneUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.yhw.alixiaohao.MyApplication.getContext;

/**
 * SIM卡信息工具类
 */
public class SIMInfoUtil {

    /**
     * 获取卡槽数量
     * @param context
     * @return
     */
    public static int getCardSlotCount(Context context){
        TelephonyManager tm = (TelephonyManager)getContext().getSystemService(TELEPHONY_SERVICE);
        return tm.getPhoneCount();
    }

    /**
     * 获取当前插入的SIM卡数量
     * @param context
     * @return
     */
    public static int getCardCount(Context context){
        return SubscriptionManager.from(getContext()).getActiveSubscriptionInfoCount();
    }


    public static List<Integer> getSlotStatus(){
        List<Integer> slotIndexs = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //1.版本超过5.1，调用系统方法
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) getContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> activeSubscriptionInfoList = null;
            if (mSubscriptionManager != null) {
                try {
                    activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
                } catch (Exception ignored) {
                }
            }
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                //1.1.1 有使用的卡，就遍历所有卡
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    slotIndexs.add(subscriptionInfo.getSimSlotIndex());
                }
            }
        }
        return slotIndexs;
    }


    public static void getSimInfo(){
        List<PhoneUtils.SimInfo> infos = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            //1.版本超过5.1，调用系统方法
            SubscriptionManager mSubscriptionManager = (SubscriptionManager) getContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> activeSubscriptionInfoList = null;
            if (mSubscriptionManager != null) {
                try {
                    activeSubscriptionInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
                } catch (Exception ignored) {
                }
            }
            if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                //1.1.1 有使用的卡，就遍历所有卡
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    PhoneUtils.SimInfo simInfo = new PhoneUtils.SimInfo();
                    simInfo.mCarrierName = subscriptionInfo.getCarrierName();
                    simInfo.mIccId = subscriptionInfo.getIccId();
                    simInfo.mSimSlotIndex = subscriptionInfo.getSimSlotIndex();
                    simInfo.mNumber = subscriptionInfo.getNumber();
                    simInfo.mCountryIso = subscriptionInfo.getCountryIso();
                    infos.add(simInfo);
                }
            }
        }
    }


    /**
     * SIM 卡信息
     */
    public static class SimInfo {
        /** 运营商信息：中国移动 中国联通 中国电信 */
        public CharSequence mCarrierName;
        /** 卡槽ID，SimSerialNumber */
        public CharSequence mIccId;
        /** 卡槽id， -1 - 没插入、 0 - 卡槽1 、1 - 卡槽2 */
        public int mSimSlotIndex;
        /** 号码 */
        public CharSequence mNumber;
        /** 城市 */
        public CharSequence mCountryIso;
        /** 设备唯一识别码 */
        public CharSequence mImei; /*= getIMEI();*/
        /** SIM的编号 */
        public CharSequence mImsi;

        /**
         * 通过 IMEI 判断是否相等
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            return obj != null && obj instanceof SimInfo && (TextUtils.isEmpty(((SimInfo) obj).mImei) || ((SimInfo) obj).mImei.equals(mImei));
        }

        @Override
        public String toString() {
            return "SimInfo{" +
                    "mCarrierName=" + mCarrierName +
                    ", mIccId=" + mIccId +
                    ", mSimSlotIndex=" + mSimSlotIndex +
                    ", mNumber=" + mNumber +
                    ", mCountryIso=" + mCountryIso +
                    ", mImei=" + mImei +
                    ", mImsi=" + mImsi +
                    '}';
        }
    }


}
