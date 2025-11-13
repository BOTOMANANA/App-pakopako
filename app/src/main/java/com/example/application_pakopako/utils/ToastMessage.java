package com.example.application_pakopako.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {
	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}

}
