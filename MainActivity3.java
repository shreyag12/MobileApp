package com.example.random;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
//import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;







import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity3 extends Activity {

	private String text,message,number,str;
	private InetAddress LocalIP;
	private Socket sock;
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
		protected String doInBackground(Void... params) 
		{
			
			try
			{
				
				 System.out.println("Connecting to server"); 
			        Socket sock = new Socket( "10.210.8.168", 4444);
			        LocalIP = sock.getLocalAddress();
					message = LocalIP.toString();
					et = (EditText) findViewById(R.id.editText1);
					number = et.getText().toString();
			       
			       OutputStream ostream = sock.getOutputStream( );
			       PrintWriter pwrite = new PrintWriter(ostream, true);
			       //pwrite.println("Hello server");
                  pwrite.print(number)			       ;
                   pwrite.println(message);
                   //pwrite.flush();
                  // */
			       InputStream istream = sock.getInputStream();
			       BufferedReader socketRead = new BufferedReader(new InputStreamReader(istream)); 
			        str=socketRead.readLine();
			     
			           pwrite.close(); socketRead.close(); 
			    
			}
			catch(UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		     
		
		return str;
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
