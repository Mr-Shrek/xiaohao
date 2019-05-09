package com.yhw.alixiaohao.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.yhw.alixiaohao.R;

import java.util.List;
/**
 * Created by cuiMarker on 2016/12/13.
 */
public class DropdownButton extends RelativeLayout implements Checkable, View.OnClickListener, PopWinDownUtil.OnDismissLisener, AdapterView.OnItemClickListener {
    /**
     * 菜单按钮文字内容
     */
    private TextView text;
    private ImageView dropView;
    private boolean isCheced;
    private PopWinDownUtil popWinDownUtil;
    private Context mContext;
    private DropDownAdapter adapter;
    /**
     * 传入的数据
     */
    private List<DropBean> drops;
    /**
     * 当前被选择的item位置
     */
    private int selectPosition;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //菜单按钮的布局
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_button,this, true);
        text = (TextView) view.findViewById(R.id.textView);
        dropView = (ImageView) view.findViewById(R.id.dropView);
        //点击事件，点击外部区域隐藏popupWindow
        setOnClickListener(this);
    }
    /**
     * 添加数据，默认选择第一项
     * @param dropBeans
     */
    public void setData(List<DropBean> dropBeans,int width){
        if(dropBeans == null || dropBeans.isEmpty()){
            return;
        }
        drops = dropBeans;
        drops.get(0).setChoiced(true);
        text.setText(drops.get(0).getName());
        selectPosition = 0;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dropdown_content, null);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        adapter = new DropDownAdapter(drops,mContext);
        listView.setAdapter(adapter);

        popWinDownUtil = new PopWinDownUtil(mContext,view,this,width);
        popWinDownUtil.setOnDismissListener(this);
    }

    public void hideJianTou(){
        dropView.setVisibility(GONE);
    }
    public void setText(CharSequence content) {
        text.setText(content);
    }
    /**
     * 根据传过来的参数改变状态
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        isCheced = checked;
        Drawable icon;
        if (checked) {
            icon = getResources().getDrawable(R.mipmap.ic_dropdown_actived);
            //text.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            popWinDownUtil.show();
        } else {
            icon = getResources().getDrawable(R.mipmap.ic_dropdown_normal);
            //text.setTextColor(getResources().getColor(R.color.black));
            popWinDownUtil.hide();
        }
        //把箭头画到textView右边
        //text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        dropView.setImageDrawable(icon);
    }
    public int getSelectedItemPosition(){
        return selectPosition;
    }
    public String getSelectedItem(){
        return drops.get(selectPosition).getName();
    }
    @Override
    public boolean isChecked() {
        return isCheced;
    }
    @Override
    public void toggle() {
        setChecked(!isCheced);
    }
    @Override
    public void onClick(View v) {
        setChecked(!isCheced);
    }

    @Override
    public void onDismiss() {
        setChecked(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setSelection(position);
    }

    public void setSelection(int position){
        if(selectPosition == position){
            return;
        }
        drops.get(selectPosition).setChoiced(false);
        drops.get(position).setChoiced(true);
        text.setText(drops.get(position).getName());
        adapter.notifyDataSetChanged();
        selectPosition = position;
        popWinDownUtil.hide();
        if(onDropItemSelectListener != null){
            onDropItemSelectListener.onItemSelected(position);
        }
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

    private int measureContentWidth(ListAdapter listAdapter) {
        ViewGroup mMeasureParent = null;
        int maxWidth = 0;
        View itemView = null;
        int itemType = 0;
        final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        final int count = listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            final int positionType = listAdapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            if (mMeasureParent == null) {
                mMeasureParent = new FrameLayout(mContext);
            }
            itemView = listAdapter.getView(i, itemView, mMeasureParent);
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            final int itemWidth = itemView.getMeasuredWidth();
            if (itemWidth > maxWidth) {
                maxWidth = itemWidth;
            }
        }
        return maxWidth;
    }
}
