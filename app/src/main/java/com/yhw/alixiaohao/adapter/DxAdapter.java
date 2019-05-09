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
import com.yhw.alixiaohao.entity.Message;
import com.yhw.alixiaohao.utils.TimeUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 通过话记录list适配器
 */
public class DxAdapter extends BaseSwipeAdapter {

    List<Message> items;
    Context context;

    public DxAdapter(Context mContext, List<Message> items){
        this.context = mContext;
        this.items = items;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.dx_sl;//item 头部布局
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_dx, null);//加载item布局
        return view;
    }

    @Override
    public void fillValues(int position, View view) {
        //内部封装了View复用处理
        ImageView ivDelete = view.findViewById(R.id.dx_delete);
        final SwipeLayout sl_content = view.findViewById(R.id.dx_sl);

        CircleImageView tx = view.findViewById(R.id.dx_img);
        TextView name = view.findViewById(R.id.dx_name);
        TextView msg = view.findViewById(R.id.dx_msg);
        TextView time = view.findViewById(R.id.dx_time);
        TextView count = view.findViewById(R.id.dx_count);

        sl_content.setShowMode(SwipeLayout.ShowMode.PullOut);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();
            }
        });

        Message message = items.get(position);

        tx.setImageResource(message.getTxId());
        name.setText(message.getName());
        msg.setText(message.getContent());
        String timeStr = TimeUtil.calculateTime(message.getTime());
        time.setText(timeStr);
//        count.setText("("+message.getCount()+")");

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
