package com.yhw.alixiaohao.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.activity.LxrInfoActivity;
import com.yhw.alixiaohao.activity.LxrLabelActivity;
import com.yhw.alixiaohao.activity.TestActivity;
import com.yhw.alixiaohao.adapter.SortAdapter;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.sysbeans.PhoneDto;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.phoneUtils.PhoneUtil;
import com.yhw.alixiaohao.view.SideBar;
import com.yhw.alixiaohao.widget.AfterTextWatcher;
import com.yhw.alixiaohao.widget.CommomDialog;
import com.yhw.alixiaohao.widget.TitleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 联系人
 */
public class LxrFragment extends Fragment implements View.OnClickListener,TitleManager.OnTitleClickListener {

    private Logcat log = new Logcat(LxrFragment.class);
    private View mView;
    private TextView queryEt;
    private RadioButton btnEdit,btnLabel,btnSync,btnFastDial;// 编辑/标签/同步/快速拨号

    private ListView listView;//通讯录控件
    private SideBar sideBar;//通讯录右侧文字
    private List<User> list;//通讯录数据
    private SortAdapter adapter;

    private PhoneUtil phoneUtil;//获取手机联系人Util
    private List<PhoneDto> phoneDtos;//手机联系人List


    private View headView;//头部我的名片布局

    private TitleManager titleManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.fragment_lxr,null);
        }
        initView();
        initData();
        return mView;
    }

    @SuppressLint("ResourceType")
    private void initView() {
        titleManager = new TitleManager(mView,0,"",getString(R.string.text_main_lxr),"",R.mipmap.gd);
        titleManager.setOnTitleClickListener(this);
        listView = mView.findViewById(R.id.lxr_list);
        sideBar = mView.findViewById(R.id.lxr_sideBar);
        queryEt = mView.findViewById(R.id.lxr_queryEt);

        btnEdit = mView.findViewById(R.id.lxr_edit);
        btnLabel = mView.findViewById(R.id.lxr_label);
        btnSync = mView.findViewById(R.id.lxr_sync);
        btnFastDial = mView.findViewById(R.id.lxr_fast_dial);

        // 初始化listview的头部布局
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_lxr,null);

        headView.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnLabel.setOnClickListener(this);
        btnSync.setOnClickListener(this);
        btnFastDial.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.lxr_head:
                Toast.makeText(getContext(), "click Head view", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lxr_edit:

                break;
            case R.id.lxr_label:
                startActivity(new Intent(getContext(), LxrLabelActivity.class));
                break;
            case R.id.lxr_sync:
                new CommomDialog(getContext(), R.style.dialog, "是否确认同步本地联系人", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            phoneDtos = phoneUtil.getPhone();//获取手机联系人
                            for (PhoneDto phoneDto : phoneDtos){
                                User user = new User(phoneDto.getName(),phoneDto.getTelPhone());
                                list.add(user);
                            }
                            Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
                            adapter.notifyDataSetChanged();
                            ManagerFactory.getInstance().getUserManager().saveOrUpdate(list);// 更新本地联系人到数据库中
                        }
                        dialog.dismiss();
                    }
                }) .setTitle("提示").show();

                break;
            case R.id.lxr_fast_dial:

                break;
        }
    }

    @SuppressLint("ResourceType")
    private void initData() {
        phoneUtil = new PhoneUtil(getContext());

        list = new ArrayList<>();
        update();
        adapter = new SortAdapter(getContext(), list, false);
        listView.addHeaderView(headView);
        listView.setAdapter(adapter);

        //字母导航选择监听
        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i < list.size(); i++) {
                    if (selectStr.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                        listView.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }
        });
        queryEt.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (titleManager.getImg_right().getVisibility() == View.VISIBLE &&
                        TextUtils.isEmpty(s.toString())){
                    return;
                }
                titleManager.getImg_right().setVisibility(View.GONE);
                titleManager.setRightTv(mView, getString(R.string.cancel));
                titleManager.getTvRight().setVisibility(View.VISIBLE);
                sideBar.setVisibility(View.GONE);
                if (TextUtils.isEmpty(s.toString())){
                    list.clear();
                    adapter.notifyDataSetChanged();
                    return;
                }
                String content = s.toString();
                List<User> users = ManagerFactory.getInstance().getUserManager().query("where name like ? or pinyin like ?","%"+content+"%","%"+content+"%");
                list.clear();
                for (User u : users){
                    list.add(u);
                }
                Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getContext(),LxrInfoActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("data",list.get(position - 1));
                in.putExtras(b);
                startActivity(in);
            }
        });

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {
        if (titleManager.getTvRight() != null &&
                titleManager.getTvRight().getText().toString().trim().equals(getString(R.string.cancel))){
            titleManager.getTvRight().setVisibility(View.GONE);
            titleManager.getImg_right().setVisibility(View.VISIBLE);
            sideBar.setVisibility(View.VISIBLE);
            update();
            adapter.notifyDataSetChanged();
            queryEt.setText("");
        }else {
            startActivity(new Intent(getContext(), TestActivity.class));
        }
    }

    /**
     * 其他页面调用的更新数据方法
     */
    public void updateUI(){
        if (list != null) {
            update();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 重新查询所有用户信息
     */
    private void update(){
        list.clear();
        List<User> users = ManagerFactory.getInstance().getUserManager().queryAll();
        for (User u : users){
            list.add(u);
        }
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        sideBar.setLetters(getLetters());
        // 已关注
        /*List<User> followUsers = ManagerFactory.getInstance().getUserManager().query("where follow = ?", "1");
        List<User> tempFollowUsers = new ArrayList<>();
        for (User u : followUsers){
            User user = new User();
            user.setTxId(u.getTxId());
            user.setName(u.getName());
            user.setFollow(u.getFollow());
            user.setFirstLetter("⭐");
            user.setPhoneNumber(u.getPhoneNumber());
            tempFollowUsers.add(user);
        }
        followUsers.clear();
        if (tempFollowUsers.size() != list.size()){
            list.addAll(0, tempFollowUsers);
        }*/
    }

    /**
     * 获取右侧导航字母
     */
    private String[] getLetters(){
        //获取右侧有哪些导航字母，并初始化设置
        List<String> letterList = new ArrayList<>();
        for (int i = 0; i< list.size(); i++){
            int flag = 0;
            for (int j = 0; j < letterList.size(); j++){
                if (list.get(i).getFirstLetter().equals(letterList.get(j))){
                    flag = -1;
                    break;
                }
            }
            if (list.get(i).getFirstLetter().equals("⭐")){
                continue;
            }
            if (flag == 0) {
                letterList.add(list.get(i).getFirstLetter());
            }
        }
        for (String s : letterList){
            log.e("letterList:"+s);
        }

        String[] letters = new String[letterList.size()+1];
        for (int i = 0; i< letterList.size();i++){
            letters[i+1] = letterList.get(i);
        }
        for (int i=0;i<letters.length;i++){
            log.e("letters:"+letters[i]);
        }
        //⭐
        letters[0] = "⭐";
        return letters;
    }

}
