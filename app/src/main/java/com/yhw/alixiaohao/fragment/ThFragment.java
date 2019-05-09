package com.yhw.alixiaohao.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.activity.MainActivity;
import com.yhw.alixiaohao.activity.TestActivity;
import com.yhw.alixiaohao.activity.ThInfoActivity;
import com.yhw.alixiaohao.adapter.MySwipeAdapter;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.TimeUtil;
import com.yhw.alixiaohao.widget.TitleManager;


import java.util.ArrayList;
import java.util.List;


/**
 * 最新通话
 */
public class ThFragment extends Fragment implements TitleManager.OnTitleClickListener {

    private final Logcat log = new Logcat(ThFragment.class);

    private View mView;
    private List<CallRecord> list;//通话记录集合
    private ListView listLv;
    private MySwipeAdapter myAdapter;

    private SwipeLayout swipeLayout;
    private int status = -1;//获取当前滑动状态 0：开始关闭

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_th,null);
            initUI();
            initData();
        }
        return mView;
    }

    private void initUI(){
        @SuppressLint("ResourceType") TitleManager titleManager = new TitleManager(mView,0,"",getString(R.string.text_main_zxth),"",R.mipmap.gd);
        titleManager.setOnTitleClickListener(this);
        listLv = mView.findViewById(R.id.th_list);
    }

    private void initData(){
        list = new ArrayList<>();
        update();
        myAdapter = new MySwipeAdapter(getContext(),list);
        listLv.setAdapter(myAdapter);
        myAdapter.setMode(Attributes.Mode.Single);
        myAdapter.setOnOpenCallBack(new MySwipeAdapter.OnOpenCallBack() {
            @Override
            public void callBack(SwipeLayout layout) {
                swipeLayout = layout;
            }

            @Override
            public void status(int i) {
                status = i;
            }
        });
        listLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (status == 0){//触发点击事件时判断是否刚刚触发过关闭删除按钮
                    status = -1;
                    return;
                }
                Intent in = new Intent(getContext(),ThInfoActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("data",list.get(position));
                in.putExtras(b);
                startActivity(in);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    public void updateUI(){
        if (list == null) return;
        if (swipeLayout != null) {
            swipeLayout.close();
        }
        update();
        myAdapter.notifyDataSetChanged();
    }



    @Override
    public void onLeftClick() {

    }

    public void onRightClick() {
        startActivity(new Intent(getContext(),TestActivity.class));
    }

    private void update(){
        list.clear();
        List<CallRecord> users = ManagerFactory.getInstance().getCallRecordManager().queryAll();
        for (CallRecord c : users){
            list.add(c);
        }
        TimeUtil.thlistSort(list);
    }

}
