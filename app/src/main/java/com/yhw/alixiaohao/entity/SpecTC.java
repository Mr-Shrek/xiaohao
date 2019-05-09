package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 套餐表
 */
@Entity
public class SpecTC {

    @Id(autoincrement = true)
    private Long id;

    private String sname; // 套餐名称

    private String stime; // 套餐时长

    private String sprice; // 套餐报价

    private String fprice; // 分钟数报价：元/分 | 忽略

    private String sdesc; // 套餐明细

    private String status; // 套餐状态

    @Generated(hash = 873750443)
    public SpecTC(Long id, String sname, String stime, String sprice, String fprice,
            String sdesc, String status) {
        this.id = id;
        this.sname = sname;
        this.stime = stime;
        this.sprice = sprice;
        this.fprice = fprice;
        this.sdesc = sdesc;
        this.status = status;
    }

    @Generated(hash = 572534029)
    public SpecTC() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSname() {
        return this.sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStime() {
        return this.stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSprice() {
        return this.sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getFprice() {
        return this.fprice;
    }

    public void setFprice(String fprice) {
        this.fprice = fprice;
    }

    public String getSdesc() {
        return this.sdesc;
    }

    public void setSdesc(String sdesc) {
        this.sdesc = sdesc;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
