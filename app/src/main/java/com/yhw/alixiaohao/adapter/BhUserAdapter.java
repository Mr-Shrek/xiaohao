package com.yhw.alixiaohao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.User;
import com.yhw.alixiaohao.utils.Logcat;

import java.util.List;

/**
 * 拨号页面横向ListView 适配器
 */
public class BhUserAdapter extends BaseAdapter {

    private final Logcat log = new Logcat(ThInfoAdapter.class);

    private List<User> items;
    private Context context;

    public BhUserAdapter(Context context,List<User> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size() > 0 ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            h = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_bh, null);
            h.img = convertView.findViewById(R.id.bhItemImg);
            h.name = convertView.findViewById(R.id.bhItemName);
            h.phone = convertView.findViewById(R.id.bhItemPhone);
            convertView.setTag(h);
        }else {
            h = (ViewHolder) convertView.getTag();
        }
        h.img.setImageResource(items.get(position).getTxId());
        h.name.setText(items.get(position).getName());
        h.phone.setText(items.get(position).getPhoneNumber());
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView name;
        TextView phone;
    }

}
