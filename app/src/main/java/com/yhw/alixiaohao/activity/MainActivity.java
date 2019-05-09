package com.yhw.alixiaohao.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.adapter.MainFragmentAdapter;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.common.CommonInfo;
import com.yhw.alixiaohao.common.SIMConfig;
import com.yhw.alixiaohao.entity.MyUser;
import com.yhw.alixiaohao.entity.MyUserDao;
import com.yhw.alixiaohao.fragment.BhFragment;
import com.yhw.alixiaohao.fragment.DxFragment;
import com.yhw.alixiaohao.fragment.ThFragment;
import com.yhw.alixiaohao.fragment.WdFragment;
import com.yhw.alixiaohao.fragment.LxrFragment;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.other.ParamEvent;
import com.yhw.alixiaohao.other.bh.BhEvent;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.PinYinUtils;
import com.yhw.alixiaohao.view.NoScrollViewPager;
import com.yhw.alixiaohao.widget.SelectPageListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    private Logcat log = new Logcat(MainActivity.class);

    @BindView(R.id.main_rg)
    RadioGroup rg;
    @BindView(R.id.main_rb_dx)
    RadioButton rb_dx;
    @BindView(R.id.main_rb_th)
    RadioButton rb_th;
    @BindView(R.id.main_rb_bh)
    RadioButton rb_bh;
    @BindView(R.id.main_bhBg)
    ImageView rb_bhBg;
    @BindView(R.id.main_rb_lxr)
    RadioButton rb_lxr;
    @BindView(R.id.main_rb_wd)
    RadioButton rb_wd;
    @BindView(R.id.main_vp)
    NoScrollViewPager viewPager;
    //-----------------------主页碎片布局
    List<Fragment> list;
    private MainFragmentAdapter mainFragmentAdapter;
    private FragmentManager fragmentManager;

    DxFragment dx;
    ThFragment th;
    LxrFragment lxr;
    BhFragment bh;
    WdFragment wd;

    /**
     * 绑定布局前执行方法
     */
    @Override
    public void afterSetView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * 绑定布局
     * @return
     */
    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    /**
     * onCreate
     * @param savedInstanceState --
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);//初始化布局绑定
        EventBus.getDefault().register(this); // 注册全局广播
        initMySelf();
        initFragmentList();
        initView();
    }

    /**
     * 初始化个人信息
     */
    public void initMySelf(){
        QueryBuilder<MyUser> qb = ManagerFactory.getInstance().getMyUserManager().queryBuilder();
        QueryBuilder<MyUser> myQb = qb.where(MyUserDao.Properties.PhoneNumber.eq(SIMConfig.tel));
        MyUser myUser;
        if (myQb != null && myQb.unique() != null){
            myUser = myQb.unique();
        } else {
            myUser = new MyUser();
        }
        myUser.setPhoneNumber(SIMConfig.tel);
        myUser.setTxId(R.mipmap.lxr_tx_man1);
        ManagerFactory.getInstance().getMyUserManager().saveOrUpdate(myUser);
        log.e("myUser:"+myUser.toString());
        CommonInfo.myUser = myUser;
    }

    /**
     * 初始化主页碎片
     */
    private void initFragmentList(){
        list = new ArrayList<>();
        dx = new DxFragment();
        th = new ThFragment();
        bh = new BhFragment();
        lxr = new LxrFragment();
        wd = new WdFragment();
        list.add(dx);
        list.add(th);
        list.add(bh);
        list.add(lxr);
        list.add(wd);
    }

    /**
     * 初始化布局
     */
    private void initView(){
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(5);
        mainFragmentAdapter = new MainFragmentAdapter(fragmentManager,list);//初始化碎片适配器
        viewPager.setAdapter(mainFragmentAdapter);//配置适配器
        //页面跳转监听
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setRbTextColor(checkedId);//改变文字颜色
                switch (checkedId){
                    case R.id.main_rb_dx:
                        dx.updateUI();
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_rb_th:
                        th.updateUI();
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.main_rb_bh:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.main_rb_lxr:
                        lxr.updateUI();
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.main_rb_wd:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        //页面滑动切换监听
        viewPager.addOnPageChangeListener(new SelectPageListener() {
            @Override
            public void onPageSelected(int i) {
                setRbCheck(i);
            }
        });
        viewPager.setCurrentItem(4);//设置默认显示“我的”页面
    }

    /**
     * 底部RadioButton按钮根据页面滑动改变状态
     */
    private void setRbCheck(int position){
        ((RadioButton) rg.getChildAt(position)).setChecked(true);
    }

    /**
     * 设置底部控件文字颜色
     * @param id 当前选中的控件ID
     */
    private void setRbTextColor(@IdRes int id){
        if (id == rb_bh.getId()){
            rb_bhBg.setImageResource(R.mipmap.bh_hover);
        }else{
            rb_bhBg.setImageResource(R.mipmap.bh_default);
        }
        for (int i = 0; i<rg.getChildCount(); i++){
            if (rg.getChildAt(i) instanceof RadioButton){
                if (id == rg.getChildAt(i).getId()){
                    ((RadioButton) rg.getChildAt(i)).setTextColor(getResources().getColor(R.color.text_blue));
                }else{
                    ((RadioButton) rg.getChildAt(i)).setTextColor(getResources().getColor(R.color.text_huise));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lxr != null) {
            lxr.updateUI();
        }
        if (dx != null){
            dx.updateUI();
        }
        if (th != null){
            th.updateUI();
        }
        /*if (bh != null){
            bh.resetPhone();
        }*/
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this); // 解除全局广播
    }

    /**
     *
     * @param paramEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ParamEvent paramEvent){
        //接收到发布者发布的事件后，进行相应的处理操作
        if (paramEvent.getType() == 2){
            bh.resetPhoneTv(((BhEvent)paramEvent.getT()).getPhone());
            viewPager.setCurrentItem(2);
        }
    }

}
