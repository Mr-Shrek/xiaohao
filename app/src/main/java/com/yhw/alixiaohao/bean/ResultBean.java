package com.yhw.alixiaohao.bean;

public class ResultBean {

    /**
     * resCode : 20000
     * resMessage : 请求成功
     * success : true
     */

    private String resCode;
    private String resMessage;
    private boolean success;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
