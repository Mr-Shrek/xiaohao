package com.yhw.alixiaohao.receive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/***************************************************************************************************************
 * 功能：监听短信状态
 * 说明：自定义类ReceiverListner，覆盖BroadcastReceiver，短信发送成功提醒“发送成功”，否则提醒重新发送
 * 日期：2014.01.20
 * 作者：lwj
 ***************************************************************************************************************/
public class ReceiverListner extends BroadcastReceiver {

    public OnSMSCallBackListener getOnSMSCallBackListener() {
        return onSMSCallBackListener;
    }

    public void setOnSMSCallBackListener(OnSMSCallBackListener onSMSCallBackListener) {
        this.onSMSCallBackListener = onSMSCallBackListener;
    }

    private OnSMSCallBackListener onSMSCallBackListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            // 获取短信状态
            switch (getResultCode()) {
                // 短信发送成功
                case Activity.RESULT_OK:
                    Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();
                    onSMSCallBackListener.onCallBack(true);
                    break;
                // 短信发送不成功
                default:
                    Toast.makeText(context, "发送失败，请重新发送！", Toast.LENGTH_LONG).show();
                    onSMSCallBackListener.onCallBack(false);
                    break;
            }
        }catch (Exception e) {
            Toast.makeText(context, "发送出现异常，请重新发送！", Toast.LENGTH_LONG).show();
            onSMSCallBackListener.onCallBack(false);
        }
    }

    public interface OnSMSCallBackListener{
        void onCallBack(boolean flag);
    }

}

/*******************************************************end*****************************************************/
