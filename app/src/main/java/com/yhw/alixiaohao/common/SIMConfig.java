package com.yhw.alixiaohao.common;


/**
 * sim卡配置类
 */
public class SIMConfig {

    public static String tel = "";// 绑定的手机号码

    public static String sim1 = "";// SIM卡1

    public static String sim2 = "";// SIM卡2

    public static int currSim = -1;// 当前使用的sim卡 0：卡1   1：卡2

    public static int simCount = -1;// sim数量

    public static int cardSlotCount = -1;// 卡槽数量


    /**
     * 初始化设置
     */
    public static void reset(){
        tel = "";
        sim1 = "";
        sim2 = "";
        currSim = -1;
        simCount = -1;
        cardSlotCount = -1;
    }

}
