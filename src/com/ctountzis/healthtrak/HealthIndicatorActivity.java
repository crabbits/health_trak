package com.ctountzis.healthtrak;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.ctountzis.healthtrak.models.HealthCalculator;
import com.parse.ParseUser;

public class HealthIndicatorActivity extends Activity {

	private ImageView lightsIndicator;
	private ParseUser currentUser;
	private HealthCalculator healthCalculator;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_health_indicator);
	
	       currentUser = ParseUser.getCurrentUser();
	       
	       lightsIndicator = (ImageView) findViewById(R.id.lights_indicator);
	       
	       healthCalculator = new HealthCalculator(currentUser);
	       
	       setHealthLightIndicator();
	}
	
	public void onResume() {
		super.onResume();
		setHealthLightIndicator();
	}
	
	private void setHealthLightIndicator() {
		int healthStatus = healthCalculator.currentHealth();
		switch(healthStatus) {
			case 0:
				lightsIndicator.setImageResource(R.drawable.light_average);
				break;
			case 1:
				lightsIndicator.setImageResource(R.drawable.light_healthy);
				break;
			case 2:
				lightsIndicator.setImageResource(R.drawable.light_unhealthy);
				break;
			default:
				lightsIndicator.setImageResource(R.drawable.light_average);
				break;
		}
	}
}
