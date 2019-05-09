package com.yhw.alixiaohao.entity;

import android.support.annotation.IdRes;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.utils.PinYinUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * 通讯录-用户信息
 */
@Entity
public class User implements Comparable<User>,Serializable {

    static final long serialVersionUID = -15515456L;

    @Id(autoincrement = true)
    private Long id;
    private int txId;//头像对应的资源文件ID
    private String name; // 姓名
    private String phoneNumber;//手机号
    private String remark;//备注
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母
    private int follow; // 关注 0:未关注 1：已关注

    public User(String name) {
        this.name = name;
        pinyin = PinYinUtils.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public User(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        pinyin = PinYinUtils.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public User(String name, String phoneNumber, @IdRes int txId) {
        this.name = name;
        this.txId = txId;
        this.phoneNumber = phoneNumber;
        pinyin = PinYinUtils.getPinYin(name); // 根据姓名获取拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    @Generated(hash = 2086132548)
    public User(Long id, int txId, String name, String phoneNumber, String remark,
            String pinyin, String firstLetter, int follow) {
        this.id = id;
        this.txId = txId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.remark = remark;
        this.pinyin = pinyin;
        this.firstLetter = firstLetter;
        this.follow = follow;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTxId() {
        if (txId == 0){
            return R.mipmap.lxr_tx_man1;
        }
        return txId;
    }

    public void setTxId(@IdRes int txId) {
        this.txId = txId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public int getFollow() {
        return this.follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    /**
     * 两个参数进行比较 -- 排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(User o) {
        if (firstLetter.equals("#") && !o.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && o.getFirstLetter().equals("#")){
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(o.getPinyin());
        }
    }

}
