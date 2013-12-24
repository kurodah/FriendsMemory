package com.sample.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistResultActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_result);
		
		Button menu_button=(Button)findViewById(R.id.menu_button);	  
		menu_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentMenu = new Intent(RegistResultActivity.this, MainActivity.class);
				startActivity(intentMenu);
			}
		});
	}		
}
