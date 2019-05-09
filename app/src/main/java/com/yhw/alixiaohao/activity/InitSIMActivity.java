package com.yhw.alixiaohao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.entity.SIMStatus;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.Utils;
import com.yhw.alixiaohao.utils.simUtils.SIMInfoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 初始化SIM页面
 */
public class InitSIMActivity extends BaseActivity {

    private final Logcat log = new Logcat(InitSIMActivity.class);

    @BindView(R.id.init_sim1)
    LinearLayout sim1;
    @BindView(R.id.init_sim2)
    LinearLayout sim2;
    @BindView(R.id.init_phone1)
    EditText phone1;
    @BindView(R.id.init_phone2)
    EditText phone2;

    private String tel;
    private SIMStatus simStatus;

    @Override
    public void afterSetView() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_init_sim;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        Intent in = getIntent();
        simStatus = (SIMStatus) in.getSerializableExtra("simInfo");
        if (simStatus != null){//判断显示哪个SIM卡输入框
            tel = simStatus.getAccount();
            log.e("tel:"+tel);
            log.e(simStatus.toString());
            if (TextUtils.isEmpty(simStatus.getSim1())){
                sim1.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(simStatus.getSim2())){
                sim2.setVisibility(View.VISIBLE);
            }
            phone1.setText(!TextUtils.isEmpty(simStatus.getSim1()) ? simStatus.getSim1() : "");
            phone2.setText(!TextUtils.isEmpty(simStatus.getSim2()) ? simStatus.getSim2() : "");
        } else {
            log.e("初始化页面未获取到simStatus信息");
            finish();
        }
    }

    @OnClick({R.id.init_sure,R.id.init_break})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.init_sure:
                String p1 = phone1.getText().toString().trim();
                String p2 = phone2.getText().toString().trim();
                log.e("p1:"+p1);
                log.e("p2:"+p2);
                if (!p1.equals(tel) && !p2.equals(tel)){
                    Toast.makeText(this, "至少有一个号码为登陆号码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.isEmpty(p1)){
                    if (!Utils.isMobileNO(p1)){
                        Toast.makeText(this, "手机号1码格式错误", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (!TextUtils.isEmpty(p2)){
                    if (!Utils.isMobileNO(p2)){
                        Toast.makeText(this, "手机号2码格式错误", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                simStatus.setSim1(p1);
                simStatus.setSim2(p2);
                ManagerFactory.getInstance().getStatusManager().saveOrUpdate(simStatus);
                startActivity(new Intent(InitSIMActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.init_break:
                ManagerFactory.getInstance().getStatusManager().saveOrUpdate(simStatus);
                startActivity(new Intent(InitSIMActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void destroy() {

    }
}
