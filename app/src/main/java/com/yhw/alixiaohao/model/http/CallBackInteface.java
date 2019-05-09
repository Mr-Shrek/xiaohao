package com.yhw.alixiaohao.model.http;

public interface CallBackInteface {

    void onResponse(Object data);

    void onError(Throwable e);

}
