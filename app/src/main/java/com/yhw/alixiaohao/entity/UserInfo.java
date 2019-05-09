package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 我的 - 用户信息表
 */
@Entity
public class UserInfo {

    @Id(autoincrement = true)
    private Long id;

    private String checkCellnumber; // 用户验证后和小号绑定的手机号

    private String bindStatus; // 账号绑定套餐状态 on/off

    private String status; // 账号使用状态

    private String uprice; // 用户余额

    private String surplusMinute; // 用户剩余分钟数

    private int balanceNum; // 用户剩余抽奖次数

    @Generated(hash = 534753271)
    public UserInfo(Long id, String checkCellnumber, String bindStatus,
            String status, String uprice, String surplusMinute, int balanceNum) {
        this.id = id;
        this.checkCellnumber = checkCellnumber;
        this.bindStatus = bindStatus;
        this.status = status;
        this.uprice = uprice;
        this.surplusMinute = surplusMinute;
        this.balanceNum = balanceNum;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckCellnumber() {
        return this.checkCellnumber;
    }

    public void setCheckCellnumber(String checkCellnumber) {
        this.checkCellnumber = checkCellnumber;
    }

    public String getBindStatus() {
        return this.bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUprice() {
        return this.uprice;
    }

    public void setUprice(String uprice) {
        this.uprice = uprice;
    }

    public String getSurplusMinute() {
        return this.surplusMinute;
    }

    public void setSurplusMinute(String surplusMinute) {
        this.surplusMinute = surplusMinute;
    }

    public int getBalanceNum() {
        return this.balanceNum;
    }

    public void setBalanceNum(int balanceNum) {
        this.balanceNum = balanceNum;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", checkCellnumber='" + checkCellnumber + '\'' +
                ", bindStatus='" + bindStatus + '\'' +
                ", status='" + status + '\'' +
                ", uprice='" + uprice + '\'' +
                ", surplusMinute='" + surplusMinute + '\'' +
                ", balanceNum=" + balanceNum +
                '}';
    }
}
