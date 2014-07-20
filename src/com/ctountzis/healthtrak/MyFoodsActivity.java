package com.ctountzis.healthtrak;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MyFoodsActivity extends Activity {

	private RelativeLayout newFoodForm;
	private EditText foodNameField, caloriesField, fatField;
	private Button addNewFoodButton, cancelButton, saveButton;
	private ListView foodList;
	private List<String> foods;
	private ParseUser currentUser;
	private String foodName, calories, fat;
	private ProgressDialog progressDialog;
	private ArrayAdapter<String> foodListAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.activity_my_foods);
	
	       newFoodForm = (RelativeLayout) findViewById(R.id.new_food);
	
	       foodNameField = (EditText) findViewById(R.id.food_name_field);
	       caloriesField = (EditText) findViewById(R.id.calories_field);
	       fatField = (EditText) findViewById(R.id.fat_field);
	       
	       addNewFoodButton = (Button) findViewById(R.id.add_new_food_button);
	       saveButton = (Button) findViewById(R.id.save_food);
	       cancelButton = (Button) findViewById(R.id.cancel);
	       
	       foodListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	       
	       foodList = (ListView) findViewById(R.id.food_list);
	       foodList.setAdapter(foodListAdapter);
	      
	       foods = new ArrayList<String>();
	      
	       currentUser = ParseUser.getCurrentUser();

	       showFormOnAddNewFoodButtonClick(addNewFoodButton);
	       addFormSubmissionListenerTo(saveButton);
	       addCancelFormListenerToButton(cancelButton);       
	}
	
	public void onResume() {
		super.onResume();
		populateFoodList();
	}
	
	private void populateFoodList() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Food");
		query.whereEqualTo("parent", currentUser);
		
		foodListAdapter.clear();
		
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> foodList, ParseException e) {
		      if (e != null) {
		    	  Toast.makeText(MyFoodsActivity.this, "Couldn't load list of foods", Toast.LENGTH_SHORT).show();
		      } else {
		    	  if(foodList.size() == 0) {
		    		  foodListAdapter.add("No foods found");
		    	  } else {
		    		  for (ParseObject food : foodList) {
		    			  foodListAdapter.add(listAdapterStringFor(food));
		    		  }
		    	  }
		      }
		    }
		});
	}
	
	private String listAdapterStringFor(ParseObject food) {
		return food.getString("name").toString() + " - " 
				+ food.getString("calories").toString() + " (cal) - "
				+ food.getString("fat").toString() + " (grams)";
	}
	
	private void showFormOnAddNewFoodButtonClick(Button button) {
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addNewFoodButton.setVisibility(View.GONE);
				foodList.setVisibility(View.GONE);
				newFoodForm.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private void addFormSubmissionListenerTo(Button button) {
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				foodName = foodNameField.getText().toString();
				calories = caloriesField.getText().toString();
				fat = fatField.getText().toString();
				
				if(formValid()) {
					saveFood();
				} else {
					setToastMessage("Please fill out all fields");
				}
			}
		});
	}
	
	private boolean formValid() {
		return (!foodName.equals("") && !calories.equals("") && !fat.equals(""));
	}
	
	private void saveFood() {
		progressDialog = ProgressDialog.show(this, "Creating Food", "Please wait...");
		
		ParseObject food = new ParseObject("Food");
		food.put("name", foodName);
		food.put("calories", calories);
		food.put("fat", fat);
		food.put("parent", currentUser);
		food.saveInBackground();
		progressDialog.dismiss();
		resetForm();
		populateFoodList();
		foodList.setVisibility(View.VISIBLE);
	}
	
	private void addCancelFormListenerToButton(Button button) {
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				resetForm();	
				foodList.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private void resetForm() {
		foodNameField.setText("");
	    caloriesField.setText("");
	    fatField.setText("");
	    addNewFoodButton.setVisibility(View.VISIBLE);;
	    newFoodForm.setVisibility(View.GONE);
	}
	
	private void setToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
}
