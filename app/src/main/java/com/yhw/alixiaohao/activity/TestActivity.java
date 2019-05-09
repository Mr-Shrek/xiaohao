package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yhw.alixiaohao.MyApplication;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.entity.CallRecordDao;
import com.yhw.alixiaohao.entity.DaoSession;
import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.entity.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @Override
    public void afterSetView() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @SuppressLint("ResourceType")
    @OnClick({R.id.testBack,R.id.testTxl,R.id.testThjl,R.id.testDx})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.testBack:
                finish();
                break;
            case R.id.testTxl:
                ArrayList<User> list;//通讯录数据
                list = new ArrayList<>();
                list.add(new User("亳州")); // 亳[bó]属于不常见的二级汉字
                list.add(new User("大娃","17777777777" , R.mipmap.lxr_tx_girl3));
                list.add(new User("二娃"));
                list.add(new User("三娃"));
                list.add(new User("四娃","16666666666",R.mipmap.lxr_tx_girl2));
                list.add(new User("五娃"));
                list.add(new User("六娃"));
                list.add(new User("七娃"));
                list.add(new User("喜羊羊"));
                list.add(new User("美羊羊","13333333333" ,R.mipmap.lxr_tx_girl2));
                list.add(new User("懒羊羊"));
                list.add(new User("沸羊羊"));
                list.add(new User("暖羊羊", "12222222222",R.mipmap.lxr_tx_girl1));
                list.add(new User("慢羊羊"));
                list.add(new User("灰太狼"));
                list.add(new User("红太狼"));
                list.add(new User("孙悟空"));
                list.add(new User("黑猫警长"));
                list.add(new User("舒克"));
                list.add(new User("贝塔"));
                list.add(new User("海尔"));
                list.add(new User("阿凡提"));
                list.add(new User("邋遢大王"));
                list.add(new User("哪吒"));
                list.add(new User("没头脑"));
                list.add(new User("不高兴"));
                list.add(new User("蓝皮鼠"));
                list.add(new User("大脸猫"));
                list.add(new User("大头儿子"));
                list.add(new User("小头爸爸"));
                list.add(new User("蓝猫"));
                list.add(new User("淘气", "12211111111",R.mipmap.lxr_tx_girl3));
                list.add(new User("叶峰"));
                list.add(new User("楚天歌"));
                list.add(new User("江流儿"));
                list.add(new User("Tom"));
                list.add(new User("Jerry"));
                list.add(new User("12345"));
                list.add(new User("54321"));
                list.add(new User("_(:з」∠)_"));
                list.add(new User("……%￥#￥%#"));
                ((MyApplication) getApplication()).getDaoSession().getUserDao().insertOrReplaceInTx(list);
                break;
            case R.id.testThjl:
                List<CallRecord> list1;//通话记录集合
                list1 = new ArrayList<>();
                list1.add(new CallRecord("17777777777","2019-01-23 12:33:22",1,1));
                list1.add(new CallRecord("16666666666","2019-04-19 12:33:22",0,1));
                list1.add(new CallRecord("13333333333","2019-02-22 02:22:12",1,0));
                list1.add(new CallRecord("12222222222","2019-04-20 00:10:12",2,0));
                list1.add(new CallRecord("12211111111","2019-04-20 14:22:15",0,0));
                list1.add(new CallRecord("17601018956","2019-04-20 14:58:15",0,0));
                ((MyApplication) getApplication()).getDaoSession().getCallRecordDao().insertOrReplaceInTx(list1);
                break;
            case R.id.testDx:
                List<Message> msgs;
                msgs = new ArrayList<>();
                Message msg = new Message();
                msg.setTxId(R.mipmap.lxr_tx_man1);
                msg.setName("大卫1");
                msg.setPhoneNumber("17601018956");
                msg.setTime("2019-05-02 12:22:21");
                msg.setType(0);
                msg.setContent("这是第一条测试sms我的天哪，这居然是一条短信，真实不敢相信，你看到了吗，儿子！");
                msg.setMsgType(Message.TYPE_RECEIVED);
                Message msg1 = new Message();
                msg1.setTxId(R.mipmap.lxr_tx_man1);
                msg1.setName("大卫1");
                msg1.setPhoneNumber("17601018956");
                msg1.setTime("2019-04-02 12:22:21");
                msg1.setType(1);
                msg1.setContent("这是第二条测试sms,昆仑山大家发了安康教我ask点击访问埃里克的风景爱的ask的为@#@￥*@&（");
                msg1.setMsgType(Message.TYPE_SENT);
                msgs.add(msg1);
                msgs.add(msg);
                ((MyApplication) getApplication()).getDaoSession().getMessageDao().insertOrReplaceInTx(msgs);
                break;
        }
    }

    @Override
    public void destroy() {

    }
}
