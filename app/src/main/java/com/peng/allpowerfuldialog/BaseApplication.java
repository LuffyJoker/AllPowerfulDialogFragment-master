package com.peng.allpowerfuldialog;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Mr.Q on 2019/3/8
 * 描述：
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
