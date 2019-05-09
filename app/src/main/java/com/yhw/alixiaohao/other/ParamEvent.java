package com.yhw.alixiaohao.other;

/**
 * EventBus 传递的参数
 * @param <T>
 */
public class ParamEvent<T> {

    private int type; // 2：拨号传参
    private T t;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
