package com.yhw.alixiaohao.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yhw.alixiaohao.R;
import com.yhw.alixiaohao.entity.Message;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 短信
 */
public class DxInfoAdapter extends RecyclerView.Adapter<DxInfoAdapter.ViewHolder> {

    private List<Message> mMsgList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        RelativeLayout rightLayout;
        CircleImageView leftImg;
        CircleImageView rightImg;
        TextView leftMsg;
        TextView rightMsg;
        TextView time;

        // view表示父类的布局，用其获取子项
        public ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftImg = view.findViewById(R.id.dx_info_left_img);
            rightImg = view.findViewById(R.id.dx_info_right_img);
            leftMsg = view.findViewById(R.id.dx_info_left_msg);
            rightMsg = view.findViewById(R.id.dx_info_right_msg);
            time = view.findViewById(R.id.dx_info_time);
        }
    }

    public DxInfoAdapter(List<Message> msgList, Context context) {
        mMsgList = msgList;
        this.context = context;
    }

    /**
     * 创建 ViewHolder 加载 RecycleView 子项的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dx_info, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 为 RecycleView 子项赋值
     * 赋值通过 position 判断子项位置
     * 当子项进入界面时执行
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message msg = mMsgList.get(position);
        if (msg.getMsgType() == Message.TYPE_RECEIVED) {
            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftImg.setImageResource(msg.getTxId());
            holder.leftMsg.setText(msg.getContent());
        } else if (msg.getMsgType() == Message.TYPE_SENT) {
            // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightImg.setImageResource(msg.getTxId());
            holder.rightMsg.setText(msg.getContent());
            switch (msg.getType()){
                case 0:
                    holder.rightMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
                    break;
                case 1:
                    holder.rightMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_blue));
                    break;
                case 2:
                    holder.rightMsg.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
                    break;
            }
        }
        holder.time.setText(msg.getTime());
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    public void setFailed(int position){

    }

}
