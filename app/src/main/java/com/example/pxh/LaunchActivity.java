package com.example.pxh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        setContentView(R.layout.activity_launch);


        //启动页
        Integer time = 4000;//设置等待时间，4秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(intent);
                LaunchActivity.this.finish();
            }
        }, time);

        //处理耗时任务后会自动跳转到登陆页面
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //以下用于处理耗时任务，如网络数据加载
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(LaunchActivity.this,LoginActivity.class);
                        startActivity(i);
                        LaunchActivity.this.finish();
                    }
                });
            }
        }).start();*/
    }


}
