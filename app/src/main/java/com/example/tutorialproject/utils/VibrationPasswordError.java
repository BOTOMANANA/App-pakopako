package com.example.tutorialproject.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibrationPasswordError {
	public static void vibrateError(Context context , int DURATION_OF_VIBRATION){
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		if(vibrator != null && vibrator.hasVibrator()){
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
				vibrator.vibrate(VibrationEffect.createOneShot(DURATION_OF_VIBRATION, VibrationEffect.DEFAULT_AMPLITUDE));
			}
			else{
				vibrator.vibrate(DURATION_OF_VIBRATION);
			}
		}
	}
}
