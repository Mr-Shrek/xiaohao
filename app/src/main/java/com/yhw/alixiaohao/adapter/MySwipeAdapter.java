package com.yhw.alixiaohao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.CallRecord;
import com.yhw.alixiaohao.utils.TimeUtil;

import java.util.List;

/**
 * 通过话记录list适配器
 */
public class MySwipeAdapter extends BaseSwipeAdapter {

    List<CallRecord> items;
    Context context;

    public MySwipeAdapter(Context mContext, List<CallRecord> items){
        this.context = mContext;
        this.items = items;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.th_sl;//item 头部布局
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_th, null);//加载item布局
        return view;
    }

    @Override
    public void fillValues(int position, View view) {
        //内部封装了View复用处理
        ImageView ivDelete = view.findViewById(R.id.th_delete);
        final SwipeLayout sl_content = view.findViewById(R.id.th_sl);
        TextView tvPhone = view.findViewById(R.id.th_phoneNum);
        TextView tvTime = view.findViewById(R.id.th_time);
        ImageView ivGo = view.findViewById(R.id.th_go);

        sl_content.setShowMode(SwipeLayout.ShowMode.PullOut);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
            }
        });

        ivGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_content.open();
            }
        });

        tvPhone.setText(items.get(position).getCalled());
        String time = TimeUtil.calculateTime(items.get(position).getStartTime());
        tvTime.setText(time);
        if(items.get(position).getCallStatus() == 1){
            tvPhone.setTextColor(context.getResources().getColor(R.color.red));
        }

        sl_content.addSwipeListener(new SimpleSwipeListener(){
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
                onOpenCallBack.callBack(layout);
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
                onOpenCallBack.callBack(null);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                super.onStartClose(layout);
                onOpenCallBack.status(0);
            }
        });
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

    public interface OnOpenCallBack{
        void callBack(SwipeLayout layout);
        void status(int i);
    }
    private OnOpenCallBack onOpenCallBack;

    public void setOnOpenCallBack(OnOpenCallBack onOpenCallBack) {
        this.onOpenCallBack = onOpenCallBack;
    }

}
