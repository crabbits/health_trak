package com.ctountzis.healthtrak;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.parse.ParseUser;

public class HealthIndicatorActivity extends Activity {

	private ImageView lightsIndicator;
	
	private ParseUser currentUser;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_health_indicator);
	
	       currentUser = ParseUser.getCurrentUser();
	       
	       lightsIndicator = (ImageView) findViewById(R.id.lights_indicator);
	       
	       setHealthLightIndicator();
	}
	
	public void onResume() {
		super.onResume();
		setHealthLightIndicator();
	}
	
	private void setHealthLightIndicator() {
		int bmi = Integer.parseInt(currentUser.get("bmi").toString());
		if(bmi < 18 )
			lightsIndicator.setImageResource(R.drawable.light_average);
		else if(bmi >= 19 && bmi < 25)
			lightsIndicator.setImageResource(R.drawable.light_healthy);
		else
			lightsIndicator.setImageResource(R.drawable.light_unhealthy);
	}
}
