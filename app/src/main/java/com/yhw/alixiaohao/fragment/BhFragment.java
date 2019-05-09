package com.yhw.alixiaohao.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.activity.InitSIMActivity;
import com.yhw.alixiaohao.activity.LoginActivity;
import com.yhw.alixiaohao.activity.MainActivity;
import com.yhw.alixiaohao.adapter.BhUserAdapter;
import com.yhw.alixiaohao.common.SIMConfig;
import com.yhw.alixiaohao.entity.SIMStatus;
import com.yhw.alixiaohao.entity.SIMStatusDao;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.other.ParamEvent;
import com.yhw.alixiaohao.other.bh.BhEvent;
import com.yhw.alixiaohao.utils.Utils;
import com.yhw.alixiaohao.utils.simUtils.SIMInfoUtil;
import com.yhw.alixiaohao.view.HorizontalListView;
import com.yhw.alixiaohao.widget.AfterTextWatcher;
import com.yhw.alixiaohao.widget.CommomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 拨号
 */
public class BhFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private TextView phoneTv;
    private RelativeLayout btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnXing,btn0,btnJing;
    private LinearLayout bh_phoneLL;
    private Button bhBtn1,bhBtn2;
    private HorizontalListView lv;
    private List<User> list;
    private BhUserAdapter adapter;

    private Context mCtx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_bh,null);
        }
        mCtx = getContext();
        initUI();
        initData();
        return mView;
    }

    private void initUI(){
        lv = mView.findViewById(R.id.bh_lv);
        phoneTv = mView.findViewById(R.id.bh_phoneNum);
        bh_phoneLL = mView.findViewById(R.id.bh_phoneLL);
        bhBtn1 = mView.findViewById(R.id.bh_bhBtn1);
        bhBtn2 = mView.findViewById(R.id.bh_bhBtn2);

        initKey();
        bhBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = "17601018956";
                String sms_content = "呵呵";
                if(phone_number.equals("")) {
                    Toast.makeText(mCtx, "没有手机号", Toast.LENGTH_LONG).show();
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    if(sms_content.length() > 70) {
                        List<String> contents = smsManager.divideMessage(sms_content);
                        for(String sms : contents) {
                            smsManager.sendTextMessage(phone_number, null, sms, null, null);
                        }
                    } else {
                        smsManager.sendTextMessage(phone_number, null, sms_content, null, null);
                    }
                    Toast.makeText(mCtx, "发送完成", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bhBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx, "拨号2", Toast.LENGTH_SHORT).show();
//                callPhone("17601018956");
                Utils.call(mCtx,0,"17601018956");
//                getNumber();
//                PhoneUtils.getSimMultiInfo(mCtx);
            }
        });
    }


    /**
     * 指定sim卡拨打电话
     *
     * @param phoneNumber
     * @param slotId      0:卡1  1:卡2
     */
    /*@RequiresApi(api = Build.VERSION_CODES.M)
    public void callPhone(String phoneNumber, int slotId) {
        PhoneAccountHandle phoneAccountHandle = getPhoneAccountHandle(slotId);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        intent.putExtra(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE, phoneAccountHandle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/

    //指定SIM卡拨打
    public static final String[] dualSimTypes = { "subscription", "Subscription",
            "com.android.phone.extra.slot",
            "phone", "com.android.phone.DialingMode",
            "simId", "simnum", "phone_type",
            "simSlot" };
    private void call(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:" + phone));
        for (int i=0; i < dualSimTypes.length; i++) {
            callIntent.putExtra(dualSimTypes[i], 2);
        }
        this.startActivity(callIntent);
    }

    private void initData(){
        list = new ArrayList<>();
        adapter = new BhUserAdapter(mCtx,list);
        lv.setAdapter(adapter);
        phoneTv.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    bh_phoneLL.setVisibility(View.VISIBLE);
                    list.clear();
                    String content = s.toString();
                    List<User> users = ManagerFactory.getInstance().getUserManager().query("where phone_number like ?",content+"%");
                    for (User u : users){
                        list.add(u);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    bh_phoneLL.setVisibility(View.INVISIBLE);
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TextUtils.isEmpty(list.get(position).getPhoneNumber())) {
                    phoneTv.setText(list.get(position).getPhoneNumber());
                }
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initKey(){
        btn1 = mView.findViewById(R.id.bhBtn1);
        btn2 = mView.findViewById(R.id.bhBtn2);
        btn3 = mView.findViewById(R.id.bhBtn3);
        btn4 = mView.findViewById(R.id.bhBtn4);
        btn5 = mView.findViewById(R.id.bhBtn5);
        btn6 = mView.findViewById(R.id.bhBtn6);
        btn7 = mView.findViewById(R.id.bhBtn7);
        btn8 = mView.findViewById(R.id.bhBtn8);
        btn9 = mView.findViewById(R.id.bhBtn9);
        btnXing = mView.findViewById(R.id.bhBtnXing);
        btn0 = mView.findViewById(R.id.bhBtn0);
        btnJing = mView.findViewById(R.id.bhBtnJing);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnXing.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btnJing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bhBtn1:
                phoneTv.append("1");
                break;
            case R.id.bhBtn2:
                phoneTv.append("2");
                break;
            case R.id.bhBtn3:
                phoneTv.append("3");
                break;
            case R.id.bhBtn4:
                phoneTv.append("4");
                break;
            case R.id.bhBtn5:
                phoneTv.append("5");
                break;
            case R.id.bhBtn6:
                phoneTv.append("6");
                break;
            case R.id.bhBtn7:
                phoneTv.append("7");
                break;
            case R.id.bhBtn8:
                phoneTv.append("8");
                break;
            case R.id.bhBtn9:
                phoneTv.append("9");
                break;
            case R.id.bhBtnXing:
                phoneTv.append("*");
                break;
            case R.id.bhBtn0:
                phoneTv.append("0");
                break;
            case R.id.bhBtnJing:
                phoneTv.append("#");
                break;
        }
    }


    private void beforeCall(){
        if (TextUtils.isEmpty(SIMConfig.sim1) && TextUtils.isEmpty(SIMConfig.sim2)){
            showTips("请完善SIM手机号码");
        } else  if (!SIMConfig.sim1.equals(SIMConfig.tel) && !SIMConfig.sim2.equals(SIMConfig.tel)){
            showTips("SIM卡号码与绑定的手机号码不匹配");
        } else{

        }
    }


    private void showTips(String tips){
        new CommomDialog(mCtx, R.style.dialog, tips, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog,boolean confirm) {
                if(confirm){
                    QueryBuilder<SIMStatus> qb = ManagerFactory.getInstance().getStatusManager().queryBuilder();
                    QueryBuilder<SIMStatus> userQb = qb.where(SIMStatusDao.Properties.Account.eq(SIMConfig.tel));
                    SIMStatus simStatus = userQb.unique();
                    Intent in = new Intent(mCtx, InitSIMActivity.class);
                    in.putExtra("tel", SIMConfig.tel);
                    Bundle b = new Bundle();
                    b.putSerializable("simInfo", simStatus);
                    in.putExtras(b);
                    startActivity(in);
                }else{
                    Utils.call(mCtx, 0, phoneTv.getText().toString().trim());
                }
                dialog.dismiss();
            }
        }) .setTitle("提示").show();
    }

    /**
     * 外部调用的方法
     */
    public void resetPhoneTv(String phone){
        if (phoneTv != null){
            phoneTv.setText(phone);
        }
    }

}
