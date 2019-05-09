package com.yhw.alixiaohao.widget;

import android.text.TextWatcher;

/**
 * EditText文本框状态拦截
 * Created by yhw on 2016/6/29.
 */
public abstract class AfterTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
