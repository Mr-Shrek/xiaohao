package com.yhw.alixiaohao.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhw.alixiaohao.R;

public class TitleManager {

    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView img_right,img_left;
    private LinearLayout leftLL,rightLL;

    /**
     * activity
     * @param activity
     * @param leftImg
     * @param left
     * @param content
     * @param rightImg
     */
    @SuppressLint("ResourceType")
    public TitleManager(Activity activity, @IdRes int leftImg, String left, String content,String right, @IdRes int rightImg){
        if (leftImg != 0){
            img_left = activity.findViewById(R.id.title_left);
            img_left.setImageResource(leftImg);
        }
        if (!TextUtils.isEmpty(left)) {
            tvLeft = activity.findViewById(R.id.title_leftTv);
            tvLeft.setText(left);
        }
        if (!TextUtils.isEmpty(content)) {
            tvTitle = activity.findViewById(R.id.title_content);
            tvTitle.setText(content);
        }
        if (!TextUtils.isEmpty(right)){
            tvRight = activity.findViewById(R.id.title_rightTv);
            tvRight.setText(right);
        }
        if (rightImg != 0){
            img_right = activity.findViewById(R.id.title_right);
            img_right.setVisibility(View.VISIBLE);
            img_right.setImageResource(rightImg);
        }
        leftLL = activity.findViewById(R.id.title_leftLL);
        rightLL = activity.findViewById(R.id.title_rightLL);
        leftLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClickListener.onLeftClick();
            }
        });
        rightLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClickListener.onRightClick();
            }
        });
    }

    /**
     * fragment
     * @param activity
     * @param leftImg
     * @param left
     * @param content
     * @param rightImg
     */
    @SuppressLint("ResourceType")
    public TitleManager(View activity, @IdRes int leftImg, String left, String content,String right, @IdRes int rightImg){
        if (leftImg != 0){
            img_left = activity.findViewById(R.id.title_left);
            img_left.setImageResource(leftImg);
        }
        if (!TextUtils.isEmpty(left)) {
            tvLeft = activity.findViewById(R.id.title_leftTv);
            tvLeft.setText(left);
        }
        if (!TextUtils.isEmpty(content)) {
            tvTitle = activity.findViewById(R.id.title_content);
            tvTitle.setText(content);
        }
        if (!TextUtils.isEmpty(right)){
            tvRight = activity.findViewById(R.id.title_rightTv);
            tvRight.setText(right);
        }
        if (rightImg != 0){
            img_right = activity.findViewById(R.id.title_right);
            img_right.setVisibility(View.VISIBLE);
            img_right.setImageResource(rightImg);
        }
        leftLL = activity.findViewById(R.id.title_leftLL);
        rightLL = activity.findViewById(R.id.title_rightLL);
        leftLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClickListener.onLeftClick();
            }
        });
        rightLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClickListener.onRightClick();
            }
        });
    }


    private OnTitleClickListener onTitleClickListener;
    public interface OnTitleClickListener{
        void onLeftClick();
        void onRightClick();
    }
    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener){
        this.onTitleClickListener = onTitleClickListener;
    }

    private void setRightTv(Activity activity, String right){
        if (tvRight == null){
            tvRight = activity.findViewById(R.id.title_rightTv);
        }
        tvRight.setText(right);
    }

    public void setRightTv(View v, String right){
        if (tvRight == null){
            tvRight = v.findViewById(R.id.title_rightTv);
        }
        tvRight.setText(right);
    }

/* -------------------------get UI */
    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public void setTvLeft(TextView tvLeft) {
        this.tvLeft = tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setTvRight(TextView tvRight) {
        this.tvRight = tvRight;
    }

    public ImageView getImg_right() {
        return img_right;
    }

    public void setImg_right(ImageView img_right) {
        this.img_right = img_right;
    }

    public ImageView getImg_left() {
        return img_left;
    }

    public void setImg_left(ImageView img_left) {
        this.img_left = img_left;
    }

}
