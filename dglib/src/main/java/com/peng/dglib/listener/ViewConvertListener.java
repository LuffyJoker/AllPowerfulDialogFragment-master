package com.peng.dglib.listener;

import android.os.Parcelable;

import com.peng.dglib.BaseDialogFragment;
import com.peng.dglib.other.ViewHolder;

/**
 * Created by Mr.Q on 2019/3/5
 * 描述：
 */
public abstract class ViewConvertListener implements Parcelable {
    public abstract void convertView(ViewHolder holder, BaseDialogFragment dialogFragment);
}
