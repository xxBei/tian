package com.example.pxh;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exampe.pxh.url.TargetUrl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
	private Button btn;
	private String result,city,TAG;
	private Handler handler;
	private EditText cityInput;
	private TextView tv,tv1,tv2,tv3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		handler = new Handler(){
			public void handleMessage(Message msg){
				if(result != null){
					try {
						JSONObject datajson = new JSONObject(result);//��һ������string��ʽת����json��ʽ
						
						JSONArray results = datajson.getJSONArray("results");//��ȡresults����
//					
						JSONObject city = results.getJSONObject(0);
						String currentCity = city.getString("currentCity");//��ȡcity����
						String pm25 = city.getString("pm25");//��ȡpm25
						Log.i(TAG, "����"+currentCity+" pm25 "+pm25);//���Գ��к�pm25
						tv.setText("����:"+currentCity+"\n"+"pm25:"+pm25);
						JSONArray index = city.getJSONArray("index");//��ȡindex�����JSONArray
						//��ȡ����
						JSONObject cy = index.getJSONObject(0);
						String titlec = cy.getString("title");
						String zsc = cy.getString("zs");
						String tiptc = cy.getString("tipt");
						String desc = cy.getString("des");
						//��ȡϴ��
						JSONObject xc = index.getJSONObject(1);
						String titlex = xc.getString("title");
						String zsx = xc.getString("zs");
						String tiptx = xc.getString("tipt");
						String desx = xc.getString("des");
						Log.i(TAG, "������������������"+titlec+zsc+tiptc+desc);//���Դ���
						tv1.setText(titlec+":"+zsc+"\n"+tiptc+":"+desc);
						Log.i(TAG, "������������������"+titlex+zsx+tiptx+desx);
						tv2.setText(titlex+":"+zsx+"\n"+tiptx+":"+desx);
						//......
						/*
						 * weather_data��δ������
						 */
						JSONArray weather_data = city.getJSONArray("weather_data");
						//��ȡ����
						JSONObject today = weather_data.getJSONObject(0);
						String date0 = today.getString("date");
						String dayPictureUrl0 = today.getString("dayPictureUrl");
						String nightPictureUrl0 = today.getString("nightPictureUrl");
						String weather0 = today.getString("weather");
						String wind0 = today.getString("wind");
						String temperature0 = today.getString("temperature");
						Log.i(TAG, "!!!!!!!!!"+date0+dayPictureUrl0+nightPictureUrl0+weather0+wind0+temperature0);
						tv3.setText("����:"+date0+"\n"+"ʵʱ:"+weather0+"\n"+"������"+wind0+"\n"+"�¶ȷ�Χ:"+temperature0);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				super.handleMessage(msg);
			}
		};
	}
	private void initView(){
		cityInput = (EditText)findViewById(R.id.cityInput);
		btn = (Button)findViewById(R.id.btn);
		tv = (TextView)findViewById(R.id.tv);
		tv1 = (TextView)findViewById(R.id.tv1);
		tv2 = (TextView)findViewById(R.id.tv2);
		tv3 = (TextView)findViewById(R.id.tv3);
		
		
		
		btn.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn:
			if(cityInput.getText().toString().equals("") || cityInput.getText().toString() == null){
				Toast.makeText(MainActivity.this, "���������", Toast.LENGTH_LONG).show();
			}else{
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						send(cityInput.getText().toString());
						Message m = handler.obtainMessage();
						handler.sendMessage(m);
					}
				}).start();
			}
			break;

		default:
			break;
		}
	}
	private String send(String city){
		String target = TargetUrl.url1+city+TargetUrl.url2; // Ҫ�ύ��Ŀ���ַ
		HttpClient httpclient=new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet(target); // ����HttpGet����
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // �������ɹ�
				result = EntityUtils.toString(httpResponse.getEntity()).trim(); // ��ȡ���ص��ַ���
				} 
			else {
				result = "fail";
			}
		}catch (ClientProtocolException e) {
			// TODO: handle exception\
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	//�ٴε�������˳�
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if((System.currentTimeMillis()-exitTime) > 2000){
				Toast.makeText(getApplicationContext(), "�ٴε�������˳� ", Toast.LENGTH_SHORT).show();
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
