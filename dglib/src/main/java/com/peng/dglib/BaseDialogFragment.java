package com.peng.dglib;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.peng.dglib.listener.AnimatorListener;
import com.peng.dglib.other.DialogFragmentOptions;
import com.peng.dglib.other.DialogGravity;
import com.peng.dglib.other.ViewHolder;
import com.peng.dglib.otherinterface.OnAnimatorEndListener;
import com.peng.dglib.otherinterface.OnAnimatorStartListener;
import com.peng.dglib.utils.CalculateUtils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 * 1、生命周期
 * onAttach、onCreate、onCreateView、onActivityCreate、onStart、onResume、onPause、onStop、onDestroyView、onDestroy、onDetach
 */
public class BaseDialogFragment extends DialogFragment {
    /**
     * 根布局
     */
    private View rootView;

    /**
     * 绑定的 activity
     */
    private AppCompatActivity mActivity;

    /**
     * 执行顺序：2
     * 绑定activity，不建议使用fragment里面自带的getActivity()
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    public AppCompatActivity getAppCompatActivity() {
        return mActivity;
    }

    /**
     * 是否已经dismiss，避免主动调用dismiss的时候与onStop中触发两次相同事件
     */
    private AtomicBoolean dismissed = new AtomicBoolean(false);

    /**
     * 保存UI状态的标签
     */
    private String options = "options";

    /**
     * BaseDialogFragment 的默认操作样式
     */
    private DialogFragmentOptions dialogFragmentOptions = new DialogFragmentOptions();

    /**
     * 此方法用于子类进行覆写，以此来修改对话框的样式
     *
     * @return
     */
    protected DialogFragmentOptions getDialogFragmentOptions() {
        return dialogFragmentOptions;
    }

    private void setDialogFragmentOptions(DialogFragmentOptions dialogFragmentOptions) {
        this.dialogFragmentOptions = dialogFragmentOptions;
    }


    /**
     * 懒加载，根据 mDialogOptions.duration 来延迟加载实现懒加载（曲线救国）
     */
    private void onLazy() {
        convertView(new ViewHolder(rootView), this);
    }

    /**
     * 执行顺序：3
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 根据compile和runtime提供者更新属性
        if (dialogFragmentOptions != null) {
            // 设置 dialog 样式
            setStyle(dialogFragmentOptions.dialogStyle, dialogFragmentOptions.getDialogTheme(this));
        }
        // 恢复保存的配置
        if (savedInstanceState != null) {
            DialogFragmentOptions dialogFragmentOptions = (DialogFragmentOptions) savedInstanceState.getSerializable(options);
            if (dialogFragmentOptions != null) {
                this.dialogFragmentOptions = dialogFragmentOptions;
            }
        }
    }

    /**
     * 执行顺序：4
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载布局
        rootView = inflater.inflate(dialogFragmentOptions.layoutId, container, false);
        if (!dialogFragmentOptions.isLazy) {
            convertView(new ViewHolder(rootView), this);
        } else {
            //懒加载
            rootView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLazy();
                }
            }, dialogFragmentOptions.duration);
        }
        return rootView;
    }

    /**
     * 数据绑定到视图/视图控件监听等
     */
    private void convertView(ViewHolder holder, BaseDialogFragment dialogFragment) {
//        dialogFragmentOptions.convertListener.convertView(holder, dialogFragment);
    }

    /**
     * 执行顺序：5
     */
    @Override
    public void onStart() {
        super.onStart();
        //初始化配置
        initParams();
    }

    /**
     * 屏幕旋转等导致 DialogFragment 销毁后重建时保存数据
     * （主要保存 dialogOptions 中的一些配置属性和监听，数据的保存还需自己手动来）
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(options, dialogFragmentOptions);
    }

    /**
     * 主动dismiss时会在onStop前调用（当采用特殊的view动画时，需要主动调用dismiss，才能生效退出动画）
     */
    @Override
    public void dismiss() {
        //如果没自定义的 view 动画，那么直接执行
        if (dialogFragmentOptions.exitAnimator == null) {
            //如果没有执行过监听操作才执行，并且把监听设为已执行状态
            if (dismissed.compareAndSet(false, true)) {
                executeDismissListener();
                if (dialogFragmentOptions.allowingStateLoss) {
                    dismissAllowingStateLoss();
                } else {
                    dismiss();
                }
            } else {//如果有动画
                //直接执行动画，该动画已经设置过监听，将会在结束动画时调用super.dismiss()方法
                dialogFragmentOptions.exitAnimator.start();
            }
        }
    }

