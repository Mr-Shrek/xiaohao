package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 小号详情
 */
@Entity
public class SmallNumber {

    @Id(autoincrement = true)
    private Long id;

    private String number; // 小号号码

    private String area; // 城市

    @Generated(hash = 1565404837)
    public SmallNumber(Long id, String number, String area) {
        this.id = id;
        this.number = number;
        this.area = area;
    }

    @Generated(hash = 1764476731)
    public SmallNumber() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
