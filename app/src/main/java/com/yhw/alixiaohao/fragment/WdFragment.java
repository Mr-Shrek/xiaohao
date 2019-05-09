package com.yhw.alixiaohao.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.bean.GetUBean;
import com.yhw.alixiaohao.common.AppConfig;
import com.yhw.alixiaohao.common.CodeNum;
import com.yhw.alixiaohao.common.CommonInfo;
import com.yhw.alixiaohao.common.SIMConfig;
import com.yhw.alixiaohao.common.UserKey;
import com.yhw.alixiaohao.entity.BindTel;
import com.yhw.alixiaohao.entity.BindTelDao;
import com.yhw.alixiaohao.entity.MyUser;
import com.yhw.alixiaohao.entity.SmallNumber;
import com.yhw.alixiaohao.entity.SmallNumberDao;
import com.yhw.alixiaohao.entity.SpecTC;
import com.yhw.alixiaohao.entity.UserInfo;
import com.yhw.alixiaohao.entity.UserInfoDao;
import com.yhw.alixiaohao.model.HttpCommImpl;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.model.http.CallBackInteface;
import com.yhw.alixiaohao.utils.LogMg;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.view.PopupMenu;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 我的
 */
public class WdFragment extends Fragment implements CallBackInteface,PopupMenu.OnItemSelectedListener {

    private Logcat log = new Logcat(WdFragment.class);

