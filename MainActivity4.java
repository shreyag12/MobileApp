package com.example.random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity4 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity4);
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				EditText passwordtext=(EditText) findViewById(R.id.editText1);
				Intent data=new Intent();
				SharedPreferences obj=getSharedPreferences("my_pin",0);
				SharedPreferences.Editor editor=obj.edit();
				editor.putString("pin", "1234");
				editor.commit();
				String password=passwordtext.getText().toString();
				System.out.println(password);
				String pass=obj.getString("pin", null);
				System.out.println(pass);
				if(password.equals(pass))
				{
					data.putExtra("result", "true");
					setResult(RESULT_OK,data);
				}		
				else
				{
					data.putExtra("result", "false");
					setResult(RESULT_OK,data);
				}
				passwordtext.setText("");
				finish();
				
				}
			
			});
			
		}
		
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity4, menu);
		return true;
	}

}
