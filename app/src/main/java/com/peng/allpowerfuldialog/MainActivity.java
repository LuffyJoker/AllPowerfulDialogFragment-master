package com.peng.allpowerfuldialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.peng.dglib.BaseDialogFragment;
import com.peng.dglib.listener.DialogListener;
import com.peng.dglib.other.DialogFragmentOptions;
import com.peng.dglib.other.DialogGravity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BaseDialogFragment baseDialogFragment;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv_msg);
        Button btn = findViewById(R.id.btn);
        baseDialogFragment = new BaseDialogFragment();
        DialogFragmentOptions options = new DialogFragmentOptions();
        options.layoutId = R.layout.dialog_two_btn;
        options.animStyle = R.style.ScaleOverShootEnterExitAnimationX100Y50;
        options.gravityAsView = DialogGravity.LEFT_TOP;
        options.asView = true;
        options.touchCancel = true;
        options.backCancel = false;
        options.offsetX = 0;
        options.offsetY = 0;
        options.width = ConvertUtils.dp2px(100f);
        options.height = ConvertUtils.dp2px(100f);
        baseDialogFragment.setDialogFragmentOptions(options);

    }


    public void showDialogFragmentOnWindow(BaseDialogFragment baseDialogFragment, String tag) {
        baseDialogFragment.showOnWindow(getSupportFragmentManager(), tag);
    }

    public void click(View view) {
        showDialogFragmentOnWindow(baseDialogFragment, "123");
    }

    public void show(View view) {
        Log.d(TAG, "show: "+textView.getX());
        Log.d(TAG, "show: "+textView.getY());
        baseDialogFragment.showOnView(getSupportFragmentManager(),textView,baseDialogFragment.getClass().getName(),true,true);
    }
}
