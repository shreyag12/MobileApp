package com.example.random;

import java.io.BufferedReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
//import java.net.ServerSocket;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;







import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity3 extends Activity {

	private String base64,message,number,str;
	private InetAddress LocalIP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity3);
		Button button=(Button)findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				
		     SendIP sip=new SendIP();
		     sip.execute();
		     
			}		
		});
	}
	
	
	private class SendIP extends AsyncTask<Void, Void, String>
	{
		EditText et; 
		/*Integer value;*/
		
		protected String doInBackground(Void... params) 
		{
			/*HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:9080/New/MyServlet");
			HttpEntity entity = null;
			StringBuffer sb = new StringBuffer();
			try {
				entity = new StringEntity("hello server");
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				InputStream is = response.getEntity().getContent();
				InputStreamReader r = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(r);
				String line = null;
				while(br.readLine() != null){
					sb.append(line);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String string = new String(sb);
			return string;
			*/
			
			try
			{
				
				 URL url = new URL("http://localhost:9080/New/MyServlet");
                 URLConnection connection = url.openConnection();
                 
                
                 connection.setDoInput(true);
                 connection.setDoOutput(true);
                 
                
                 
                 
					et = (EditText) findViewById(R.id.editText1);
					number = et.getText().toString();
					OutputStreamWriter out=new OutputStreamWriter(connection.getOutputStream());
			       //out.write(message);
			       out.write(number+"\n");//sending mobile no. to server
			       out.flush();
			       //out.close();
			     
			       BufferedReader in=new BufferedReader(new InputStreamReader (connection.getInputStream()));
			       message=in.readLine();
			       str=in.readLine();//reading the string sent by server

			       //in.close();
			       byte[]data=str.getBytes("UTF-8");
			       base64=Base64.encodeToString(data, Base64.DEFAULT);//encoding the string into base64
			       //OutputStreamWriter out1=new OutputStreamWriter(connection.getOutputStream());
			       out.write(base64+"\n");
			       out.flush();//sending the encoded string to server
			       out.close();
			       //out.close();
			      
			      
			       
			       
			       
			    
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return message;
			
		     
		
		
	     
	}
	
	protected void onPostExecute(String result)
	{

		
		Toast.makeText(MainActivity3.this, result, Toast.LENGTH_LONG).show();
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

}
