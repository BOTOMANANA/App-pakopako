package com.example.tutorialproject.error;

import android.util.Log;

import com.example.tutorialproject.constants.Constants;

public class DBException {
	public static void logError(Exception e){
		Log.d(Constants.TAG, "Error is here ==>> " + e.getMessage());
	}
}
