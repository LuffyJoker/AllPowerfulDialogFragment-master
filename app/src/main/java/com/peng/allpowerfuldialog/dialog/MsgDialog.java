package com.peng.allpowerfuldialog.dialog;

import com.blankj.utilcode.util.ConvertUtils;
import com.peng.allpowerfuldialog.R;
import com.peng.dglib.BaseDialogFragment;
import com.peng.dglib.other.DialogFragmentOptions;
import com.peng.dglib.other.DialogGravity;

/**
 * create by Mr.Q on 2019/3/16.
 * 类介绍：
 *      示例用的对话框
 */
public class MsgDialog extends BaseDialogFragment {
    @Override
    protected DialogFragmentOptions getDialogFragmentOptions() {
        DialogFragmentOptions options = new DialogFragmentOptions();
        options.layoutId = R.layout.dialog_two_btn;
        options.width = ConvertUtils.dp2px(120);
        options.height = ConvertUtils.dp2px(100);
        options.gravityAsView = DialogGravity.RIGHT_ALIGN_TOP;
        options.asView = true;
        return options;
    }
}