    /**
     * 如果不是主动dismiss，而是点击屏幕或者返回键，
     * 就不会调用dismiss方法，直接走onStop
     * 所以需要在这里也调用dismiss的监听
     */
    @Override
    public void onStop() {
        super.onStop();
        //判断时候已经执行过dismiss的监听操作，如果已执行过，那么重新设为未执行监听状态
        if (dismissed.compareAndSet(true, false)) {
            return;
        }
        executeDismissListener();
    }

    /**
     * 执行 show 时候的监听操作
     */
    public void executeShowListener() {

    }

    /**
     * 执行dismiss时候的监听操作
     */
    public void executeDismissListener() {

    }

    /**
     * 进入动画的listener
     */
    private AnimatorListener animatorEnterListener;

    /**
     * 退出动画的listener
     */
    private AnimatorListener animatorExitListener;

    /**
     * 初始化进入动画的listener
     */
    private AnimatorListener initAnimatorEnterListener() {

        animatorEnterListener = new AnimatorListener().onAnimatorStart(new OnAnimatorStartListener() {
            @Override
            public void animatorStart(Animator animator) {
                dialogFragmentOptions.canClick = false;
            }
        }).onAnimatorEnd(new OnAnimatorEndListener() {
            @Override
            public void animatorEnd(Animator animator) {
                dialogFragmentOptions.canClick = true;
            }
        });
        return animatorEnterListener;
    }

    /**
     * 初始化退出动画的listener
     */
    private AnimatorListener initAnimatorExitListener() {

        animatorExitListener = new AnimatorListener().onAnimatorStart(new OnAnimatorStartListener() {
            @Override
            public void animatorStart(Animator animator) {
                dialogFragmentOptions.canClick = false;
            }
        }).onAnimatorEnd(new OnAnimatorEndListener() {
            @Override
            public void animatorEnd(Animator animator) {
                //退出动画结束时调用super.dismiss()
                if (dismissed.compareAndSet(false, true)) {
                    executeDismissListener();
                    if (dialogFragmentOptions.allowingStateLoss) {
                        dismissAllowingStateLoss();
                    } else {
                        dismiss();
                    }
                }
                dialogFragmentOptions.canClick = true;
            }
        });
        return animatorExitListener;
    }

