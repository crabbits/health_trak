package com.ctountzis.healthtrak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

public class AccountSetupActivity extends FullScreenActivity {

	private EditText bmiField;
	private Button accountSetupGoButton;
	
	private String bmi;
	
	private ProgressDialog progressDialog;

	private ParseUser currentUser;

	private Intent healthTrakIntent; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_setup);
	
		bmiField = (EditText) findViewById(R.id.bmi_field);
	
		accountSetupGoButton = (Button) findViewById(R.id.account_setup_go);
	
		currentUser = ParseUser.getCurrentUser();
		
		healthTrakIntent = new Intent(AccountSetupActivity.this, HealthTrakActivity.class);
		
		setSubmitAccountSetupListenerTo(accountSetupGoButton);
	}
	
	private void setSubmitAccountSetupListenerTo(Button button) {
		button.setOnClickListener(new View.OnClickListener() {		
			
			@Override
			public void onClick(View v) {
				
				bmi = bmiField.getText().toString();
				
				if(formValid())
					setBMIIfValid();
				else
					setToastErrorMessageFor("BMI required");
			}
		});
	}
	
	private boolean formValid() {
		return (!bmi.equals(""));
	}
	
	private void setBMIIfValid() {
		if(bmiInvalid()) 
			setToastErrorMessageFor("BMI must be between 12 and 42");
		else
			setBMIAndContinue();
	}

	private void setBMIAndContinue() {
		progressDialog = ProgressDialog.show(this, "Setting BMI", "Please wait...");
		currentUser.put("bmi", Integer.parseInt(bmi));
		currentUser.saveInBackground();
		progressDialog.dismiss();
		startActivity(healthTrakIntent);
		finish();
	}
	
	private boolean bmiInvalid() {
		return (Integer.parseInt(bmi) < 12 || Integer.parseInt(bmi) > 42);
	}
}
