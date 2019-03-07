package com.peng.dglib.other;

import android.view.Gravity;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 */
public enum  DialogGravity {

    LEFT_TOP(Gravity.START | Gravity.TOP),

    LEFT_CENTER(Gravity.START | Gravity.CENTER_VERTICAL),

    LEFT_BOTTOM(Gravity.START | Gravity.BOTTOM),

    CENTER_TOP(Gravity.CENTER_HORIZONTAL | Gravity.TOP),

    CENTER_CENTER(Gravity.CENTER),

    CENTER_BOTTOM(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM),

    RIGHT_TOP(Gravity.END | Gravity.TOP),

    RIGHT_CENTER(Gravity.END | Gravity.CENTER_VERTICAL),

    RIGHT_BOTTOM(Gravity.END | Gravity.BOTTOM);

    // 成员变量
    private int layoutGravity;

    // 构造方法
    DialogGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
    }

    public int getLayoutGravity() {
        return layoutGravity;
    }
}
