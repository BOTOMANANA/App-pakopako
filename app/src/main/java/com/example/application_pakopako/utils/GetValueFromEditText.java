package com.example.application_pakopako.utils;

import android.widget.EditText;

import com.example.application_pakopako.error.LogException;

public class GetValueFromEditText {

	public static int getValue(EditText userInput){
		String value  = userInput.getText().toString().trim();
		if(value.isEmpty()) {
			return 0;
		}
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException error) {
			LogException.logNumberFormatError(error);
			return 0;
		}
	}
}
