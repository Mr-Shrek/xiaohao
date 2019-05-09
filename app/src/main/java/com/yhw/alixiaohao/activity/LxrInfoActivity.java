package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class LxrInfoActivity extends BaseActivity implements TitleManager.OnTitleClickListener {

    private final Logcat log = new Logcat(LxrInfoActivity.class);

    @BindView(R.id.lxrInfoPhone)
    RelativeLayout phoneBtn;
    @BindView(R.id.lxrInfoPhoneTv)
    TextView phoneNum;
    @BindView(R.id.lxrInfoName)
    TextView tvName;
    @BindView(R.id.lxrInfoTx)
    CircleImageView imgTx;

    private List<User> list;

    @Override
    public void afterSetView() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_lxr_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        @SuppressLint("ResourceType") TitleManager titleManager = new TitleManager(this,R.mipmap.back,getString(R.string.back),"","",R.mipmap.gd);
        titleManager.setOnTitleClickListener(this);

        User record = (User) getIntent().getSerializableExtra("data");
        if (record == null) finish();

        tvName.setText(record.getName());
        imgTx.setImageResource(record.getTxId());
        phoneNum.setText(record.getPhoneNumber());

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