    private View mView;
    private HttpCommImpl httpComm;
    //--------页面右上角
    private ImageView topPop;//页面右上角“+”号
    private PopupMenu popupMenu;//“+”号弹出Menu
    private String[] popData = {"咨询客服","常见问题","帮助中心"};//“+”弹出的信息
    //--------
    private CircleImageView cIvImg; // 头像
    private TextView tvPhone; // 我的号码
    private TextView tvXh1; // 小号1
    private TextView tvXh2; // 小号2
    private ImageView ivXh1; // 小号1Button
    private ImageView ivXh2; // 小号2Button
    private ImageView ivXh1Icon; // 小号1标记
    private ImageView ivXh2Icon; // 小号2标记


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_wd,null);
        }
        initView();
        initData();
        return mView;
    }

    /**
     * 初始化布局
     */
    private void initView(){
        topPop = mView.findViewById(R.id.wd_topPop);
        popupMenu = new PopupMenu(getContext());
        popupMenu.setData(popData, mView.findViewById(R.id.wd_topPop),120);
        popupMenu.setOnItemSelectedListener(this);

        //-----view
        cIvImg = mView.findViewById(R.id.wd_Img);
        tvPhone = mView.findViewById(R.id.wd_phone);
        tvXh1 = mView.findViewById(R.id.wd_xh1);
        tvXh2 = mView.findViewById(R.id.wd_xh2);
        ivXh1 = mView.findViewById(R.id.wd_xhImg1);
        ivXh2 = mView.findViewById(R.id.wd_xhImg2);
        ivXh1Icon = mView.findViewById(R.id.wd_xh1Icon);
        ivXh2Icon = mView.findViewById(R.id.wd_xh2Icon);

        cIvImg.setImageResource(R.mipmap.lxr_tx_man1);
        tvPhone.setText(SIMConfig.tel);

        topPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(){
        httpComm = new HttpCommImpl();
        httpComm.setCallBackInteface(this);
        httpComm.getu(UserKey.key);
    }

    /**
     * 页面右上角“+”号
     * Menu选择监听
     * @param position -
     */
    @Override
    public void onItemSelected(int position) {
        switch (position){
            case 0:
                log.i("点击了:"+popData[0]);
                break;
            case 1:
                log.i("点击了:"+popData[1]);
                break;
            case 2:
                log.i("点击了:"+popData[1]);
                break;
        }
    }

    /**
     * 网络请求回调函数
     */
    @Override
    public void onResponse(Object data) {
        JSONObject json = (JSONObject) data;
        String api = json.getString("api");//请求id
        switch (api){
            case AppConfig.GET_U:
                GetUBean bean = JSON.parseObject(json.toJSONString(), new TypeReference<GetUBean>(){});
                if (bean == null){
                    Toast.makeText(getContext(), "返回数据出现异常", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (bean.getCode()){
                    case CodeNum.S00000:
                        GetUBean.ResultBean resultBean = bean.getResult();
                        // 检测如果多条数据异常，循环删除数据，仅保留一条
                        List<UserInfo> userInfos = ManagerFactory.getInstance().getUserInfoManager().
                                query("where check_cellnumber = ?", resultBean.getCheckCellnumber());
                        if (userInfos !=null && userInfos.size() > 1){
                            LogMg.e("","********************************出先异常现象：相同手机号多条数据*****************************");
                            LogMg.e("","*******************数据：");

                            for (UserInfo u : userInfos){
                                LogMg.e("",u.toString());
                            }
                            for (int i = 0; i < userInfos.size(); i++){
                                if (i == 0){
                                    continue;
                                }
                                ManagerFactory.getInstance().getUserInfoManager().delete(userInfos.get(i));
                            }
                        }
                        QueryBuilder<UserInfo> qb = ManagerFactory.getInstance().getUserInfoManager().queryBuilder();
                        QueryBuilder<UserInfo> userQb = qb.where(UserInfoDao.Properties.CheckCellnumber.eq(resultBean.getCheckCellnumber()));
                        UserInfo userInfo;
                        if (userQb != null && userQb.unique() != null){
                            userInfo = userQb.unique();
                        } else {
                            userInfo = new UserInfo();
                        }
                        userInfo.setId((long) resultBean.getId());
                        userInfo.setBalanceNum(resultBean.getBalanceNum());
                        userInfo.setBindStatus(resultBean.getBindStatus());
                        userInfo.setCheckCellnumber(resultBean.getCheckCellnumber());
                        userInfo.setStatus(resultBean.getStatus());
                        userInfo.setSurplusMinute(resultBean.getSurplusMinute());
                        userInfo.setUprice(resultBean.getUprice());
                        ManagerFactory.getInstance().getUserInfoManager().saveOrUpdate(userInfo);
                        CommonInfo.userInfo = userInfo;
                        for (GetUBean.ResultBean.BindTelsBean telsBean : resultBean.getBindTels()){
                            QueryBuilder<BindTel> tQb = ManagerFactory.getInstance().getBindTelManager().queryBuilder();
                            QueryBuilder<BindTel> telQb = tQb.where(BindTelDao.Properties.BindCellnumber.eq(telsBean.getBindCellnumber()));
                            //查询套餐信息在表中是否存在,不存在则添加，存在则更新
                            SpecTC specTC = ManagerFactory.getInstance().getSpecTcManager().query(Long.valueOf(telsBean.getSpecid()));
                            if (specTC == null){
                                specTC = new SpecTC();
                            }
                            specTC.setId((long) telsBean.getSpecTC().getId());
                            specTC.setFprice(String.valueOf(telsBean.getSpecTC().getFprice()));
                            specTC.setSdesc(telsBean.getSpecTC().getSdesc());
                            specTC.setSname(telsBean.getSpecTC().getSname());
                            specTC.setSprice(String.valueOf(telsBean.getSpecTC().getSprice()));
                            specTC.setStatus(telsBean.getSpecTC().getStatus());
                            specTC.setStime(telsBean.getSpecTC().getStime());
                            ManagerFactory.getInstance().getSpecTcManager().saveOrUpdate(specTC);
                            BindTel bindTel;
                            if (telQb != null && telQb.unique() != null){
                                bindTel = telQb.unique();
                            } else {
                                bindTel = new BindTel();
                            }
                            bindTel.setBindCellnumber(telsBean.getBindCellnumber());
                            bindTel.setBindStartTime(telsBean.getBindStartTime());
                            bindTel.setBindEndTime(telsBean.getBindEndTime());
                            bindTel.setBindStatus(telsBean.getBindStatus());
                            bindTel.setUId((long) telsBean.getUid());
                            bindTel.setSpecId((long) telsBean.getSpecid());
                            bindTel.setUsedTime(telsBean.getUsedTime());
                            ManagerFactory.getInstance().getBindTelManager().saveOrUpdate(bindTel);
                            // 根据小号号码查询表中的小号数据，插入或更新
                            QueryBuilder<SmallNumber> sQb = ManagerFactory.getInstance().getSmallNumberManager().queryBuilder();
                            QueryBuilder<SmallNumber> smallQb = sQb.where(SmallNumberDao.Properties.Number.eq(bindTel.getBindCellnumber()));
                            SmallNumber smallNumber;
                            if (smallQb != null && smallQb.unique() != null){
                                smallNumber = smallQb.unique();
                            } else {
                                smallNumber = new SmallNumber();
                            }
                            smallNumber.setNumber(telsBean.getSmallNumber().getNumber());
                            smallNumber.setArea(telsBean.getSmallNumber().getArea());
                            smallNumber.setId((long) telsBean.getSmallNumber().getId());
                            ManagerFactory.getInstance().getSmallNumberManager().saveOrUpdate(smallNumber);
                        }
                        // --------------- 刷新 UI
                        // 查询表中状态为可用的小号
                        List<BindTel> bindTels = ManagerFactory.getInstance().getBindTelManager().
                                query("where u_id = ?", String.valueOf(CommonInfo.userInfo.getId()));
                        if (bindTels == null || bindTels.size() == 0){
                            Toast.makeText(getContext(), "未绑定小号", Toast.LENGTH_SHORT).show();
                            noAvailableData();
                            return;
                        }
                        MyUser myUser = ManagerFactory.getInstance().getMyUserManager().query(CommonInfo.myUser.getId());
                        // 设置小号1
                        tvXh1.setText(bindTels.get(0).getBindCellnumber());
                        ivXh1.setImageResource(bindTels.get(0).getBindStatus().equals("on") ? R.mipmap.btn_jc : R.mipmap.btn_hq);
                        ivXh1Icon.setImageResource(bindTels.get(0).getBindStatus().equals("on") ? R.mipmap.k_syz : R.mipmap.k_mr);
                        myUser.setTelId1(bindTels.get(0).getBindStatus().equals("on") ? bindTels.get(0).getId() : 0);
                        if (bindTels.size() >= 2){
                            // 设置小号2
                            tvXh2.setText(bindTels.get(1).getBindCellnumber());
                            ivXh2.setImageResource(bindTels.get(1).getBindStatus().equals("on") ? R.mipmap.btn_jc : R.mipmap.btn_hq);
                            ivXh2Icon.setImageResource(bindTels.get(1).getBindStatus().equals("on") ? R.mipmap.k_syz : R.mipmap.k_mr);
                            myUser.setTelId2(bindTels.get(1).getBindStatus().equals("on") ? bindTels.get(1).getId() : 0);
                        } else {
                            tvXh2.setText(R.string.text_wd_show_no_number);
                            ivXh2.setImageResource(R.mipmap.btn_hq);
                            ivXh2Icon.setImageResource(R.mipmap.k_mr);
                        }
                        ManagerFactory.getInstance().getMyUserManager().saveOrUpdate(myUser);
                        CommonInfo.myUser = myUser;
                        break;
                    case CodeNum.E00001:
                        Toast.makeText(getContext(), "参数异常", Toast.LENGTH_SHORT).show();
                        noAvailableData();
                        break;
                    case CodeNum.E00201:
                        Toast.makeText(getContext(), "获取用户信息失败", Toast.LENGTH_SHORT).show();
                        noAvailableData();
                        break;
                }
                break;
        }
    }
    /**
     * 网络请求异常回调
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * 无可用数据 ui 设置
     */
    private void noAvailableData(){
        tvXh1.setText(R.string.text_wd_show_no_number);
        tvXh2.setText(R.string.text_wd_show_no_number);
        ivXh1.setImageResource(R.mipmap.btn_hq);
        ivXh2.setImageResource(R.mipmap.btn_hq);
        ivXh1Icon.setImageResource(R.mipmap.k_mr);
        ivXh2Icon.setImageResource(R.mipmap.k_mr);
    }

}
