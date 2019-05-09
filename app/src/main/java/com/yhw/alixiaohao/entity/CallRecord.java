package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 通话记录实体
 */
@Entity
public class CallRecord implements Serializable {

    static final long serialVersionUID = -15515456L;

    @Id(autoincrement = true)
    private Long id;

    private String calling;//真实主叫号码
    private String called;//被叫号码
    private String calledName;//被叫人姓名
    private String inventedNum;//虚拟号码
    private String startTime;//呼叫开始时间
    private String callDuration;//呼叫时长
    private int callStatus;//通话类型 0：已接听 1：未接听 2：已呼叫
    private int callType;//通话类型 0：主动呼叫 1：被动接听

    public CallRecord(String called,String startTime,int callStatus,int callType){
        this.called = called;
        this.startTime = startTime;
        this.callStatus = callStatus;
        this.callType = callType;
    }

    @Generated(hash = 1351699447)
    public CallRecord(Long id, String calling, String called, String calledName,
            String inventedNum, String startTime, String callDuration,
            int callStatus, int callType) {
        this.id = id;
        this.calling = calling;
        this.called = called;
        this.calledName = calledName;
        this.inventedNum = inventedNum;
        this.startTime = startTime;
        this.callDuration = callDuration;
        this.callStatus = callStatus;
        this.callType = callType;
    }
    @Generated(hash = 1744672525)
    public CallRecord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCalling() {
        return this.calling;
    }
    public void setCalling(String calling) {
        this.calling = calling;
    }
    public String getCalled() {
        return this.called;
    }
    public void setCalled(String called) {
        this.called = called;
    }
    public String getCalledName() {
        return this.calledName;
    }
    public void setCalledName(String calledName) {
        this.calledName = calledName;
    }
    public String getInventedNum() {
        return this.inventedNum;
    }
    public void setInventedNum(String inventedNum) {
        this.inventedNum = inventedNum;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getCallDuration() {
        return this.callDuration;
    }
    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
    public int getCallStatus() {
        return this.callStatus;
    }
    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }
    public int getCallType() {
        return this.callType;
    }
    public void setCallType(int callType) {
        this.callType = callType;
    }

}
