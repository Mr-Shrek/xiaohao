package com.yhw.alixiaohao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.yhw.alixiaohao.R;

import static com.yhw.alixiaohao.MyApplication.getContext;

/**
 * Describe:</br>
 * 获取Sim卡信息
 * 本实例通过TelephonyManager类的对象的getXxx()
 * 方法获取手机Sim卡信息。
 * @author jph
 * Date:2014.07.22
 * */
public class TelephonyStatus extends Activity {
    ListView listShow;
    //创建一个tManager类的实例
    TelephonyManager tManager;
    //声明一个表示Sim卡状态名的数组
    String []statusName=new String[]{};
    //声明一个表示Sim卡状态值得集合
    ArrayList<String>statusValue=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        verifyStoragePermissions(this);//调用请求6.0权限的方法

        listShow=(ListView)findViewById(R.id.listShow);
        //获取系统的tManager对象
        tManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //获取表示各种状态名的数组
        statusName=getResources().getStringArray(R.array.statusName);
        //获取表示sim卡状态的的数组
        String simStatus[]=getResources().getStringArray(R.array.simStatus);
        //获取表示手机类型的数组
        String phoneType[]=getResources().getStringArray(R.array.phoneType);
        //获取设备编号
        statusValue.add(tManager.getDeviceId());
        //获取设备类型
        statusValue.add(phoneType[tManager.getPhoneType()]);
        //获取软件版本号
        statusValue.add(tManager.getDeviceSoftwareVersion()==null?"未知"
                :tManager.getDeviceSoftwareVersion());
        //获取设备当前位置
        statusValue.add(tManager.getCellLocation()==null? "未知"
                :tManager.getCellLocation().toString());
        //获取设备呼叫状态
        switch (tManager.getCallState()) {
            case TelephonyManager.CALL_STATE_IDLE:
                statusValue.add("空暇");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                statusValue.add("正在通话");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                statusValue.add("等待接听");
                break;
            default:
                break;
        }
        //获取电话号码
        statusValue.add(tManager.getLine1Number());
        //获取运营商的国家代码
        statusValue.add(tManager.getNetworkCountryIso());
        //获取运营商的名称
        statusValue.add(tManager.getNetworkOperatorName());
        //获取网络类型
        statusValue.add(getNetworkType(tManager.getNetworkType()));
        //获取SPN
        statusValue.add(tManager.getSimOperatorName().equals("")?"未知"
                :tManager.getSimOperatorName());
        //获取SIM卡的序列号
        statusValue.add(tManager.getSimSerialNumber());
        //获取SIM卡状态
        statusValue.add(simStatus[tManager.getSimState()]);
        List<Map<String, Object>> listItems=new ArrayList<Map<String,Object>>();
        // 遍历statusValues集合。将statusNames、statusValues
        // 的数据封装到List<Map<String , String>>集合中
        for (int i = 0; i < statusName.length; i++) {
            Map<String, Object>listItem=new HashMap<String, Object>();
            listItem.put("name",statusName[i]);
            listItem.put("value",statusValue.get(i));
            listItems.add(listItem);
        }
        SimpleAdapter adapter=new SimpleAdapter(this, listItems, R.layout.line,
                new String[]{"name","value"},new int[]{R.id.txtName,R.id.txtValue});
        //为listShow设置Adapter
        listShow.setAdapter(adapter);
        JudgeSIM();
    }
    //获取手机网络类型
    private String getNetworkType(int networkType) {
        // TODO Auto-generated method stub
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "EHRPD";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO_A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO_B";
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPAP";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "IDEN";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "UNKNOWN";
            default:
                return "UNKNOWN";
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void JudgeSIM() {
        TelephonyManager tm = (TelephonyManager)getContext().getSystemService(TELEPHONY_SERVICE);
        int phoneCount = tm.getPhoneCount(); //获取当前SIM卡槽数量
        //获取当前SIM卡数量
        int activeSubscriptionInfoCount = SubscriptionManager.from(getContext()).getActiveSubscriptionInfoCount();
        Toast.makeText(getContext(), "卡槽数量："+phoneCount, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "当前SIM卡数量："+activeSubscriptionInfoCount, Toast.LENGTH_SHORT).show();
    }

    //权限初始化
    private int READ_PHONE_STATE = 1;
    private String[] CALLS_STATE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
    };
    /*
     * 方法名：verifyStoragePermissions(Activity activity)
     * 功    能：6.0动态权限
     * 参    数：Activity activity
     * 返回值：无
     */
    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, CALLS_STATE, READ_PHONE_STATE);
        }
    }

}