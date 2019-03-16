package com.peng.dglib.other;

import android.view.Gravity;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 *      Dialog 定位
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

    RIGHT_BOTTOM(Gravity.END | Gravity.BOTTOM),

    // 下方为新拓展出来的展示方式
    LEFT_ALIGN_TOP(0),

    LEFT_ALIGN_BOTTOM(1),

    TOP_ALIGN_LEFT(2),

    TOP_ALIGN_RIGHT(3),

    BOTTOM_ALIGN_LEFT(4),

    BOTTOM_ALIGN_RIGHT(5),

    RIGHT_ALIGN_TOP(6),

    RIGHT_ALIGN_BOTTOM(7);

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
