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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        setContentView(R.layout.activity_main);
    }
}
