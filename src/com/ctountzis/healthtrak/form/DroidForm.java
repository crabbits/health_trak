package com.ctountzis.healthtrak.form;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DroidForm {

	private Map<EditText,Boolean> requiredFields;
	private Iterator<Entry<EditText,Boolean>> iterator;
	private Context context;

	public DroidForm(Context context) {
		this.context = context;
		this.requiredFields = new HashMap<EditText,Boolean>();
	}

	public void addField(int id, Boolean required) {
		EditText field = (EditText) ((Activity) context).findViewById(id);
		this.requiredFields.put(field, required);
	}

	public boolean formValid() {
		iterator = requiredFields.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<EditText,Boolean> field = (Map.Entry<EditText,Boolean>)iterator.next();
			if(field.getValue() == true && field.getKey().getText().toString().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public String valueOf(int id) {
		iterator = requiredFields.entrySet().iterator();
		View view = ((Activity) context).findViewById(id);
		while(iterator.hasNext()) {
			Map.Entry<EditText,Boolean> nextField = (Map.Entry<EditText,Boolean>)iterator.next();
			if(nextField.getKey() == view) {
				return nextField.getKey().getText().toString();
			}
		}
		return null;
	}

	public void displayToastMessage(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
