package com.yhw.alixiaohao.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.activity.DxEditActivity;
import com.yhw.alixiaohao.activity.DxInfoActivity;
import com.yhw.alixiaohao.adapter.DxAdapter;
import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.AnimationUtil;
import com.yhw.alixiaohao.utils.TimeUtil;
import com.yhw.alixiaohao.utils.Utils;
import com.yhw.alixiaohao.widget.AfterTextWatcher;
import com.yhw.alixiaohao.widget.TitleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信
 */
public class DxFragment extends Fragment implements TitleManager.OnTitleClickListener {

    private View mView;
    private List<Message> list;//通话记录集合
    private ListView listLv;
    private DxAdapter adapter;
    private SwipeLayout swipeLayout;//侧拉布局
    private int status = -1;//获取当前滑动状态 0：开始关闭
    private ImageView btnEdit;//编辑一条新的短信
    private RelativeLayout queryRl;// 查询按钮弹出的查询输入框布局
    private ImageView btnQuery; // 根据输入的内容查询消息
    private EditText etQuery; // 查询内容

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_dx,null);
        }

        initView();
        initData();
        return mView;
    }

    private void initView(){
        @SuppressLint("ResourceType") TitleManager titleManager =
                new TitleManager(mView,R.mipmap.ic_launcher,"",getString(R.string.text_main_dx),"",R.mipmap.gd);
        titleManager.setOnTitleClickListener(this);
        listLv = mView.findViewById(R.id.dx_list);
        queryRl = mView.findViewById(R.id.dx_queryRl);
        btnQuery = mView.findViewById(R.id.dx_queryImg);
        etQuery = mView.findViewById(R.id.dx_queryEt);
        btnEdit = mView.findViewById(R.id.dx_edit);
    }

    private void initData(){
        list = new ArrayList<>();
        update();
        adapter = new DxAdapter(getContext(),list);
        listLv.setAdapter(adapter);
        adapter.setMode(Attributes.Mode.Single);
        adapter.setOnOpenCallBack(new DxAdapter.OnOpenCallBack() {
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
                Intent in = new Intent(getContext(),DxInfoActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("data",list.get(position));
                in.putExtras(b);
                startActivity(in);
            }
        });

        // 检测输入框无数据显示所有短信记录
        etQuery.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0){
                    update();
                }
            }
        });

        // 软键盘搜索按钮事件
        etQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){//搜索按键action
                    Utils.closeInoutDecorView(getActivity()); // 关闭软键盘
                    String content = etQuery.getText().toString();
                    if (TextUtils.isEmpty(content)){
                        return true;
                    }
                    List<Message> messages = ManagerFactory.getInstance().getMessageManager().query("where name like ? or content like ?","%"+content+"%","%"+content+"%");
                    list.clear();
                    for (Message m : messages){
                        list.add(m);
                    }
                    TimeUtil.dxlistSort(list);
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DxEditActivity.class));
            }
        });
        /*btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etQuery.getText().toString().trim();
                List<Message> messages = ManagerFactory.getInstance().getMessageManager().query("where name like ? or content like ?","%"+content+"%","%"+content+"%");
                list.clear();
                for (Message m : messages){
                    list.add(m);
                }
                TimeUtil.dxlistSort(list);
                adapter.notifyDataSetChanged();
            }
        });*/
    }
    public void updateUI(){
        if (list == null) return;
        if (swipeLayout != null) {
            swipeLayout.close();
        }
        update();
        adapter.notifyDataSetChanged();
    }

    private void update(){
        list.clear();
        List<Message> messages = ManagerFactory.getInstance().getMessageManager().queryAll();
        for (Message m : messages){
            list.add(m);
        }
        TimeUtil.dxlistSort(list);
    }

    @Override
    public void onLeftClick() {
        if (queryRl.getVisibility() == View.VISIBLE){
            queryRl.setVisibility(View.GONE);
            queryRl.setAnimation(AnimationUtils.makeOutAnimation(getContext(), false));// 从左边移除
        }else{
            queryRl.setVisibility(View.VISIBLE);
            queryRl.setAnimation(AnimationUtil.moveToViewLocation());// 从下方向上移入
        }
    }

    @Override
    public void onRightClick() {

    }
}
