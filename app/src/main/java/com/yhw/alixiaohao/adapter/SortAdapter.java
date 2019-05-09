package com.yhw.alixiaohao.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.model.db.ManagerFactory;
import com.yhw.alixiaohao.utils.Logcat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 联系人页面：通讯录适配器
 */
public class SortAdapter extends BaseAdapter{

    private Logcat log = new Logcat(SortAdapter.class);

    private List<User> list = null;
    private Context mContext;
    private boolean label = false;

    public SortAdapter(Context mContext, List<User> list, boolean label) {
        this.mContext = mContext;
        this.list = list;
        this.label = label;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup arg2) {
        final ViewHolder viewHolder;
        final User user = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_lxr, null);
            viewHolder.name = view.findViewById(R.id.lxr_item_name);
            viewHolder.tx = view.findViewById(R.id.lxr_item_tx);
            viewHolder.catalog = view.findViewById(R.id.lxr_item_catalog);
            viewHolder.follow = view.findViewById(R.id.lxr_item_follow);
            viewHolder.fgx = view.findViewById(R.id.lxr_item_fgx);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //设置隐藏分割线
        if (position != getCount() -1){
            //根据position获取首字母作为目录catalog
            String nextCatalog = list.get(position+1).getFirstLetter();
            if (position == getHideFgx(nextCatalog)){
                viewHolder.fgx.setVisibility(View.GONE);
            }else{
                viewHolder.fgx.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.fgx.setVisibility(View.GONE);
        }

        //根据position获取首字母作为目录catalog
        String catalog = user.getFirstLetter();
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(catalog)){
            viewHolder.catalog.setVisibility(View.VISIBLE);
            if (!label) {
                viewHolder.catalog.setText(user.getFirstLetter().equals("⭐")
                        ? mContext.getString(R.string.text_lxr_followed) : user.getFirstLetter().toUpperCase());
            } else{
                viewHolder.catalog.setText(user.getFirstLetter().toUpperCase());
            }
        }else{
            viewHolder.catalog.setVisibility(View.GONE);
        }
        viewHolder.name.setText(user.getName());
        Glide.with(mContext).load(user.getTxId()).into(viewHolder.tx);
        viewHolder.tx.setImageResource(user.getTxId());
        if (label) { // 是否显示标记图标，在联系人页面点击标签进入新页面后使用
            viewHolder.follow.setVisibility(View.VISIBLE);
            viewHolder.follow.setImageResource(this.list.get(position).getFollow() == 0 ? R.mipmap.ic_launcher : R.mipmap.wd_icon_yhzh);
            viewHolder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //关注/取消关注
                    user.setFollow(user.getFollow() == 1 ? 0 : 1);
                    String firstLetter = user.getPinyin().substring(0,1).toUpperCase();
                    if (!firstLetter.matches("[A-Z]")){
                        firstLetter = "#";
                    }
                    user.setFirstLetter(firstLetter);
                    List<User> uu = ManagerFactory.getInstance().getUserManager().query("where name = ?", user.getName());
                    log.e("---user:"+uu.get(0).getFirstLetter());
                    ManagerFactory.getInstance().getUserManager().update(user); // 更新
                    List<User> uu1 = ManagerFactory.getInstance().getUserManager().query("where name = ?", user.getName());
                    log.e("===user:"+uu1.get(0).getFirstLetter());
                    viewHolder.follow.setImageResource(user.getFollow() == 0 ? R.mipmap.ic_launcher : R.mipmap.wd_icon_yhzh);
                }
            });
        }
        return view;

    }

    final static class ViewHolder {
        TextView catalog;
        TextView name;
        CircleImageView tx;
        ImageView follow;
        View fgx;
    }

    /**
     * 获取catalog首次出现位置
     */
    private int getPositionForSection(String catalog) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getFirstLetter();
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取下一条catalog出现位置
     */
    private int getHideFgx(String catalog){
        for (int i = 0; i < getCount(); i++) {
            if (i == getCount()-1){
                return i;
            }
            String sortStr = list.get(i+1).getFirstLetter();//获取下一条首字母
            if (catalog.equalsIgnoreCase(sortStr)) {
                return i;
            }
        }
        return -1;
    }

}