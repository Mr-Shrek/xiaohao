package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 被绑定的小号表
 */
@Entity
public class BindTel {

    @Id(autoincrement = true)
    private Long id;

    private Long specId; // 关联套餐表id

    private Long uId; // 关联的用户表id

    private String bindCellnumber; // 绑定的小号

    private String bindStartTime; // 绑定起始日期

    private String bindEndTime; //绑定结束日期

    private String usedTime; // 使用分钟数

    private String bindStatus; // 小号绑定状态

    @Generated(hash = 338950678)
    public BindTel(Long id, Long specId, Long uId, String bindCellnumber,
            String bindStartTime, String bindEndTime, String usedTime,
            String bindStatus) {
        this.id = id;
        this.specId = specId;
        this.uId = uId;
        this.bindCellnumber = bindCellnumber;
        this.bindStartTime = bindStartTime;
        this.bindEndTime = bindEndTime;
        this.usedTime = usedTime;
        this.bindStatus = bindStatus;
    }

    @Generated(hash = 1229488029)
    public BindTel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpecId() {
        return this.specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public Long getUId() {
        return this.uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public String getBindCellnumber() {
        return this.bindCellnumber;
    }

    public void setBindCellnumber(String bindCellnumber) {
        this.bindCellnumber = bindCellnumber;
    }

    public String getBindStartTime() {
        return this.bindStartTime;
    }

    public void setBindStartTime(String bindStartTime) {
        this.bindStartTime = bindStartTime;
    }

    public String getBindEndTime() {
        return this.bindEndTime;
    }

    public void setBindEndTime(String bindEndTime) {
        this.bindEndTime = bindEndTime;
    }

    public String getUsedTime() {
        return this.usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getBindStatus() {
        return this.bindStatus;
    }

    public void setBindStatus(String bindStatus) {
        this.bindStatus = bindStatus;
    }

}
