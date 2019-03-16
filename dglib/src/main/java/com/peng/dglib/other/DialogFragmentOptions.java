package com.peng.dglib.other;

import android.animation.Animator;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peng.dglib.BaseDialogFragment;
import com.peng.dglib.R;
import com.peng.dglib.utils.CalculateUtils;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 * 1、dialogFragment 的属性设置
 * 2、Parceable 是因为要在状态变化的时候保存 Fragment 的状态
 */
public class DialogFragmentOptions implements Parcelable {

    /**
     * 布局文件id
     */
    @LayoutRes
    public int layoutId = R.layout.loading_layout;

    /**
     * dialog 样式
     */
    public int dialogStyle = DialogFragment.STYLE_NO_TITLE;

    /**
     * 是否允许状态丢失
     * 说明：Activity 在面对屏幕旋转、系统语言变化时，会强制杀死Activity，然后在重新创建该 Activity，此时就需要保护现场和恢复现场。
     * 当页面中包含 Fragment 时，状态保存与恢复的时候也必须要把 Fragment 保存与恢复。而每一个Fragment有一个FragmentState，
     * 这个FragmentState相当于Fragment的快照，在保存状态是，FragmentManager 把每个 Fragment 的 FragmentState 存储起来，
     * 最终存储到 Activity 的 saveInstanceState 中。既然状态的保存与恢复都必须带上 Fragment，那么，当 Fragment 的状态已经保存过，
     * 那就不应该在改变 Fragment 的状态，因此 FragmentManager 的每一个操作前，都会调用一个方法来检查是否已经保存过状态，即 checkStateLoss()方法
     * 此时，放大招，直接允许状态丢失即可。
     * 为防止状态丢失异常，必须在Activity的onResume或者onPause期间完成提交。
     */
    public boolean allowingStateLoss = true;

    /**
     * 是否立刻提交
     */
    public boolean commitNow = false;

    /**
     * dialog 进出的 animation 动画
     * 此动画为普通动画，
     */
    @StyleRes
    public int animStyle = 0;

    /**
     * 是否可以触发取消，比如在动画开始时将此属性设置 false，防止在动画进行时，被再次触发动画
     */
    public boolean canClick = true;

    /**
     * 是否是懒加载
     */
    public boolean isLazy = false;

    /**
     * 懒加载的延时(根据自己动画的时长来设置)
     */
    public long duration = 0L;

    /**
     * dialog 进入的自定义 animator 动画，不对模块外暴露
     */
    public Animator enterAnimator = null;

    /**
     * dialog 退出的自定义 animator 动画，不对模块外暴露
     */
    public Animator exitAnimator = null;

    /**
     * dialog 的 statusBarColor，如果 Activity 使用沉浸式状态栏，此时就需要设置 dialog 的 statusBarColor，默认透明
     */
    public int dialogStatusBarColor = Color.TRANSPARENT;

    /**
     * 宽度 单位：px(如果不设置，那么默认为布局中所有控件所占的最大空间，即 wrapContent 效果
     * 如果需要达到相应的效果，建议在布局中添加一层View作为背景，例如aaa.xml)
     * 但是经过测试有时候依旧会出一些显示问题，所以尽量还是设置好宽高
     */
    public int width = 0;

    /**
     * 高度 单位：px
     */
    public int height = 0;

    /**
     * 是否横向占满
     */
    public boolean isFullHorizontal = false;

    /**
     * 是否纵向占满
     * 该纵向占满并非全屏，纵向占满会自动扣掉状态栏的高度
     */
    public boolean isFullVertical = false;

    /**
     * 该纵向占满全屏不会扣掉状态栏高度,是真正的全屏
     */
    public boolean isFullVerticalOverStatusBar = false;

    /**
     * 上下边距
     * 以下两个margin分别适用于横纵向不占满的情况
     * 并且根据gravity来判定margin的方向，
     * 例如：当gravity为left和top的时候，
     * 此时的verticalMargin则代表距离left的Margin
     * 此时的horizontalMargin就代表距离top的Margin
     * (CENTER_CENTER的时候默认是相对于left和top)
     * 如果取值范围为[0-1]，代表相对于屏幕的百分比（屏幕宽高*margin取值）
     * 如果取值范围大于1，那么会自动计算出相对于屏幕宽高的比例（margin取值/屏幕宽高）
     * 该属性同样会影响dialog在依附于view上时的偏移，因此在依附于view的时候建议设置为0f
     */
    public float verticalMargin = 0f;

