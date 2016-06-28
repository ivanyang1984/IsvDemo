package com.iflytek.isvdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by 健宇 on 2016/6/11.
 * 欢迎界面
 */
public class WelcomeActivity extends Activity {

    /**
     * Called when the activity is first created.
     */

    private Handler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                goLoginActivity();
            }
        }, 1000);
    }

    public void goLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
