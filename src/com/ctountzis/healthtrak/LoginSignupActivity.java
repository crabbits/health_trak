package com.ctountzis.healthtrak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSignupActivity extends FullScreenActivity {

	private Button startTrackingButton;
	private CheckBox createNewAccount;
	private EditText usernameField, passwordField;
	
	private String username, password;
	
	private Intent accountSetupIntent;
	private Intent healthTrakIntent;
	
	private ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_signup);
	
		startTrackingButton = (Button) findViewById(R.id.start_tracking);
		createNewAccount = (CheckBox) findViewById(R.id.create_new_account);
	    usernameField = (EditText) findViewById(R.id.username_field);
	    passwordField = (EditText) findViewById(R.id.password_field);
		
	    accountSetupIntent = new Intent(LoginSignupActivity.this, AccountSetupActivity.class);
	    healthTrakIntent = new Intent(LoginSignupActivity.this, HealthTrakActivity.class);
	    
		setFormSubmissionListenerTo(startTrackingButton);
		
	}
	
	private void setFormSubmissionListenerTo(Button button) {
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				username = usernameField.getText().toString();
				password = passwordField.getText().toString();
				
				if(formValid()) {
					checkForLoginOrSignup();
				} else {
					setToastErrorMessageFor("Username & Password required");
				}
			}
		});
	}
	
	private void checkForLoginOrSignup() {
		if(createNewAccount.isChecked())
			createNewAccount();
		else
			loginUser();
	}
	
	private void createNewAccount() {
		progressDialog = ProgressDialog.show(this, "Creating Account", "Please wait...");
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					startActivity(accountSetupIntent);
					finish();
				} else {
					progressDialog.dismiss();
					setToastErrorMessageFor(e.getLocalizedMessage());
				}
			}
		});
	}
	
	private void loginUser() {
		progressDialog = ProgressDialog.show(this, "Logging In", "Please wait...");
		ParseUser.logInInBackground(username,  password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					checkCurrentUserSignupStepFor(user, LoginSignupActivity.this);
				} else {
					progressDialog.dismiss();
					setToastErrorMessageFor(e.getLocalizedMessage());;
				}
			}
		});
	}
	
	private boolean formValid() {
		return (!username.equals("") && !password.equals(""));
	}
}
