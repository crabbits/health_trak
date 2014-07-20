package com.ctountzis.healthtrak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;

public class SplashActivity extends FullScreenActivity {

	private Intent menuIntent;


	private ParseUser currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	
		menuIntent = new Intent(SplashActivity.this, LoginSignupActivity.class);
			
		initializeParse();
		finish();
	}
	
	private void initializeParse() {
		Parse.initialize(this, "ZLacvYiFspat1IT0FEjN8Duj1Ghf5Ajc12JBmlkn", "QmiDQLh63JsyJng6ejYbt4vsSPwx0hQIa0LauY0q");
		currentUser = ParseUser.getCurrentUser();
		loginCachedUser();
	}
	
	private void loginCachedUser() {		
		if (currentUser != null) 
			checkCurrentUserSignupStepFor(currentUser, SplashActivity.this);
		else 
			startActivity(menuIntent);
	}
}
