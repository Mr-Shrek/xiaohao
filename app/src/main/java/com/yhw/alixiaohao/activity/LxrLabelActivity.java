package com.yhw.alixiaohao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.Toast;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.adapter.SortAdapter;
import com.yhw.alixiaohao.base.BaseActivity;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.fragment.LxrFragment;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.view.SideBar;
import com.yhw.alixiaohao.widget.TitleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LxrLabelActivity extends BaseActivity implements TitleManager.OnTitleClickListener {

    private Logcat log = new Logcat(LxrLabelActivity.class);

    @BindView(R.id.lxr_label_list)
    ListView listView;//通讯录控件
    @BindView(R.id.lxr_label_sideBar)
    SideBar sideBar;//通讯录右侧文字
    private List<User> list;//通讯录数据
    private SortAdapter adapter;


    private TitleManager titleManager;

    @Override
    public void afterSetView() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_lxr_label;
    }

    @SuppressLint("ResourceType")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        titleManager = new TitleManager(this,R.mipmap.ic_launcher,getString(R.string.back),getString(R.string.text_lxr_label),getString(R.string.text_lxr_all_label),0);
        titleManager.setOnTitleClickListener(this);

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
        List<User> tempUsers = ManagerFactory.getInstance().getUserManager().query("where follow = ?", "1");
        long count = ManagerFactory.getInstance().getUserManager().count();
        if (tempUsers.size() == count){
            titleManager.getTvRight().setText(getString(R.string.text_lxr_all_cancel));
        }
        log.e("count:"+count);

        list = new ArrayList<>();
        list = ManagerFactory.getInstance().getUserManager().queryAll();

        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        sideBar.setLetters(getLetters());
        adapter = new SortAdapter(this, list, true);
        listView.setAdapter(adapter);
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
            if (flag == 0) {
                letterList.add(list.get(i).getFirstLetter());
            }
        }
        String[] letters = new String[letterList.size()];
        for (int i = 0; i< letterList.size();i++){
            letters[i] = letterList.get(i);
        }
        return letters;
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
        if (titleManager.getTvRight().getText().toString().equals(getString(R.string.text_lxr_all_label))){
            titleManager.getTvRight().setText(getString(R.string.text_lxr_all_cancel));
            for (User u : list){
                u.setFollow(1);
            }
        } else {
            titleManager.getTvRight().setText(getString(R.string.text_lxr_all_label));
            for (User u : list){
                u.setFollow(0);
            }
        }
        adapter.notifyDataSetChanged();
        for (User user : list){
            String firstLetter = user.getPinyin().substring(0,1).toUpperCase();
            if (!firstLetter.matches("[A-Z]")){
                firstLetter = "#";
            }
            user.setFirstLetter(firstLetter);
        }
        ManagerFactory.getInstance().getUserManager().update(list);
    }
}