    /**
     * 左右边距
     */
    public float horizontalMargin = 0f;

    /**
     * 以下两个 margin 分别用于横纵向占满的情况
     * 垂直方向上顶部和底部的margin 单位： px
     */
    public int fullVerticalMargin = 0;

    /**
     * 水平方向上左右两边的margin  单位： px
     */
    public int fullHorizontalMargin = 0;

    /**
     * 灰度深浅，值越大，背景灰度越深
     */
    public float dimAmount = 0.3f;

    /**
     * dialog 的位置（默认居中）
     */
    public DialogGravity gravityAsWindow = DialogGravity.CENTER_CENTER;

    /**
     * 当 dialog 依附于 view 时的位置（默认在下方居中）
     */
    public DialogGravity gravityAsView = DialogGravity.CENTER_BOTTOM;

    /**
     * x轴坐标值，用于特殊动画时定位dialog
     */
    public int dialogViewX = 0;

    /**
     * y轴坐标值，用于特殊动画时定位dialog
     */
    public int dialogViewY = 0;

    /**
     * 当 dialog 依附在 view 上时x轴的偏移量
     */
    public int offsetX = 0;

    /**
     * 当dialog依附在view上时y轴的偏移量
     */
    public int offsetY = 0;

    /**
     * 点击屏幕区域，对话框是否消失，但是，点击返回按钮还是会消失
     */
    public boolean touchCancel = true;

    /**
     * 点击返回键，对话框是否关闭
     * 1、当 touchCancel == true 时此属性无效
     * 2、必须是 touchCancel == false 和 backCancel == false 时，那么点击屏幕区域和返回按钮都不能关闭 dialog
     */
    public boolean backCancel = true;

    /**
     * 是否依附在 view 上
     */
    public boolean asView = false;

    /**
     * 返回是否是依附在 view 上
     */
    public boolean isAsView() {
        return asView;
    }

    /**
     * 移除对View的依附
     */
    public void removeAsView() {
        asView = false;
    }

