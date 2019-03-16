package com.peng.allpowerfuldialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.peng.allpowerfuldialog.dialog.MsgDialog;
import com.peng.dglib.BaseDialogFragment;
import com.peng.dglib.other.DialogGravity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private BaseDialogFragment baseDialogFragment;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏

        setContentView(R.layout.activity_main);

    }

    public void showDialog(View view) {

//        MsgDialog msgDialog = new MsgDialog();
//        msgDialog.showOnView(getSupportFragmentManager(),view);

        new MsgDialog.Builder()
                .setLayoutId(R.layout.dialog_two_btn)
                .asView(true)
                .setGravityAsView(DialogGravity.BOTTOM_ALIGN_RIGHT)
                .setWidth(50)
                .setHeight(50)
                .setDimAmount(0.5f)
                .build().showOnView(getSupportFragmentManager(), view);

    }
}
