package com.peng.dglib.listener;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 */
public class DialogListener {

    public interface OnDialogShow{
        void show();
    }

    public interface OnDialogDismiss{
        void dismiss();
    }

    /*****************对外暴露元素*******************/

    /**
     * 是否在dialog显示的时候执行onDialogShow()方法
     */
    public boolean enableExecuteShowListener = true;

    /**
     * 是否在dialog关闭的时候执行onDialogDismiss()方法
     */
    public boolean enableExecuteDismissListener = true;

    /**
     * 设置在dialog显示的时候执行的回调方法
     */
    public void onDialogShow(OnDialogShow listener) {
        dialogShowListener = listener;
    }

    /**
     * 设置在dialog关闭的时候执行的回调方法
     */
    public void onDialogDismiss(OnDialogDismiss listener) {
        dialogDismissListener = listener;
    }

    /*****************模块内部使用*******************/

    /**
     * dialog显示回调
     */
    private OnDialogShow dialogShowListener;

    /**
     * dialog关闭回调
     */
    private OnDialogDismiss dialogDismissListener;

    /**
     * 模块内dialog显示回调函数执行调用
     */
    private void dialogShow() {
        dialogShowListener.show();
    }

    /**
     * 模块内dialog关闭回调函数执行调用
     */
    private void dialogDismiss() {
        dialogDismissListener.dismiss();
    }

}
