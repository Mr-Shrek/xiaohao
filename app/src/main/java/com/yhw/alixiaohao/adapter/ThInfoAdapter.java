package com.yhw.alixiaohao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.utils.Logcat;
import com.yhw.alixiaohao.utils.TimeUtil;

import java.util.List;

/**
 * 通话记录详情 适配器
 */
public class ThInfoAdapter extends BaseAdapter {

    private final Logcat log = new Logcat(ThInfoAdapter.class);

    private List<CallRecord> items;
    private Context context;

    public ThInfoAdapter(Context ctx, List<CallRecord> list){
        this.context = ctx;
        this.items = list;

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
        if (convertView == null){
            h = new ViewHolder();
            convertView = View.inflate(context,R.layout.item_th_info,null);
            h.leftImg = convertView.findViewById(R.id.thInfoItemImg);
            h.time = convertView.findViewById(R.id.thInfoItemTime);
            h.status = convertView.findViewById(R.id.thInfoItemStatus);
            h.hx = convertView.findViewById(R.id.thInfoItemHX);
            convertView.setTag(h);
        }else {
            h = (ViewHolder) convertView.getTag();
        }
        h.leftImg.setImageResource(items.get(position).getCallType() == 0 ? R.mipmap.wd_icon_rmhd : R.mipmap.wd_icon_yhzh);
        h.time.setText(TimeUtil.calculateTime(items.get(position).getStartTime()));
        switch (items.get(position).getCallStatus()){
            case 0:
                h.status.setText("已接听");
                break;
            case 1:
                h.status.setText("未接听");
                break;
            case 2:
                h.status.setText("已呼叫");
                break;
        }
        if (position == items.size()-1){
            h.hx.setVisibility(View.GONE);
        }else {
            h.hx.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView leftImg;
        TextView time;
        TextView status;
        View hx;
    }

}
