package com.ctountzis.healthtrak;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class HealthTrakActivity extends TabActivity {
	
	private TabHost tabHost;
	
	private TabSpec healthIndicator, myDetails, myFoods;
	
	private Intent healthIndicatorIntent, myDetailsIntent, myFoodsIntent;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_trak);
         
        tabHost = getTabHost();
         
        healthIndicator = tabHost.newTabSpec("Health Indicator");
        myDetails = tabHost.newTabSpec("My Details");
        myFoods = tabHost.newTabSpec("My Foods");
  
        healthIndicator.setIndicator("Health Indicator");
        myDetails.setIndicator("My Details");
        myFoods.setIndicator("My Foods");
        
        healthIndicatorIntent = new Intent(this, HealthIndicatorActivity.class);
        myDetailsIntent = new Intent(this, MyDetailsActivity.class);
        myFoodsIntent = new Intent(this, MyFoodsActivity.class);
        
        healthIndicator.setContent(healthIndicatorIntent);
        myDetails.setContent(myDetailsIntent);
        myFoods.setContent(myFoodsIntent);
         
        tabHost.addTab(healthIndicator);
        tabHost.addTab(myDetails);
        tabHost.addTab(myFoods);
    }
}