package com.peng.dglib.listener;

import android.animation.Animator;

import com.peng.dglib.otherinterface.OnAnimatorCancelListener;
import com.peng.dglib.otherinterface.OnAnimatorEndListener;
import com.peng.dglib.otherinterface.OnAnimatorRepeatListener;
import com.peng.dglib.otherinterface.OnAnimatorStartListener;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 *      动画监听器
 */
public class AnimatorListener implements Animator.AnimatorListener {


    @Override
    public void onAnimationStart(Animator animation) {
        onAnimatorStartListener.animatorStart(animation);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        onAnimatorEndListener.animatorEnd(animation);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        onAnimatorCancelListener.animatorCancel(animation);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        onAnimatorRepeatListener.animatorRepeat(animation);
    }

    public OnAnimatorStartListener onAnimatorStartListener;
    public OnAnimatorEndListener onAnimatorEndListener;
    public OnAnimatorRepeatListener onAnimatorRepeatListener;
    public OnAnimatorCancelListener onAnimatorCancelListener;

    public AnimatorListener onAnimatorStart(OnAnimatorStartListener onAnimatorStartListener) {
        this.onAnimatorStartListener = onAnimatorStartListener;
        return this;
    }

    public AnimatorListener onAnimatorEnd(OnAnimatorEndListener onAnimatorEndListener) {
        this.onAnimatorEndListener = onAnimatorEndListener;
        return this;
    }

    public AnimatorListener onAnimatorRepeat(OnAnimatorRepeatListener onAnimatorRepeatListener) {
        this.onAnimatorRepeatListener = onAnimatorRepeatListener;
        return this;
    }

    public AnimatorListener onAnimatorCancel(OnAnimatorCancelListener onAnimatorCancelListener) {
        this.onAnimatorCancelListener = onAnimatorCancelListener;
        return this;
    }

}
