package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class MyUser implements Serializable {

    static final long serialVersionUID = -15515456L;

    @Id(autoincrement = true)
    private Long id;
    private int txId;//头像对应的资源文件ID
    private String name; // 姓名
    private String phoneNumber;//手机号
    private Long telId1; // 绑定的小号1
    private Long telId2; // 绑定的小号2
    private String birthday; // 生日
    private String company; // 公司
    private String job; // 职位
    private String remark;//备注
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母

    @Generated(hash = 172392225)
    public MyUser(Long id, int txId, String name, String phoneNumber, Long telId1,
            Long telId2, String birthday, String company, String job, String remark,
            String pinyin, String firstLetter) {
        this.id = id;
        this.txId = txId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.telId1 = telId1;
        this.telId2 = telId2;
        this.birthday = birthday;
        this.company = company;
        this.job = job;
        this.remark = remark;
        this.pinyin = pinyin;
        this.firstLetter = firstLetter;
    }
    @Generated(hash = 623865568)
    public MyUser() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getTxId() {
        return this.txId;
    }
    public void setTxId(int txId) {
        this.txId = txId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getTelId1() {
        return this.telId1;
    }
    public void setTelId1(Long telId1) {
        this.telId1 = telId1;
    }
    public Long getTelId2() {
        return this.telId2;
    }
    public void setTelId2(Long telId2) {
        this.telId2 = telId2;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getFirstLetter() {
        return this.firstLetter;
    }
    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getJob() {
        return this.job;
    }
    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", txId=" + txId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", telId1=" + telId1 +
                ", telId2=" + telId2 +
                ", birthday='" + birthday + '\'' +
                ", company='" + company + '\'' +
                ", job='" + job + '\'' +
                ", remark='" + remark + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }
}
