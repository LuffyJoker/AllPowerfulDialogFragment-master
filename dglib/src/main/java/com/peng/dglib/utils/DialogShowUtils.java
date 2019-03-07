package com.peng.dglib.utils;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.peng.dglib.BaseDialogFragment;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 */
public class DialogShowUtils {

    private DialogShowUtils() {

    }

    private static class SingletonHolder {
        private final static DialogShowUtils instance = new DialogShowUtils();
    }

    public static DialogShowUtils getInstance() {
        return SingletonHolder.instance;
    }

    public BaseDialogFragment showDialogFragmentOnWindow(FragmentManager fragmentManager, BaseDialogFragment baseDialogFragment, String tag, boolean allowingStateLoss, boolean commitNow){
        return baseDialogFragment.showOnWindow(fragmentManager, tag, allowingStateLoss, commitNow);
    }

    public void showDialogFragmentOnView(FragmentManager fragmentManager,View view, BaseDialogFragment baseDialogFragment, String tag, boolean allowingStateLoss, boolean commitNow) {
        baseDialogFragment.showOnView(fragmentManager, view,  tag, allowingStateLoss, commitNow);
    }
//
//    /**
//     * 创建一个dialog
//     * 你也可以继承 BaseDialog，实现更多功能，并通过扩展函数来简化创建过程
//     */
//    public BaseDialogFragment baseDialogFragment(DialogFragmentOptions.DialogOptionsIpml dialogOptionsIpml){
//        BaseDialogFragment baseDialogFragment = new BaseDialogFragment();
//        baseDialogFragment.setRuntimeOverrideOptions(dialogOptionsIpml);
//        return baseDialogFragment;
//    }

}
