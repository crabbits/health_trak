package com.ctountzis.healthtrak.models;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class HealthCalculator {

	private ParseUser user;
	private List<ParseObject> foodList;
	
	private int AVERAGE = 0;
	private int HEALTHY = 1;
	private int UNHEALTHY = 2;
	
	public HealthCalculator(ParseUser user) {
		this.user = user;
		this.foodList = foods();
	}
	
	public int currentHealth() {
		int healthWeighting = AVERAGE;
		String bmiRange = currentBMIRange();
		
		if(bmiRange == "normal") {
			healthWeighting = HEALTHY;
		} else if(bmiRange == "underweight") {
			healthWeighting = checkUnderWeightCaloriesAndFat();
		} else if(bmiRange == "overweight") {
			healthWeighting = checkOverWeightCaloriesAndFat();
		} else {
			healthWeighting = UNHEALTHY;
		}
		return healthWeighting;
	}
	
	private int checkUnderWeightCaloriesAndFat() {
		if(averageCalories() < 2500 || averageFat() < 70)
			return UNHEALTHY;
		else
			return AVERAGE;
	}
	
	private int checkOverWeightCaloriesAndFat() {
		if(averageCalories() > 2500 || averageFat() > 70)
			return UNHEALTHY;
		else
			return AVERAGE;
	}
	
	private String currentBMIRange() {
		int bmi = Integer.parseInt(user.get("bmi").toString());
		if(bmi < 18)
			return "underweight";
		else if(bmi >= 19 && bmi < 25)
			return "normal";
		else if(bmi >= 25 && bmi < 30)
			return "overweight";
		else
			return "obese";
	}
	
	private int averageCalories() {
		int averageCalories = 0;
		for (ParseObject food : foodList) {
			averageCalories += food.getInt("caloires");
		}
		if(averageCalories > 0)
			return averageCalories / foodList.size();
		else
			return averageCalories;
	}
	
	private int averageFat() {
		int averageFat = 0;
		for (ParseObject food : foodList) {
			averageFat += food.getInt("fat");
		}
		if(averageFat > 0)
			return averageFat / foodList.size();
		else
			return averageFat;		
	}
	
	private List<ParseObject> foods() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Food");
		query.whereEqualTo("parent", user);
		
		try {
			foodList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foodList;
	}	
}
