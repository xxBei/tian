package com.example.pxh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class LoginActivity extends Activity {
//    MyApplication app;
    private Button login;
    private EditText user,pass;
    String users,passs;
    TextView registered;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.login);
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.pass);
        registered = (TextView) findViewById(R.id.registered);

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisteredActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users = user.getText().toString();
                passs = pass.getText().toString();
                if(users.equals("")&&passs.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    RequestQueue app = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, TargetUrl.loginer,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("TAG",s);
                            try {
                                JSONObject jsonObject = new JSONObject(s.trim());
                                if(jsonObject.getBoolean("logins")){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    String username = jsonObject.getString("user");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("usernames",username);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Log.d("E","用户名或密码错误");
                                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("TAG",volleyError.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("suser",users);
                            params.put("spassword",passs);
                            return params;
                        }
                    };
                    app.add(stringRequest);
                }
            }
        });

    }
        //再次点击即可退出
        private long exitTime = 0;
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                if((System.currentTimeMillis()-exitTime) > 2000){
                    Toast.makeText(getApplicationContext(), "再次点击即可退出 ", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    System.exit(0);
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
    }

}

