package com.ctountzis.healthtrak;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

public class MyDetailsActivity extends Activity {

	private EditText bmiField;
	private Button updateButton;
	private ParseUser currentUser;
	private String bmi;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_my_details);
	       
	       currentUser = ParseUser.getCurrentUser();
	       
	       bmiField = (EditText) findViewById(R.id.bmi_field);
	       updateButton = (Button) findViewById(R.id.update_button);
	       
	       bmiField.setText(currentUser.get("bmi").toString());
	       
	       setUpdateDetailsListenerTo(updateButton);
	}
	
	private void setUpdateDetailsListenerTo(Button button) {
		button.setOnClickListener(new View.OnClickListener() {		
			
			@Override
			public void onClick(View v) {
				
				bmi = bmiField.getText().toString();
				
				if(formValid()) {
					updateDetails();
				} else { 
					setToastMessage("BMI can't be blank");
				}
			}
		});
	}
	
	private boolean formValid() {
		return (!bmi.equals(""));
	}
	
	private void updateDetails() {
		setBMIIfValid();
	}
	
	private void setBMIIfValid() {
		if(bmiInvalid()) 
			setToastMessage("BMI must be between 12 and 42");
		else
			updateBMI();
	}
	
	private boolean bmiInvalid() {
		return (Integer.parseInt(bmi) < 12 || Integer.parseInt(bmi) > 42);
	}
	
	private void updateBMI() {
		currentUser.put("bmi", Integer.parseInt(bmi));
		currentUser.saveInBackground();
		setToastMessage("Details updated");
		
	}
	
	private void setToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
