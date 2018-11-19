package com.example.ruxing.softkeyboardtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    RelativeLayout mRlTest;
    RelativeLayout mRlCommit;

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;

        mRlTest = findViewById(R.id.rl_test);
        mRlCommit = findViewById(R.id.rl_commit);

        mRlTest.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {//认为软键盘将Activity向上推的高度超过了屏幕高度的1/3，就是软键盘弹起了，这个时候隐藏底部的提交按钮
                    //延迟100ms设置不可见性是避免view还没计算好自己的宽高，设置可见不可见性失效。
                    mRlCommit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRlCommit.setVisibility(View.GONE);
                        }
                    }, 100);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {//认为软键盘将Activity向下推的高度超过了屏幕高度的1/3，就是软键盘隐藏了，这个时候显示底部的提交按钮

                    mRlCommit.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRlCommit.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }
            }
        });
    }
}
