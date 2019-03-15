package com.peng.dglib.utils;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.peng.dglib.BaseDialogFragment;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 *      1、单例，用于快速显示一个对话框
 *      2、
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

    public BaseDialogFragment showDialogFragmentOnWindow(FragmentManager fragmentManager, BaseDialogFragment baseDialogFragment) {
        return baseDialogFragment.showOnWindow(fragmentManager);
    }

    public void showDialogFragmentOnView(FragmentManager fragmentManager, View view, BaseDialogFragment baseDialogFragment) {
        baseDialogFragment.showOnView(fragmentManager, view);
    }
}
