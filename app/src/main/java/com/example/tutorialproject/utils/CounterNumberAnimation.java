package com.example.tutorialproject.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.widget.TextView;


public class CounterNumberAnimation {
	public static void counterAnimate(TextView textView, int startNumber, int endNumber, int duration){
		@SuppressLint("Recycle") ValueAnimator valueAnimator =  ValueAnimator.ofInt(startNumber, endNumber);
		valueAnimator.setDuration(duration);
		valueAnimator.addUpdateListener(animation -> {
			int stateValue = (int) animation.getAnimatedValue();
			String convertValue = NumberFormated.formatValue(stateValue);
			textView.setText(convertValue);
		});
		valueAnimator.start();

	}
}