    /**
     * 初始化配置
     */
    private void initParams() {
        // 设置 dialog 的初始化数据
        if (getDialog().getWindow() != null) {
            // 设置 dialog 显示时，布局中 view 的自定义动画
            if (dialogFragmentOptions.enterAnimator != null) {
                dialogFragmentOptions.enterAnimator.setTarget(getDialog().getWindow().getDecorView().findViewById(android.R.id.content));
                dialogFragmentOptions.enterAnimator.addListener(initAnimatorEnterListener());
            }
            // 设置 dialog 隐藏时，布局中 view 的自定义动画
            if (dialogFragmentOptions.exitAnimator != null) {
                dialogFragmentOptions.exitAnimator.setTarget(getDialog().getWindow().getDecorView().findViewById(android.R.id.content));
                dialogFragmentOptions.exitAnimator.addListener(initAnimatorExitListener());
            }
            // 设置 dialog 的 statusBarColor
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getDialog().getWindow().setStatusBarColor(dialogFragmentOptions.dialogStatusBarColor);
            }
            // 设置 dialog 的 statusBar 的显示模式
            dialogFragmentOptions.setDialogFragmentStatusBarMode(this);

            // 设置 window 属性
            WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
            if (layoutParams != null) {
                layoutParams.dimAmount = dialogFragmentOptions.dimAmount;

                // 设置 dialog 宽度
                if (dialogFragmentOptions.width == 0) {
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                } else {
                    layoutParams.width = dialogFragmentOptions.width;
                }

                // 设置 dialog 高度
                if (dialogFragmentOptions.height == 0) {
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                } else {
                    layoutParams.height = dialogFragmentOptions.height;
                }

                // 当左右占满时，设置左右两边的平均边距
                if (dialogFragmentOptions.isFullHorizontal) {
                    layoutParams.horizontalMargin = 0f;
                    layoutParams.width = CalculateUtils.getScreenWidth(getResources()) - 2 * dialogFragmentOptions.fullHorizontalMargin;
                } else {
                    //没有占满的时候，设置水平方向的相对边距
                    if (dialogFragmentOptions.horizontalMargin < 0) {
                        layoutParams.horizontalMargin = 0f;
                    } else if (dialogFragmentOptions.horizontalMargin < 0 && dialogFragmentOptions.horizontalMargin < 1) {
                        layoutParams.horizontalMargin = dialogFragmentOptions.horizontalMargin;
                    } else {
                        layoutParams.horizontalMargin = dialogFragmentOptions.horizontalMargin / CalculateUtils.getScreenWidth(getResources());
                    }
                }

                //（不包含statusBar）当上下占满时，设置上下的平均边距
                if (dialogFragmentOptions.isFullVertical) {
                    layoutParams.verticalMargin = 0f;
                    layoutParams.height = CalculateUtils.getScreenHeight(getResources()) - 2 * dialogFragmentOptions.fullVerticalMargin;
                } else {
                    //没有占满的时候，设置水平方向的相对边距
                    if (dialogFragmentOptions.verticalMargin < 0) {
                        layoutParams.verticalMargin = 0f;
                    } else if (dialogFragmentOptions.verticalMargin > 0 && dialogFragmentOptions.verticalMargin < 1) {
                        layoutParams.verticalMargin = dialogFragmentOptions.verticalMargin;
                    } else {
                        layoutParams.verticalMargin = dialogFragmentOptions.verticalMargin / CalculateUtils.getScreenHeight(getResources());
                    }
                }

                //（包含StatusBar）真正的全屏
                if (dialogFragmentOptions.isFullVerticalOverStatusBar) {
                    layoutParams.verticalMargin = 0f;
                    layoutParams.height = CalculateUtils.getScreenHeightOverStatusBar(mActivity) - 2 * dialogFragmentOptions.fullVerticalMargin;
                }
                // 设置位置(如果设置了 asView, 那么 gravity 则永远为 LEFT_TOP )
                layoutParams.gravity = dialogFragmentOptions.gravityAsWindow.getLayoutGravity();
                layoutParams.windowAnimations = dialogFragmentOptions.animStyle;
                //如果设置了asView，那么设置 dialog 的 x，y 值，将 dialog 显示在 view 附近
                if (dialogFragmentOptions.isAsView()) {
                    layoutParams.x = dialogFragmentOptions.dialogViewX;
                    layoutParams.y = dialogFragmentOptions.dialogViewY;
                }
            }
            getDialog().getWindow().setAttributes(layoutParams);
        }

