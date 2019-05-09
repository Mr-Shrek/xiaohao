package com.yhw.alixiaohao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class SIMStatus implements Serializable {

    static final long serialVersionUID = -15515456L;

    @Id(autoincrement = true)
    private Long id;
    private String Account;
    private String sim1;
    private String sim2;
    @Generated(hash = 828409286)
    public SIMStatus(Long id, String Account, String sim1, String sim2) {
        this.id = id;
        this.Account = Account;
        this.sim1 = sim1;
        this.sim2 = sim2;
    }
    @Generated(hash = 978452086)
    public SIMStatus() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAccount() {
        return this.Account;
    }
    public void setAccount(String Account) {
        this.Account = Account;
    }
    public String getSim1() {
        return this.sim1;
    }
    public void setSim1(String sim1) {
        this.sim1 = sim1;
    }
    public String getSim2() {
        return this.sim2;
    }
    public void setSim2(String sim2) {
        this.sim2 = sim2;
    }


    @Override
    public String toString() {
        return "SIMStatus{" +
                "id=" + id +
                ", Account='" + Account + '\'' +
                ", sim1='" + sim1 + '\'' +
                ", sim2='" + sim2 + '\'' +
                '}';
    }

}
