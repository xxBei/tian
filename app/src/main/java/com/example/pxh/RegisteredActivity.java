package com.example.pxh;
import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exampe.pxh.url.TargetUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11.
 */

public class RegisteredActivity extends Activity{
    Button signUp;
    EditText signUp_user,signUp_pass;
    String signUp_users,signUp_passs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_registered);

        signUp = (Button) findViewById(R.id.signUp);
        signUp_user = (EditText)findViewById(R.id.signUp_user);
        signUp_pass = (EditText)findViewById(R.id.signUp_pass);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp_users = signUp_user.getText().toString();
                signUp_passs = signUp_pass.getText().toString();

                System.out.println(signUp_users+"--"+signUp_passs);
                if(signUp_users.equals("") && signUp_passs.equals("")){
                    Toast.makeText(RegisteredActivity.this,"用户名或密码不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    RequestQueue myapp = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, TargetUrl.registered, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("REGISTERED",s);
                            Toast.makeText(RegisteredActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisteredActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("REGISTERED",volleyError.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("suser",signUp_users);
                            params.put("spassword",signUp_passs);
                            return params;
                        }
                    };
                    myapp.add(request);
                }

            }
        });
    }

    //返回
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent = new Intent(RegisteredActivity.this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
