package com.yhw.alixiaohao.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuiMarker on 2017/9/26.
 */
public class PopupMenu implements AdapterView.OnItemClickListener {
    private Context context;
    private PopWinDownUtil popWinDownUtil;
    private DropDownAdapter adapter;
    private List<DropBean> drops;

    public PopupMenu(Context context){
        this.context = context;
    }
    public void setData(String[] datas,View vv,int width) {
        View view = LayoutInflater.from(context).inflate(R.layout.dropdown_content, null);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        drops = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            drops.add(new DropBean(datas[i]));
        }
        adapter = new DropDownAdapter(drops,context);
        listView.setAdapter(adapter);
        popWinDownUtil = new PopWinDownUtil(context,view,vv, DensityUtil.dp2px(width));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popWinDownUtil.hide();
        if(onDropItemSelectListener != null){
            onDropItemSelectListener.onItemSelected(position);
        }
    }

    public void show(){
        popWinDownUtil.show();
    }

    private OnItemSelectedListener onDropItemSelectListener;
    public void setOnItemSelectedListener(OnItemSelectedListener onDropItemSelectListener){
        this.onDropItemSelectListener = onDropItemSelectListener;
    }
    public interface OnItemSelectedListener{
        void onItemSelected(int postion);
    }


    class DropDownAdapter extends BaseAdapter {
        private List<DropBean> drops;
        private Context context;
        public DropDownAdapter(List<DropBean> drops, Context context){
            this.drops = drops;
            this.context = context;
        }
        @Override
        public int getCount() {
            return drops.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_item,parent,false);
                holder.tv = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(drops.get(position).getName());
            return convertView;
        }
        private class ViewHolder{
            TextView tv;
        }
    }
}
