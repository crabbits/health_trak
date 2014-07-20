package com.ctountzis.healthtrak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.parse.ParseUser;

public class FullScreenActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	public void setToastErrorMessageFor(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	public void checkCurrentUserSignupStepFor(ParseUser currentUser, Context context) {
		if(currentUser.get("bmi") == null) 
			startActivity(new Intent(context, AccountSetupActivity.class));
		else
			startActivity(new Intent(context, HealthTrakActivity.class));	
	}
}
