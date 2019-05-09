package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.adapter.ThInfoAdapter;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.entity.UserDao;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.widget.TitleManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 通话记录-二级详细页面
 */
public class ThInfoActivity extends BaseActivity implements TitleManager.OnTitleClickListener {

    private final Logcat log = new Logcat(ThInfoActivity.class);

    @BindView(R.id.thInfoPhone)
    RelativeLayout phoneBtn;
    @BindView(R.id.thInfoPhoneTv)
    TextView phoneNum;
    @BindView(R.id.thInfoName)
    TextView tvName;
    @BindView(R.id.thInfoTx)
    CircleImageView imgTx;
    @BindView(R.id.thInfoList)
    ListView lv;

    private ThInfoAdapter adapter;
    private List<CallRecord> list;

    @Override
    public void afterSetView() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_th_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        @SuppressLint("ResourceType") TitleManager titleManager = new TitleManager(this,R.mipmap.back,getString(R.string.back),"","",R.mipmap.gd);
        titleManager.setOnTitleClickListener(this);

        CallRecord record = (CallRecord) getIntent().getSerializableExtra("data");
        if (record == null) finish();

        QueryBuilder<User> qb = ManagerFactory.getInstance().getUserManager().queryBuilder();
        QueryBuilder<User> userQb = qb.where(UserDao.Properties.PhoneNumber.eq(record.getCalled()));
        User u = userQb.unique();
        if (u != null){
            tvName.setText(u.getName());
            imgTx.setImageResource(u.getTxId());
            phoneNum.setText(u.getPhoneNumber());
        }else{
            tvName.setText(record.getCalled());
            imgTx.setImageResource(R.mipmap.ic_launcher);
            phoneNum.setText(record.getCalled());
        }

        list = ManagerFactory.getInstance().getCallRecordManager().query("where called = ?",record.getCalled());
        adapter = new ThInfoAdapter(this,list);
        lv.setAdapter(adapter);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
