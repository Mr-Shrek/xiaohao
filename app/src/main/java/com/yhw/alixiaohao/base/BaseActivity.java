package com.yhw.alixiaohao.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yhw.alixiaohao.MyApplication;
import com.yhw.alixiaohao.common.CodeNum;
import com.yhw.alixiaohao.entity.DaoSession;
import com.yhw.alixiaohao.widget.AppManager;


public abstract class BaseActivity extends AppCompatActivity {

    public DaoSession daoSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        afterSetView();
        setContentView(setLayout());
        daoSession = ((MyApplication) getApplication()).getDaoSession();
        AppManager.getAppManager().addActivity(this);
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();
    }

    public abstract void afterSetView();
    public abstract int setLayout();
    public abstract void initData(@Nullable Bundle savedInstanceState);
    public abstract void destroy();


    /**
     * 校验http请求返回状态
     * @param json
     * @return
     */
    protected boolean checkResponse(JSONObject json){
        return json.getString("code").equals(CodeNum.S00000);
    }

    protected boolean showErrorTip(String code){
        switch (code){
            case CodeNum.E00001:
                Toast.makeText(this, "参数异常", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    //----------------------------------------------隐藏软键盘

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
