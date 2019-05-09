package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Message implements Serializable {

    public static final int TYPE_RECEIVED = 0;// 接收消息
    public static final int TYPE_SENT = 1;// 发送消息

    static final long serialVersionUID = -15515456L;

    @Id(autoincrement = true)
    private Long id;
    private int txId;//头像对应的资源文件ID
    private String name; // 姓名
    private String phoneNumber;//手机号
    private String content;//短信内容
    private String time;//时间
    private int type;//状态 0: 正在发送 / 1 已发送 / 2 发送失败
    private int msgType; // 发送和接受

    @Generated(hash = 422880917)
    public Message(Long id, int txId, String name, String phoneNumber,
            String content, String time, int type, int msgType) {
        this.id = id;
        this.txId = txId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.time = time;
        this.type = type;
        this.msgType = msgType;
    }
    @Generated(hash = 637306882)
    public Message() {
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getMsgType() {
        return this.msgType;
    }
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

}
