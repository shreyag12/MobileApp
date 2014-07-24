package com.example.random;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import com.google.gson.Gson;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity3 extends Activity {

	private String message = null;
	private String message2 = null;
	private String str = null;
	private String imei = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity3);

		SendIP sip = new SendIP();
		sip.execute();
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent intent=new Intent(MainActivity3.this,MainActivity4.class);
				startActivityForResult(intent,2);
				

			}
		});

	}
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		Sendbase64 s = new Sendbase64();
		String message=data.getStringExtra("result");
		if(resultCode==RESULT_OK)
		{
			if(message.equals("true"))
				
		{
				Toast.makeText(MainActivity3.this, "correct pin", Toast.LENGTH_LONG).show();
			s.execute();
		}
		else
			{
				Toast.makeText(MainActivity3.this, "Incorrect pin", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
     //This will send IMEI number and IP Address to MyServlet on loading of the Application
	private class SendIP extends AsyncTask<Void, Void, String> {
		protected String doInBackground(Void... params) {

			try {

				URL url = new URL("http://10.210.8.168:8080/New/MyServlet");
				URLConnection urlconnection = url.openConnection();

				urlconnection.setDoInput(true);
				urlconnection.setDoOutput(true);

				OutputStreamWriter out = new OutputStreamWriter(
						urlconnection.getOutputStream());
				TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

				imei = tm.getDeviceId();

				out.write(imei + "\n");// sending IMEI no. to server
				out.flush();
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlconnection.getInputStream()));
				message = in.readLine();//Acknowledgment  sent by server
				str = in.readLine();// reading the string sent by server
				System.out.println(str);
				in.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return message;

		}

		protected void onPostExecute(String result) {

			Toast.makeText(MainActivity3.this, result, Toast.LENGTH_LONG)
					.show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity3, menu);
		return true;
	}
//This will receive a string ,digitally sign it and send it back to Servlet1 on button click
	private class Sendbase64 extends AsyncTask<Void, Void, String> {
		protected String doInBackground(Void... params) {

			try {
				URL url1 = new URL("http://10.210.8.168:8080/Base64/Servlet1");
				URLConnection urlconnection = url1.openConnection();

				urlconnection.setDoOutput(true);
				urlconnection.setDoInput(true);
				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");// returns
																			// a
																			// keypair
																			// generator
																			// object
																			// that
																			// generates
																			// public/private
																			// key
																			// pair
																			// for
																			// the
																			// specified
																			// algorithm
				kpg.initialize(1024);
				KeyPair kp = kpg.generateKeyPair();
				PrivateKey priKey = kp.getPrivate();
				PublicKey pubKey = kp.getPublic();
				Signature instance = Signature.getInstance("SHA1withRSA");
				instance.initSign(priKey);// initialize this object for signing
				instance.update(str.getBytes());// updates the data to be signed
												// or verified,using the
												// specified array of bytes

				byte[] signature = instance.sign();

				byte[] publickeybytes = pubKey.getEncoded();

				MyEntity obj = new MyEntity();
				obj.setStr(str);
				obj.setPublickeybytes(publickeybytes);
				obj.setSignature(signature);
				Gson gson = new Gson();
				String json = gson.toJson(obj);//converting java object into json representation
				OutputStreamWriter out1 = new OutputStreamWriter(
						urlconnection.getOutputStream());
				out1.write(json);

				System.out.println(str);
				System.out.println(new String(signature));
				System.out.println(new String(publickeybytes));
				out1.flush();
				out1.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlconnection.getInputStream()));
				message2 = in.readLine();//Acknowledgment sent by server on verifying the signature

			} catch (Exception e) {
				e.printStackTrace();
			}
			return message2;
		}

		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT)
					.show();
		}

	}

}
