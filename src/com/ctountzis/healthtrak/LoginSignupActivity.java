package com.ctountzis.healthtrak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.ctountzis.healthtrak.form.DroidForm;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginSignupActivity extends FullScreenActivity {

	private DroidForm loginSignupForm;
	
	private Button startTrackingButton;
	private CheckBox createNewAccount;
	
	private Intent accountSetupIntent;
	
	private ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_signup);
	
		loginSignupForm = new DroidForm(this);
		
		loginSignupForm.addField(R.id.username_field, true);
		loginSignupForm.addField(R.id.password_field, true);
		
		startTrackingButton = (Button) findViewById(R.id.start_tracking);
		createNewAccount = (CheckBox) findViewById(R.id.create_new_account);
		
	    accountSetupIntent = new Intent(LoginSignupActivity.this, AccountSetupActivity.class);
	    
		setFormSubmissionListener();		
	}
	
	private void setFormSubmissionListener() {
		startTrackingButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				if(loginSignupForm.formValid()) {
					checkForLoginOrSignup();
				} else {
					loginSignupForm.displayToastMessage("Username & Password required");
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
		user.setUsername(loginSignupForm.valueOf(R.id.username_field));
		user.setPassword(loginSignupForm.valueOf(R.id.password_field));
		
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					startActivity(accountSetupIntent);
					finish();
				} else {
					progressDialog.dismiss();
					loginSignupForm.displayToastMessage(e.getLocalizedMessage());
				}
			}
		});
	}
	
	private void loginUser() {
		progressDialog = ProgressDialog.show(this, "Logging In", "Please wait...");
		ParseUser.logInInBackground(loginSignupForm.valueOf(R.id.username_field),
				loginSignupForm.valueOf(R.id.password_field), 
				new LogInCallback() {
			
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					checkCurrentUserSignupStepFor(user, LoginSignupActivity.this);
				} else {
					progressDialog.dismiss();
					loginSignupForm.displayToastMessage(e.getLocalizedMessage());
				}
			}
		});
	}
}