    /**
     * 按钮事件监听
     */
    public DialogInterface.OnKeyListener mOnKeyListener;

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        mOnKeyListener = onKeyListener;
    }

    public DialogFragmentOptions() {
    }

    protected DialogFragmentOptions(Parcel in) {
        layoutId = in.readInt();
        dialogStyle = in.readInt();
        allowingStateLoss = in.readByte() != 0;
        commitNow = in.readByte() != 0;
        animStyle = in.readInt();
        canClick = in.readByte() != 0;
        isLazy = in.readByte() != 0;
        duration = in.readLong();
        dialogStatusBarColor = in.readInt();
        width = in.readInt();
        height = in.readInt();
        isFullHorizontal = in.readByte() != 0;
        isFullVertical = in.readByte() != 0;
        isFullVerticalOverStatusBar = in.readByte() != 0;
        verticalMargin = in.readFloat();
        horizontalMargin = in.readFloat();
        fullVerticalMargin = in.readInt();
        fullHorizontalMargin = in.readInt();
        dimAmount = in.readFloat();
        dialogViewX = in.readInt();
        dialogViewY = in.readInt();
        offsetX = in.readInt();
        offsetY = in.readInt();
        touchCancel = in.readByte() != 0;
        backCancel = in.readByte() != 0;
        asView = in.readByte() != 0;
    }

    public static final Creator<DialogFragmentOptions> CREATOR = new Creator<DialogFragmentOptions>() {
        @Override
        public DialogFragmentOptions createFromParcel(Parcel in) {
            return new DialogFragmentOptions(in);
        }

        @Override
        public DialogFragmentOptions[] newArray(int size) {
            return new DialogFragmentOptions[size];
        }
    };

    /**
     * 设置对话框的主题样式（如果有其他需求可重写该方法），不对模块外暴露
     *
     * @param baseDialogFragment
     */
    protected void setDialogTheme(BaseDialogFragment baseDialogFragment) {
        AppCompatActivity appCompatActivity = baseDialogFragment.getAppCompatActivity();
        //如果activity(是/否)占满全屏并且依旧保留状态栏（沉浸式状态栏）
        if (appCompatActivity.getWindow().getDecorView().getSystemUiVisibility() == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                || appCompatActivity.getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE)) {
            dialogStyle = R.style.BaseDialogFullScreen;
        } else {
            dialogStyle = R.style.BaseDialog;
        }
    }

    /**
     * 获取对话框的主题
     *
     * @param baseDialogFragment
     * @return
     */
    public int getDialogTheme(BaseDialogFragment baseDialogFragment) {
        AppCompatActivity appCompatActivity = baseDialogFragment.getAppCompatActivity();
        // 如果 activity (是/否)占满全屏并且依旧保留状态栏（沉浸式状态栏）
        if (appCompatActivity.getWindow().getDecorView().getSystemUiVisibility() == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                || appCompatActivity.getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE)) {
            return R.style.BaseDialogFullScreen;
        } else {
            return dialogStyle = R.style.BaseDialog;
        }
    }

    /**
     * 依附在view上
     * <p>
     * 1.如果dialog在view的左上角(DialogGravity.LEFT_TOP)
     * 该 offsetX 值表示 dialog 右边线相对于 view 左边线的偏移
     * offsetY 值表示 dialog 下边线相对于 view 上边线的偏移
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * ------------
     * |   view   |
     * ------------
     * <p>
     * 2.如果dialog在view的上部居中(DialogGravity.CENTER_TOP)
     * 此时offsetX值表示dialog与view纵向中心线的偏移量
     * offsetY值表示dialog下边线相对于view上边线的偏移
     * ------------
     * |   view   |
     * ------------
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * ^
     * 纵
     * 向
     * 中
     * 心
     * 线
     * <p>
     * 3.如果dialog在view的右上方(DialogGravity.RIGHT_TOP)
     * 该offsetX值表示dialog左边线相对于view右边线的偏移
     * offsetY值表示dialog下边线相对于view上边线的偏移
     * <p>
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * -------------
     * |    view   |
     * -------------
     * <p>
     * 4.如果dialog在view的左边居中(DialogGravity.LEFT_CENTER)
     * 该offsetX值表示dialog右边线相对于view左边线的偏移
     * offsetY值表示dialog与view横向中心线的偏移
     * <p>
     * -------------
     * |           |-------------
     * |   dialog  ||    view   | <中心线
     * |           |-------------
     * -------------
     * <p>
     * 5.如果dialog在view的右边居中(DialogGravity.RIGHT_CENTER)
     * 该offsetX值表示dialog左边线相对于view右边线的偏移
     * offsetY值表示dialog与view横向中心线的偏移
     * <p>
     * -------------
     * -------------|           |
     * |    view   ||   dialog  |  <中心线
     * -------------|           |
     * -------------
     * <p>
     * 6.如果dialog在view的左下角(DialogGravity.RIGHT_CENTER)
     * 该offsetX值表示dialog右边线相对于view左边线的偏移
     * offsetY值表示dialog上边线相对于view下边线的偏移
     * <p>
     * -------------
     * |    view   |
     * -------------
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * <p>
     * 7.如果dialog在view的下方居中(DialogGravity.CENTER_BOTTOM)
     * 该offsetX值表示dialog与view纵向中心线的偏移
     * offsetY值表示dialog上边线相对于view下边线的偏移
     * <p>
     * -------------
     * |    view   |
     * -------------
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * <p>
     * 8.如果dialog在view的右下方(DialogGravity.CENTER_BOTTOM)
     * 该offsetX值表示dialog左边线相对于view右边线的偏移
     * offsetY值表示dialog上边线相对于view下边线的偏移
     * <p>
     * -------------
     * |    view   |
     * -------------
     * -------------
     * |           |
     * |   dialog  |
     * |           |
     * -------------
     * <p>
     * 9.如果dialog与view的中心重合(DialogGravity.CENTER_BOTTOM)
     * 该offsetX值表示dialog与view纵向中心线的偏移
     * offsetY值表示dialog与view横向中心线的偏移
     * -------------------
     * |                 |
     * |                 |
     * |  -------------  |
     * |  |    view   |  |    <横向中心线
     * |  -------------  |
     * |                 |
     * |      dialog     |
     * -------------------
     * ^
     * 纵
     * 向
     * 中
     * 心
     * 线
     *
     * @param view          目标view
     * @param gravityAsView 依附于view的位置
     * @param newAnim       新的动画
     * @param offsetX       x轴偏移量
     * @param offsetY       y轴偏移量
     */
    public void dialogAsView(View view, DialogGravity gravityAsView, @StyleRes int newAnim, int offsetX, int offsetY) {
        //依附于view
        asView = true;
        //先设置 dialog 的位置在屏幕的左上角，因为这样才能更好的计算最终位置
        this.gravityAsWindow = DialogGravity.LEFT_TOP;
        //设置新的动画
        if (animStyle != newAnim) {
            animStyle = newAnim;
        }
        //设置偏移量
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        //获取到 dialogView 的宽高
        int[] dialogViewSize = CalculateUtils.unDisplayViewSize(LayoutInflater.from(view.getContext()).inflate(layoutId, null));
        int dialogViewWidth = dialogViewSize[0];
        int dialogViewHeight = dialogViewSize[1];
        //设置 view 的数据
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        int viewX = (int) view.getX();
        int viewY = (int) view.getY();
        //设置依附于 view 的位置
        this.gravityAsView = gravityAsView;
        //根据gravity判断显示的位置
        switch (gravityAsView) {
            //dialog显示在view的左上角
            case LEFT_TOP:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y100;
                }
                if (width != 0) {
                    dialogViewX = viewX - width + offsetX;
                } else {
                    dialogViewX = viewX - dialogViewWidth + offsetX;
                }
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY;
                }

                break;
            //dialog显示在view的上方
            case CENTER_TOP:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y100;
                }
                if (width != 0) {
                    dialogViewX = viewX - (width - viewWidth) / 2 + offsetX;
                } else {
                    dialogViewX = viewX - (dialogViewWidth - viewWidth) / 2 + offsetX;
                }
                if (height != 0) {
                    dialogViewY = viewY - height;
                } else {
                    dialogViewY = viewY - (dialogViewHeight + offsetY);
                }
                break;
            //dialog显示在view的右上角
            case RIGHT_TOP:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y100;
                }
                this.dialogViewX = viewX + viewWidth + offsetX;
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY;
                }
                break;
            //dialog显示在view的左边
            case LEFT_CENTER:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y50;
                }
                if (width != 0) {
                    dialogViewX = viewX - width + offsetX;
                } else {
                    dialogViewX = viewX - dialogViewWidth + offsetX;
                }

                if (height != 0) {
                    dialogViewY = viewY - (height - viewHeight) / 2 + offsetY;
                } else {
                    dialogViewY = viewY - (dialogViewHeight - viewHeight) / 2 + offsetY;
                }
                break;
            //dialog显示在view的正中心
            case CENTER_CENTER:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y50;
                }
                if (width != 0) {
                    dialogViewX = viewX - (width - viewWidth) / 2 + offsetX;
                } else {
                    dialogViewX = viewX - (dialogViewWidth - viewWidth) / 2 + offsetX;
                }

                if (height != 0) {
                    dialogViewY = viewY - (height - viewHeight) / 2 + offsetY;
                } else {
                    dialogViewY = viewY - (dialogViewHeight - viewHeight) / 2 + offsetY;
                }
                break;
            //dialog显示在view的右边
            case RIGHT_CENTER:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y50;
                }
                dialogViewX = viewX + viewWidth + offsetX;
                if (height != 0) {
                    dialogViewY = viewY - (height - viewHeight) / 2 + offsetY;
                } else {
                    dialogViewY = viewY - (dialogViewHeight - viewHeight) / 2 + offsetY;
                }
                break;
            //dialog显示在view的左下角
            case LEFT_BOTTOM:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y0;
                }
                if (width != 0) {
                    dialogViewX = viewX - width;
                } else {
                    dialogViewX = viewX - (dialogViewWidth + offsetX);
                }
                dialogViewY = viewY + viewHeight + offsetY;
                break;
            //dialog显示在view的下方
            case CENTER_BOTTOM:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX50Y0;
                }
                if (width != 0) {
                    dialogViewX = viewX - (width - viewWidth) / 2 + offsetX;
                } else {
                    dialogViewX = viewX - (dialogViewWidth - viewWidth) / 2 + offsetX;
                }
                dialogViewY = viewY + viewHeight + offsetY;
                break;
            //dialog显示在view的右下角
            case RIGHT_BOTTOM:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y0;
                }
                dialogViewX = viewX + viewWidth + offsetX;
                dialogViewY = viewY + viewHeight + offsetY;
                break;
            case LEFT_ALIGN_BOTTOM:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y100;
                }
                if (width != 0) {
                    dialogViewX = viewX - width + offsetX;
                } else {
                    dialogViewX = viewX - dialogViewWidth + offsetX;
                }
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY + viewHeight;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY + viewHeight;
                }
                break;
            case LEFT_ALIGN_TOP:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y0;
                }
                if (width != 0) {
                    dialogViewX = viewX - width;
                } else {
                    dialogViewX = viewX - (dialogViewWidth + offsetX);
                }
                dialogViewY = viewY + offsetY;
                break;
            case TOP_ALIGN_LEFT:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y100;
                }
                this.dialogViewX = viewX + offsetX;
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY;
                }
                break;
            case TOP_ALIGN_RIGHT:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y100;
                }
                if (width != 0) {
                    dialogViewX = viewX - width + offsetX + viewWidth;
                } else {
                    dialogViewX = viewX - dialogViewWidth + offsetX + viewWidth;
                }
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY;
                }
                break;
            case BOTTOM_ALIGN_LEFT:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y0;
                }
                dialogViewX = viewX + offsetX;
                dialogViewY = viewY + viewHeight + offsetY;
                break;
            case BOTTOM_ALIGN_RIGHT:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y0;
                }
                if (width != 0) {
                    dialogViewX = viewX - width + viewWidth;
                } else {
                    dialogViewX = viewX - (dialogViewWidth + offsetX) + viewWidth;
                }
                dialogViewY = viewY + viewHeight + offsetY;
                break;
            case RIGHT_ALIGN_TOP:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y0;
                }
                dialogViewX = viewX + viewWidth + offsetX;
                dialogViewY = viewY + offsetY;
                break;

            case RIGHT_ALIGN_BOTTOM:
                if (animStyle == 0) {
                    animStyle = R.style.ScaleOverShootEnterExitAnimationX0Y100;
                }
                this.dialogViewX = viewX + viewWidth + offsetX;
                if (height != 0) {
                    dialogViewY = viewY - height + offsetY + viewHeight;
                } else {
                    dialogViewY = viewY - dialogViewHeight + offsetY + +viewHeight;
                }
                break;
            default:
                if (animStyle == 0) {
                    animStyle = R.style.AlphaEnterExitAnimation;
                }
                if (width != 0) {
                    dialogViewX = viewX - (width - viewWidth) / 2 + offsetX;
                } else {
                    dialogViewX = viewX - (dialogViewWidth - viewWidth) / 2 + offsetX;
                }
                if (height != 0) {
                    dialogViewY = viewY - (height - viewHeight) / 2 + offsetY;
                } else {
                    dialogViewY = viewY - (dialogViewHeight - viewHeight) / 2 + offsetY;
                }

        }
    }

    /**
     * 当没有设置动画的时候，该方法会设置一个默认动画
     */
    public void loadAnim() {
        if (animStyle != 0) {
            return;
        }

        //根据 dialog 的位置来设置默认 anim
        int tempGravity = gravityAsWindow.getLayoutGravity();

        //左上(默认动画从左至右加速减速)
        if ((tempGravity == DialogGravity.LEFT_TOP.getLayoutGravity()) || (tempGravity == DialogGravity.LEFT_CENTER.getLayoutGravity()) || (tempGravity == DialogGravity.LEFT_BOTTOM.getLayoutGravity())) {
            animStyle = R.style.LeftTransAlphaADAnimation;
            //右上(默认动画从右至左加速减速)
        } else if ((tempGravity == DialogGravity.RIGHT_TOP.getLayoutGravity()) || (tempGravity == DialogGravity.RIGHT_CENTER.getLayoutGravity()) || (tempGravity == DialogGravity.RIGHT_BOTTOM.getLayoutGravity())) {
            animStyle = R.style.RightTransAlphaADAnimation;
            //正中(默认动画渐入渐出)
        } else if (tempGravity == DialogGravity.CENTER_CENTER.getLayoutGravity()) {
            animStyle = R.style.AlphaEnterExitAnimation;
            //中上(默认动画从上至下加速减速)
        } else if (tempGravity == DialogGravity.CENTER_TOP.getLayoutGravity()) {
            animStyle = R.style.TopTransAlphaADAnimation;
            //中下(默认动画从下至上加速减速)
        } else if (tempGravity == DialogGravity.CENTER_BOTTOM.getLayoutGravity()) {
            animStyle = R.style.BottomTransAlphaADAnimation;
        } else {
            animStyle = R.style.AlphaEnterExitAnimation;
        }
    }

    /**
     * dialog 顶部导航栏设置
     * 主要用于设置在 statusBar 的影响下，dialog 显示时 y 轴错位的情况
     * 当 activity 是沉浸式状态栏时（无论是否预留 statusBar 的高度）：dialog 的是可占满全屏的
     * 当 activity 非沉浸式状态栏时：dialog 是不可占满全屏的
     * 该方法对象可重写
     */
    public void setDialogFragmentStatusBarMode(BaseDialogFragment baseDialogFragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            AppCompatActivity activity = baseDialogFragment.getAppCompatActivity();
            //activity 是否是占满全屏并且依旧保留状态栏（沉浸式状态栏）
            if (activity.getWindow().getDecorView().getSystemUiVisibility() == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    || activity.getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE)) {
                ViewGroup viewGroup = activity.findViewById(android.R.id.content);
                //使 dialog 的 statusBar 状态与 activity 保持一致
                baseDialogFragment.getDialog().getWindow().getDecorView().setFitsSystemWindows(viewGroup.getChildAt(0).getFitsSystemWindows());
                //无论是否预留了statusBar，都将dialog设置可占满全屏
                if (viewGroup.getChildAt(0).getFitsSystemWindows()) {
                    baseDialogFragment.getDialog().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    //此时可以设置dialogStatusBarColor
//                    baseDialogFragment.getDialog().getWindow().setStatusBarColor(dialogStatusBarColor);

                    //如果不预留statusrBar，那么dialog设置可占满全屏
                } else {
                    baseDialogFragment.getDialog().getWindow().getDecorView().setSystemUiVisibility(activity.getWindow().getDecorView().getSystemUiVisibility());
                }
//                baseDialogFragment.getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            } else {
                //非沉浸式状态栏（包括普通的带状态栏页面以及全屏无状态栏页面）此时的statusBarColor不生效
                baseDialogFragment.getDialog().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                baseDialogFragment.getDialog().getWindow().setStatusBarColor(dialogStatusBarColor);
            }
        } else {//4.4 全透明状态栏
            baseDialogFragment.getDialog().getWindow().addFlags(baseDialogFragment.getAppCompatActivity().getWindow().getAttributes().flags);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(layoutId);
        dest.writeInt(dialogStyle);
        dest.writeByte((byte) (allowingStateLoss ? 1 : 0));
        dest.writeByte((byte) (commitNow ? 1 : 0));
        dest.writeInt(animStyle);
        dest.writeByte((byte) (canClick ? 1 : 0));
        dest.writeByte((byte) (isLazy ? 1 : 0));
        dest.writeLong(duration);
        dest.writeInt(dialogStatusBarColor);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeByte((byte) (isFullHorizontal ? 1 : 0));
        dest.writeByte((byte) (isFullVertical ? 1 : 0));
        dest.writeByte((byte) (isFullVerticalOverStatusBar ? 1 : 0));
        dest.writeFloat(verticalMargin);
        dest.writeFloat(horizontalMargin);
        dest.writeInt(fullVerticalMargin);
        dest.writeInt(fullHorizontalMargin);
        dest.writeFloat(dimAmount);
        dest.writeInt(dialogViewX);
        dest.writeInt(dialogViewY);
        dest.writeInt(offsetX);
        dest.writeInt(offsetY);
        dest.writeByte((byte) (touchCancel ? 1 : 0));
        dest.writeByte((byte) (backCancel ? 1 : 0));
        dest.writeByte((byte) (asView ? 1 : 0));
    }
}
