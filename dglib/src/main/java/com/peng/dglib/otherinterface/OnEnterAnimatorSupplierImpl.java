package com.peng.dglib.otherinterface;

import android.animation.Animator;
import android.view.View;

/**
 * Created by Mr.Q on 2019/3/6
 * 描述：进入动画提供接口
 */
public interface OnEnterAnimatorSupplierImpl {
    Animator supplierAnimator(View contentView);
}
