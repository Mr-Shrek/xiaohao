package com.yhw.alixiaohao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yhw.alixiaohao.utils.Logcat;

import java.util.List;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private Logcat log = new Logcat(MainFragmentAdapter.class);

    List<Fragment> list;

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public MainFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }
}
