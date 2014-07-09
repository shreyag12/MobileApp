package com.example.random;

import java.io.BufferedReader;



import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity3 extends Activity {

	private String base64, message, message2,imei,str;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity3);
		

				SendIP sip = new SendIP();
				sip.execute();
				Button button = (Button) findViewById(R.id.button1);
				button.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Sendbase64 s=new Sendbase64();
						s.execute();

			}
		});
		
	}
	

	private class SendIP extends AsyncTask<Void, Void, String> {
		EditText et;
		
		protected String doInBackground(Void... params) {
			
			
			try {

				URL url = new URL("http://10.0.2.2:8080/New/MyServlet");
				URLConnection urlconnection = url.openConnection();
				
				
				urlconnection.setDoInput(true);
				urlconnection.setDoOutput(true);
				//et = (EditText) findViewById(R.id.editText1);
				//number = et.getText().toString();
				
				
		    OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
		    TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		       imei = tm.getDeviceId();
			
				out.write(imei+ "\n");// sending mobile no. to server
				out.flush();
				out.close();
				
				//out.close();
			
			

				BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
				message = in.readLine();
				str = in.readLine();// reading the string sent by server
			
				 in.close();
				

				
			
				
				
				/*out1.write(base64 + "\n");
				out1.flush();// sending the encoded string to server
				out1.close();
				*/
			
			
				// out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
			

		}

		protected void onPostExecute(String result) {

			Toast.makeText(MainActivity3.this, result, Toast.LENGTH_LONG)
					.show();
			et = (EditText) findViewById(R.id.editText1);
			et.setText("");
		}

	}

	@Override
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity3, menu);
		return true;
	}
	private class Sendbase64 extends AsyncTask<Void, Void, String> 
	{
		protected String doInBackground(Void... params)
		{
	
		try
		{
		URL url1=new URL("http://10.0.2.2:8080/Base64/Servlet1");
		URLConnection urlconnection=url1.openConnection();
		
		//HttpURLConnection httpconnection=(HttpURLConnection)urlconnection;
		//httpconnection.setRequestMethod("POST");
		//httpconnection.connect();
		
		
		urlconnection.setDoOutput(true);
		urlconnection.setDoInput(true);
		
		byte[] data = str.getBytes("UTF-8");
		base64 = Base64.encodeToString(data, Base64.DEFAULT);
		
		OutputStreamWriter out1=new OutputStreamWriter(urlconnection.getOutputStream());
		out1.write(base64+"\n");
		out1.flush();
		out1.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
		message2 = in.readLine();
		System.out.println(message2);
		//httpconnection.disconnect();
		//Toast.makeText(getApplicationContext(), message2, Toast.LENGTH_SHORT).show();
	}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return message2;
	}
		protected void onPostExecute(String result)
		{
			Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
		}
		
	}
}