        //设置是否点击外部不消失
        setCancelable(dialogFragmentOptions.backCancel);
        //设置是否点击屏幕区域不消失（点击返回键可消失）
        getDialog().setCanceledOnTouchOutside(dialogFragmentOptions.touchCancel);
        //设置按键拦截事件，一般在全屏显示需要重写返回键时用到
        setOnKeyListener();
    }

    /**
     * 重写按钮监听
     */
    private void setOnKeyListener() {

        //如果设置过特殊的动画，并且没有设置返回建的监听，那么默认设置一个返回键的监听
        if (dialogFragmentOptions.exitAnimator != null && dialogFragmentOptions.mOnKeyListener == null) {
            DialogInterface.OnKeyListener onKey = new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (dialogFragmentOptions.canClick) {
                            dismiss();
                            return true;
                        } else {
                            return true;
                        }
                    }
                    return false;
                }
            };
            getDialog().setOnKeyListener(onKey);
        } else {
            //如果不是特殊动画，或者用户自定义了OnKeyListener，那么直接将 onKeyListener 设置
            if (dialogFragmentOptions.mOnKeyListener != null) {
                getDialog().setOnKeyListener(dialogFragmentOptions.mOnKeyListener);
            }
        }
    }

    /**
     * 将 dialog 显示在屏幕位置
     *
     * @param manager
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager) {
        return showOnWindow(manager, dialogFragmentOptions.gravityAsWindow, dialogFragmentOptions.animStyle, null, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将 dialog 显示在屏幕位置，一般调用该方法，然后重写 getDialogFragmentOptions
     *
     * @param manager
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, String tag) {
        return showOnWindow(manager, dialogFragmentOptions.gravityAsWindow, dialogFragmentOptions.animStyle, tag, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将 dialog 显示在屏幕位置
     *
     * @param manager
     * @param gravity 对话框显示的位置
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, DialogGravity gravity) {
        return showOnWindow(manager, gravity, dialogFragmentOptions.animStyle, null, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将 dialog 显示在屏幕位置
     *
     * @param manager
     * @param newAnim
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, int newAnim) {
        return showOnWindow(manager, dialogFragmentOptions.gravityAsWindow, newAnim, null, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将 dialog 显示在屏幕位置
     *
     * @param manager
     * @param gravity 对话框显示的位置
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, DialogGravity gravity, int newAnim) {
        return showOnWindow(manager, gravity, newAnim, null, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将 dialog 显示在屏幕位置
     *
     * @param manager
     * @param gravity 对话框显示的位置
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, DialogGravity gravity, int newAnim, String tag) {
        return showOnWindow(manager, gravity, newAnim, tag, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 显示 DialogFragment
     *
     * @param manager           管理器
     * @param gravity           DialogFragment 显示的位置，默认值为：dialogOptions.gravity
     * @param newAnim           DialogFragment 显示/消失时的动画样式，默认值：dialogOptions.animStyle
     * @param tag               将 DialogFragment 加入回退栈时所需的 Tag 信息，一般是 XXXDialogFragment.class.getName()。默认值：null
     * @param allowingStateLoss 书否允许状态丢失。默认值：true
     * @param commitNow         是否立即提交事务。默认值：true
     * @return
     */
    public BaseDialogFragment showOnWindow(FragmentManager manager, DialogGravity gravity, int newAnim, String tag, boolean allowingStateLoss, boolean commitNow) {
        executeShowListener();
        dialogFragmentOptions.gravityAsWindow = gravity;
        dialogFragmentOptions.animStyle = newAnim;
        dialogFragmentOptions.removeAsView();
        dialogFragmentOptions.loadAnim();
        show(manager, tag, allowingStateLoss, commitNow);
        return this;
    }

    /**
     * DialogFragment 显示在 View 的某个位置
     *
     * @param manager
     * @param view
     * @return
     */
    public BaseDialogFragment showOnView(FragmentManager manager, View view) {
        if (dialogFragmentOptions != null) {
            dialogFragmentOptions = getDialogFragmentOptions();
        }
        return showOnView(manager, view, dialogFragmentOptions.gravityAsView, dialogFragmentOptions.animStyle, null, dialogFragmentOptions.offsetX, dialogFragmentOptions.offsetY, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * DialogFragment 显示在 View 的某个位置
     *
     * @param manager
     * @param view
     * @return
     */
    public BaseDialogFragment showOnView(FragmentManager manager, View view, String tag) {
        if (dialogFragmentOptions != null) {
            dialogFragmentOptions = getDialogFragmentOptions();
        }
        return showOnView(manager, view, dialogFragmentOptions.gravityAsView, dialogFragmentOptions.animStyle, tag, dialogFragmentOptions.offsetX, dialogFragmentOptions.offsetY, dialogFragmentOptions.allowingStateLoss, dialogFragmentOptions.commitNow);
    }

    /**
     * 将dialog显示在view附近
     *
     * @param manager
     * @param view          被依赖的view
     * @param gravityAsView 相对于该view的位置(默认为上一次设置的位置)
     * @param newAnim       新的动画(默认为上一次的动画效果)
     * @param offsetX       x轴的偏移量，(默认为上一次设置过的偏移量)
     *                      偏移量的定义请看{@link DialogFragmentOptions# dialogAsView(View, DialogGravity, Int, Int, Int) DialogFragmentOptions.dialogAsView}
     * @param offsetY       y轴的偏移量，(默认为上一次设置过的偏移量)
     */
    private BaseDialogFragment showOnView(FragmentManager manager, View view, DialogGravity gravityAsView, @StyleRes int newAnim, String tag, int offsetX, int offsetY, boolean allowingStateLoss, boolean commitNow) {
        executeShowListener();
        dialogFragmentOptions.dialogAsView(view, gravityAsView, newAnim, offsetX, offsetY);
        show(manager, tag, allowingStateLoss, commitNow);
        return this;
    }

    /**
     * 显示 dialog
     */
    private void show(FragmentManager manager, String tag, boolean allowingStateLoss, boolean commitNow) {
        FragmentTransaction transaction = manager.beginTransaction();
        String fragmentTag;
        if (tag != null) {
            fragmentTag = tag;
        } else {
            fragmentTag = this.getClass().getSimpleName();
        }
        if (manager.findFragmentByTag(fragmentTag) != null) {
            transaction.remove(this);
        }
        transaction.add(this, fragmentTag);
        dialogFragmentOptions.allowingStateLoss = allowingStateLoss;
        dialogFragmentOptions.commitNow = commitNow;
        if (allowingStateLoss) {
            if (commitNow) {
                transaction.commitNowAllowingStateLoss();
            } else {
                transaction.commitAllowingStateLoss();
            }
        } else {
            if (commitNow) {
                transaction.commitNow();
            } else {
                transaction.commit();
            }
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 75;
        }
    }

    /**
     * 建造者模式，用于快速创建一个对话框
     */
    public static class Builder {

        private final DialogFragmentOptions P;
        private View anchor = null;

        public Builder() {
            this.P = new DialogFragmentOptions();
        }

        public BaseDialogFragment.Builder setLayoutId(int layoutId) {
            this.P.layoutId = layoutId;
            return this;
        }


        public BaseDialogFragment.Builder setAnimStyle(@StyleRes int animStyle) {
            this.P.animStyle = animStyle;
            return this;
        }

        public BaseDialogFragment.Builder setWidth(int width) {
            this.P.width = width;
            return this;
        }

        public BaseDialogFragment.Builder setHeight(int height) {
            this.P.height = height;
            return this;
        }

        public BaseDialogFragment.Builder isFullHorizontal(boolean isFullHorizontal) {
            this.P.isFullHorizontal = isFullHorizontal;
            return this;
        }

        public BaseDialogFragment.Builder isFullVertical(boolean isFullVertical) {
            this.P.isFullVertical = isFullVertical;
            return this;
        }

        public BaseDialogFragment.Builder isFullVerticalOverStatusBar(boolean isFullVerticalOverStatusBar) {
            this.P.isFullVerticalOverStatusBar = isFullVerticalOverStatusBar;
            return this;
        }

        public BaseDialogFragment.Builder setDimAmount(float dimAmount) {
            this.P.dimAmount = dimAmount;
            return this;
        }

        public BaseDialogFragment.Builder setGravityAsWindow(DialogGravity gravityAsWindow) {
            this.P.gravityAsWindow = gravityAsWindow;
            return this;
        }

        public BaseDialogFragment.Builder setGravityAsView(DialogGravity gravityAsView) {
            this.P.gravityAsView = gravityAsView;
            return this;
        }

        public BaseDialogFragment.Builder setOffetX(int offsetX) {
            this.P.offsetX = offsetX;
            return this;
        }

        public BaseDialogFragment.Builder setOffetY(int offsetY) {
            this.P.offsetY = offsetY;
            return this;
        }

        public BaseDialogFragment.Builder touchCancel(boolean touchCancel) {
            this.P.touchCancel = touchCancel;
            return this;
        }

        public BaseDialogFragment.Builder backCancel(boolean backCancel) {
            this.P.backCancel = backCancel;
            return this;
        }

        public BaseDialogFragment.Builder asView(boolean asView) {
            this.P.asView = asView;
            return this;
        }

        public BaseDialogFragment.Builder setAnchor(View anchor) {
            this.anchor = anchor;
            return this;
        }

        public BaseDialogFragment build() {
            BaseDialogFragment dialog = new BaseDialogFragment();
            dialog.setDialogFragmentOptions(this.P);
            return dialog;
        }

        public BaseDialogFragment show(FragmentManager fragmentManager) {
            BaseDialogFragment dialog = this.build();
            if (this.P.asView && anchor != null) {
                dialog.showOnView(fragmentManager, anchor);
            } else {
                dialog.showOnWindow(fragmentManager);
            }
            return dialog;
        }
    }

}
