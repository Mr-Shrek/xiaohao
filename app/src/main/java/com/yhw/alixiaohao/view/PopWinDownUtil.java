package com.yhw.alixiaohao.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.yhw.alixiaohao.R;


/**
 * Created by cuijunling on 2016/11/1.
 */
public class PopWinDownUtil {
    private Context context;
    private View contentView;
    private View relayView;
    private PopupWindow popupWindow;
    private int width;
    public PopWinDownUtil(Context context, View contentView, View relayView,int width){
        this.context = context;
        this.contentView = contentView;
        this.relayView = relayView;
        this.width = width;
        init();
    }
    @SuppressLint("WrongConstant")
    public void init(){
        //内容，高度，宽度
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(width);
        //动画效果
        popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(true);
        //关闭事件
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(onDismissLisener != null){
                    onDismissLisener.onDismiss();
                }
            }
        });
    }
    public void show(){
        //显示位置
        int x = -popupWindow.getWidth()+relayView.getWidth()+(int)context.getResources().getDimension(R.dimen.dp_5);
        showAsDropDown(popupWindow,relayView,x,0);
        /*if (Build.VERSION.SDK_INT < 24) {
            int x = -popupWindow.getWidth()+relayView.getWidth()+(int)context.getResources().getDimension(R.dimen.dp_5);
            Log.e("PopWinDownUtil","x:"+x);
//            popupWindow.showAsDropDown(relayView,x,0);
            showAsDropDown(popupWindow,relayView,x,0);
        } else {
            // 获取控件的位置，安卓系统>7.0
            int[] location = new int[2];
            relayView.getLocationOnScreen(location);
            popupWindow.showAtLocation(relayView, Gravity.NO_GRAVITY, 0, location[1] + relayView.getHeight());
        }*/
    }

    /**
     *
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    private void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            /*int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);*/
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public void hide(){
        if(popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    private OnDismissLisener onDismissLisener;
    public void setOnDismissListener(OnDismissLisener onDismissLisener){
        this.onDismissLisener = onDismissLisener;
    }
    public interface OnDismissLisener{
        void onDismiss();
    }
    public boolean isShowing(){
        return popupWindow.isShowing();
    }
}
