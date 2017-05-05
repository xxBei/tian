package com.example.pxh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/4.
 */

public class LaunchActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//���ر�����
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//����״̬��
        setContentView(R.layout.activity_launch);


        //����ҳ
        Integer time = 4000;//���õȴ�ʱ�䣬4��
        Handler handler = new Handler();
        //����ʱ����ʱ����ת��������
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this,LoginActivity.class);
                startActivity(intent);
                LaunchActivity.this.finish();
            }
        },time);

        //�����ʱ�������Զ���ת����½ҳ��
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //�������ڴ����ʱ�������������ݼ���
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
