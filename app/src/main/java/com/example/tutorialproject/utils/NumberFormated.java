package com.example.tutorialproject.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormated {
	public static String formatValue(long data){
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		return numberFormat.format(data);
	}
	public static String formatIntValue(int data){
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);
		return numberFormat.format(data);
	}
}
