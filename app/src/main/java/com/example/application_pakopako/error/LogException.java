package com.example.application_pakopako.error;

import android.util.Log;

import com.example.application_pakopako.constants.Constants;

public class LogException {
	public static void logError(Exception e){
		Log.d(Constants.TAG, "Error is here ==>> " + e.getMessage());
	}

	public static void logNumberFormatError(NumberFormatException e) {
		Log.d(Constants.TAG_FORMAT , "Error is here" + Constants.FORMAT_EXCEPTION + e.getMessage());

	}


}
