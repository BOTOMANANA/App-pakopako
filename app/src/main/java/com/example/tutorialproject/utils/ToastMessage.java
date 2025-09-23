package com.example.tutorialproject.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {
	private Context context;
	private String message;

	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}

}
